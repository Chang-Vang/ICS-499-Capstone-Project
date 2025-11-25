// language: javascript
document.addEventListener('DOMContentLoaded', () => {
  const foodSection = document.getElementById('food-section');
  const dealsSection = document.getElementById('deals-section');
  const navFood = document.getElementById('nav-food');
  const navDeals = document.getElementById('nav-deals');
  const toast = document.getElementById('toast');

  navFood.addEventListener('click', (e) => { e.preventDefault(); showSection('food'); });
  navDeals.addEventListener('click', (e) => { e.preventDefault(); showSection('deals'); });

  function showSection(name) {
    if (name === 'food') {
      foodSection.classList.remove('hidden');
      dealsSection.classList.add('hidden');
    } else {
      foodSection.classList.add('hidden');
      dealsSection.classList.remove('hidden');
    }
  }

  // Food handling
  const foodListEl = document.getElementById('food-list');
  const foodLoading = document.getElementById('food-loading');
  const btnNewFood = document.getElementById('btn-new-food');
  const modalFood = document.getElementById('modal-food');
  const foodForm = document.getElementById('food-form');
  const foodCancel = document.getElementById('food-cancel');

  btnNewFood.addEventListener('click', () => openFoodModal());
  foodCancel.addEventListener('click', closeFoodModal);

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
      closeFoodModal();
    } catch (err) {
      showToast('Error: ' + err, true);
    } finally {
      showLoading(foodLoading, false);
    }
  });

  async function loadFoodItems() {
    foodListEl.innerHTML = '';
    showLoading(foodLoading, true);
    try {
      const res = await fetch('/api/owner/food');
      const items = await res.json();
      if (!Array.isArray(items)) throw 'Invalid response';
      items.forEach(renderFoodItem);
    } catch (err) {
      showToast('Failed to load food items', true);
    } finally {
      showLoading(foodLoading, false);
    }
  }

  function renderFoodItem(item) {
    const card = document.createElement('div');
    card.className = 'card';
    const img = item.imageUrl ? `<img src="${item.imageUrl}" alt="" />` : '';
    card.innerHTML = `
      <div class="card-media">${img}</div>
      <div class="card-body">
        <h3>${escapeHtml(item.name)}</h3>
        <p>${escapeHtml(item.description || '')}</p>
        <div class="meta">
          <span>${item.category || ''}</span>
          <strong>$${item.price?.toFixed ? item.price.toFixed(2) : item.price}</strong>
        </div>
        <div class="actions">
          <button class="edit" data-id="${item.id}">Edit</button>
          <button class="delete" data-id="${item.id}">Delete</button>
        </div>
      </div>
    `;
    foodListEl.appendChild(card);

    card.querySelector('.edit').addEventListener('click', () => openFoodModal(item));
    card.querySelector('.delete').addEventListener('click', async () => {
      if (!confirm('Delete this item?')) return;
      try {
        const res = await fetch(`/api/owner/food/${item.id}`, { method: 'DELETE' });
        if (!res.ok) throw 'Delete failed';
        showToast('Deleted');
        await loadFoodItems();
      } catch (err) {
        showToast('Delete failed', true);
      }
    });
  }

  function openFoodModal(item = null) {
    modalFood.classList.remove('hidden');
    foodForm.reset();
    foodForm.id.value = item ? item.id : '';
    document.getElementById('food-form-title').textContent = item ? 'Edit Food Item' : 'New Food Item';
    if (item) {
      foodForm.name.value = item.name || '';
      foodForm.description.value = item.description || '';
      foodForm.price.value = item.price || '';
      foodForm.category.value = item.category || '';
    }
  }

  function closeFoodModal() {
    modalFood.classList.add('hidden');
  }

  // Deals handling
  const dealsListEl = document.getElementById('deals-list');
  const dealsLoading = document.getElementById('deals-loading');
  const btnNewDeal = document.getElementById('btn-new-deal');
  const modalDeal = document.getElementById('modal-deal');
  const dealForm = document.getElementById('deal-form');
  const dealCancel = document.getElementById('deal-cancel');

  btnNewDeal.addEventListener('click', () => openDealModal());
  dealCancel.addEventListener('click', closeDealModal);

  dealForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = dealForm.id.value;
    const url = id ? `/api/owner/deals/${id}` : '/api/owner/deals';
    const method = id ? 'PUT' : 'POST';
    const data = new URLSearchParams(new FormData(dealForm));
    showLoading(dealsLoading, true);
    try {
      const res = await fetch(url, { method, body: data });
      if (!res.ok) throw await res.text();
      await loadDeals();
      showToast('Saved');
      closeDealModal();
    } catch (err) {
      showToast('Error: ' + err, true);
    } finally {
      showLoading(dealsLoading, false);
    }
  });

  async function loadDeals() {
    dealsListEl.innerHTML = '';
    showLoading(dealsLoading, true);
    try {
      const res = await fetch('/api/owner/deals');
      const items = await res.json();
      items.forEach(renderDeal);
    } catch (err) {
      showToast('Failed to load deals', true);
    } finally {
      showLoading(dealsLoading, false);
    }
  }

  function renderDeal(d) {
    const card = document.createElement('div');
    card.className = 'deal-card';
    card.innerHTML = `
      <h3>${escapeHtml(d.title)}</h3>
      <p>${escapeHtml(d.description || '')}</p>
      <div class="meta">
        <span>${d.discountType === 'PERCENT' ? d.discountValue + '%' : '$' + d.discountValue}</span>
        <span>${d.startDate || ''} → ${d.endDate || ''}</span>
      </div>
      <div class="actions">
        <button class="edit-deal" data-id="${d.id}">Edit</button>
        <button class="delete-deal" data-id="${d.id}">Delete</button>
      </div>
    `;
    dealsListEl.appendChild(card);

    card.querySelector('.edit-deal').addEventListener('click', async () => {
      openDealModal(d);
    });

    card.querySelector('.delete-deal').addEventListener('click', async () => {
      if (!confirm('Delete this deal?')) return;
      try {
        const res = await fetch(`/api/owner/deals/${d.id}`, { method: 'DELETE' });
        if (!res.ok) throw 'Delete failed';
        showToast('Deleted');
        await loadDeals();
      } catch (err) {
        showToast('Delete failed', true);
      }
    });
  }

  function openDealModal(d = null) {
    modalDeal.classList.remove('hidden');
    dealForm.reset();
    dealForm.id.value = d ? d.id : '';
    document.getElementById('deal-form-title').textContent = d ? 'Edit Deal' : 'New Deal';
    if (d) {
      dealForm.title.value = d.title || '';
      dealForm.description.value = d.description || '';
      dealForm.discountValue.value = d.discountValue || '';
      dealForm.discountType.value = d.discountType || 'PERCENT';
      dealForm.startDate.value = d.startDate || '';
      dealForm.endDate.value = d.endDate || '';
      dealForm.applicableItemIds.value = d.applicableItemIds || '';
    }
  }

  function closeDealModal() {
    modalDeal.classList.add('hidden');
  }

  // utilities
  function showToast(message, isError) {
    toast.textContent = message;
    toast.classList.remove('hidden');
    toast.style.background = isError ? '#d9534f' : '#28a745';
    setTimeout(() => toast.classList.add('hidden'), 3500);
  }

  function showLoading(el, on) {
    el.style.display = on ? 'block' : 'none';
  }

  function escapeHtml(s) {
    if (!s) return '';
    return String(s).replace(/[&<>"']/g, function (m) {
      return ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]);
    });
  }

  // initial load
  loadFoodItems();
  loadDeals();
});