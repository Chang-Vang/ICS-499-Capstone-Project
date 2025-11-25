// Show alert when button is clicked
document.querySelector('#myButton').addEventListener('click', () => {
    alert('Button was clicked!');
});

// Toggle visibility of an element
document.querySelector('#toggleBtn').addEventListener('click', () => {
    const content = document.querySelector('#content');
    content.style.display = (content.style.display === 'none') ? 'block' : 'none';
});