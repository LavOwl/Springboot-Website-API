document.querySelectorAll('.editableInput').forEach(input => {
    input.setAttribute('readonly', true);
    input.style.userSelect = "none";
    input.addEventListener('dblclick', (e) => {
        input.style.userSelect = "none";
        input.removeAttribute('readonly');
        input.style.userSelect = "none";
        requestAnimationFrame(() => {
            const val = e.target.value;
            e.target.value = '';
            e.target.value = val;
        });
        input.focus();
      });
      input.addEventListener('blur', () => {
        input.setAttribute('readonly', true);
      });
});