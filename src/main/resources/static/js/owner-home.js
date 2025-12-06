document.addEventListener('DOMContentLoaded', () => {

    /* ------------------------------------------------------------------
       UNIVERSAL MODAL SYSTEM
    ------------------------------------------------------------------ */

    function openModal(modal) {
        modal.classList.remove('hidden');
        document.body.style.overflow = 'hidden';
    }

    function closeModal(modal) {
        modal.classList.add('hidden');
        document.body.style.overflow = '';
    }

    function enableOverlayClose(modal) {
        modal.addEventListener('click', (e) => {
            if (e.target === modal) closeModal(modal);
        });
    }

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            document.querySelectorAll('.modal').forEach(m => closeModal(m));
        }
    });

    /* ------------------------------------------------------------------
       DOM ELEMENTS
    ------------------------------------------------------------------ */

    const foodSection = document.getElementById('food-section');
    const dealsSection = document.getElementById('deals-section');
    const navFood = document.getElementById('nav-food');
    const navDeals = document.getElementById('nav-deals');
    const toast = document.getElementById('toast');

    const foodListEl = document.getElementById('food-list');
    const foodLoading = document.getElementById('food-loading');
    const btnNewFood = document.getElementById('btn-new-food');
    const modalFood = document.getElementById('modal-food');
    const foodForm = document.getElementById('food-form');
    const foodCancel = document.getElementById('food-cancel');

    const modalDeal = document.getElementById('modal-deal');
    const dealForm = document.getElementById('deal-form');
    const btnNewDeal = document.getElementById('btn-new-deal');
    const dealCancelBtn = document.getElementById('deal-cancel');

    /* ------------------------------------------------------------------
       UTILITIES
    ------------------------------------------------------------------ */

    function showSection(name) {
        if (name === 'food') {
            foodSection.classList.remove('hidden');
            dealsSection.classList.add('hidden');
        } else {
            foodSection.classList.add('hidden');
            dealsSection.classList.remove('hidden');
        }
    }

    function showToast(message, isError = false) {
        toast.textContent = message;
        toast.classList.remove('hidden');
        toast.style.background = isError ? '#d9534f' : '#28a745';

        setTimeout(() => toast.classList.add('hidden'), 3500);
    }

    function showLoading(el, show) {
        el.style.display = show ? 'block' : 'none';
    }

    function escapeHtml(s) {
        if (!s) return '';
        return String(s).replace(/[&<>"']/g, (m) => ({
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#39;'
        }[m]));
    }

    async function loadRestaurants() {
        try {
            const res = await fetch('/api/owner/restaurants');
            if (!res.ok) throw await res.text();
            const restaurants = await res.json();

            const sel = document.getElementById('restaurantSelector');
            sel.innerHTML = restaurants
                .map(r => `<option value="${r.id}">${r.name}</option>`)
                .join('');

            sel.addEventListener('change', async () => {
                try {
                    const r = await fetch(`/api/owner/restaurants/select/${sel.value}`, { method: 'POST' });
                    if (!r.ok) throw await r.text();
                    showToast('Restaurant selected');
                    await loadFoodItems();
                    await loadDeals();
                } catch (err) {
                    showToast('Failed to select restaurant', true);
                }
            });
        } catch (err) {
            showToast('Failed to load restaurants', true);
        }
    }

    /* ------------------------------------------------------------------
       MODAL HANDLERS
    ------------------------------------------------------------------ */

    btnNewFood.addEventListener('click', () => openFoodModal());
    foodCancel.addEventListener('click', () => closeModal(modalFood));
    enableOverlayClose(modalFood);

    btnNewDeal.addEventListener('click', () => openDealModal());
    dealCancelBtn.addEventListener('click', () => closeModal(modalDeal));
    enableOverlayClose(modalDeal);

    function openFoodModal(item = null) {
        foodForm.reset();
        foodForm.id.value = item ? item.id : '';
        document.getElementById('food-form-title').textContent =
            item ? 'Edit Food Item' : 'New Food Item';

        if (item) {
            foodForm.name.value = item.name;
            foodForm.description.value = item.description || '';
            foodForm.price.value = item.price;
            foodForm.category.value = item.category || '';
        }

        openModal(modalFood);
    }

    function openDealModal(d = null) {
        dealForm.reset();
        dealForm.id.value = d ? d.id : '';
        document.getElementById('deal-form-title').textContent =
            d ? 'Edit Deal' : 'New Deal';

        if (d) {
            dealForm.title.value = d.title;
            dealForm.description.value = d.description || '';
            dealForm.discountValue.value = d.discountValue;
            dealForm.discountType.value = d.discountType || 'PERCENT';
            dealForm.startDate.value = d.startDate || '';
            dealForm.endDate.value = d.endDate || '';
            dealForm.applicableItemIds.value = d.applicableItemIds || '';
        }

        openModal(modalDeal);
    }

    /* ------------------------------------------------------------------
       DATA LOADING
    ------------------------------------------------------------------ */

    async function fetchData(url, options = {}) {
        const res = await fetch(url, options);
        if (!res.ok) throw await res.text();
        return res.json();
    }

    async function loadFoodItems() {
        showLoading(foodLoading, true);
        foodListEl.innerHTML = '';
        try {
            const items = await fetchData('/api/owner/food');
            items.forEach(renderFoodItem);
        } catch (err) {
            showToast('Failed to load food items', true);
        } finally {
            showLoading(foodLoading, false);
        }
    }

    async function loadDeals() {
        const dealsLoading = document.getElementById('deals-loading');
        const dealsListEl = document.getElementById('deals-list');

        dealsLoading.style.display = 'block';
        dealsListEl.innerHTML = '';

        try {
            const items = await fetchData('/api/owner/deals');
            items.forEach(renderDeal);
        } catch (err) {
            showToast('Failed to load deals', true);
        } finally {
            dealsLoading.style.display = 'none';
        }
    }

    function renderFoodItem(item) {
        const card = document.createElement('div');
        card.className = 'card';

        const img = item.imageUrl ? `<img src="${item.imageUrl}" alt="">` : '';

        card.innerHTML = `
            <div class="card-media">${img}</div>
            <div class="card-body">
                <h3>${escapeHtml(item.name)}</h3>
                <p>${escapeHtml(item.description || '')}</p>
                <div class="meta">
                    <span>${escapeHtml(item.category || '')}</span>
                    <strong>$${item.price?.toFixed(2)}</strong>
                </div>
                <div class="actions">
                    <button class="edit">Edit</button>
                    <button class="delete">Delete</button>
                </div>
            </div>
        `;

        foodListEl.appendChild(card);

        card.querySelector('.edit')
            .addEventListener('click', () => openFoodModal(item));

        card.querySelector('.delete')
            .addEventListener('click', () => deleteFoodItem(item.id));
    }

    function renderDeal(d) {
        const card = document.createElement('div');
        card.className = 'deal-card';

        card.innerHTML = `
            <h3>${escapeHtml(d.title)}</h3>
            <p>${escapeHtml(d.description || '')}</p>
            <div class="meta">
                <span>${d.discountType === 'PERCENT'
            ? d.discountValue + '%'
            : '$' + d.discountValue}</span>
                <span>${d.startDate} → ${d.endDate}</span>
            </div>
            <div class="actions">
                <button class="edit-deal">Edit</button>
                <button class="delete-deal">Delete</button>
            </div>
        `;

        document.getElementById('deals-list').appendChild(card);

        card.querySelector('.edit-deal')
            .addEventListener('click', () => openDealModal(d));

        card.querySelector('.delete-deal')
            .addEventListener('click', () => deleteDeal(d.id));
    }

    /* ------------------------------------------------------------------
       CRUD
    ------------------------------------------------------------------ */

    foodForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const id = foodForm.id.value;
        const url = id ? `/api/owner/food/${id}` : '/api/owner/food';
        const method = id ? 'PUT' : 'POST';
        const fd = new FormData(foodForm);

        showLoading(foodLoading, true);

        try {
            const res = await fetch(url, { method, body: fd });
            if (!res.ok) throw await res.text();

            await loadFoodItems();
            showToast('Saved');
            closeModal(modalFood);

        } catch (err) {
            showToast('Error: ' + err, true);
        } finally {
            showLoading(foodLoading, false);
        }
    });

    // ✔ FIXED: Deal form now sends JSON instead of FormData
    dealForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const id = dealForm.id.value;
        const url = id ? `/api/owner/deals/${id}` : '/api/owner/deals';
        const method = id ? 'PUT' : 'POST';

        const payload = {
            title: dealForm.title.value,
            description: dealForm.description.value,
            discountValue: parseFloat(dealForm.discountValue.value),
            discountType: dealForm.discountType.value,
            startDate: dealForm.startDate.value,
            endDate: dealForm.endDate.value,
            applicableItemIds: dealForm.applicableItemIds.value
        };

        try {
            const res = await fetch(url, {
                method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            if (!res.ok) throw await res.text();

            await loadDeals();
            showToast('Saved');
            closeModal(modalDeal);

        } catch (err) {
            showToast('Error: ' + err, true);
        }
    });

    async function deleteFoodItem(id) {
        if (!confirm('Delete this item?')) return;

        try {
            const res = await fetch(`/api/owner/food/${id}`, { method: 'DELETE' });
            if (!res.ok) throw 'Delete failed';

            showToast('Deleted');
            await loadFoodItems();
        } catch (err) {
            showToast('Delete failed', true);
        }
    }

    async function deleteDeal(id) {
        if (!confirm('Delete this deal?')) return;

        try {
            const res = await fetch(`/api/owner/deals/${id}`, { method: 'DELETE' });
            if (!res.ok) throw 'Delete failed';

            showToast('Deleted');
            await loadDeals();

        } catch (err) {
            showToast('Delete failed', true);
        }
    }

    /* ------------------------------------------------------------------
       INITIAL LOAD
    ------------------------------------------------------------------ */

    navFood.addEventListener('click', (e) => { e.preventDefault(); showSection('food'); });
    navDeals.addEventListener('click', (e) => { e.preventDefault(); showSection('deals'); });

    loadFoodItems();
    loadDeals();
    loadRestaurants();

});
