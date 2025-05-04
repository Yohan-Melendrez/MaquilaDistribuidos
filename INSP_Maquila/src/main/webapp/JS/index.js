// JS/index.js
const API_ROOT = 'http://localhost:9090';
const select     = document.getElementById('inspectorSelect');
const loginBtn   = document.getElementById('loginBtn');

fetch(`${API_ROOT}/inspeccion/inspectores`)
  .then(res => {
    if (!res.ok) throw new Error('No se pudo cargar inspectores.');
    return res.json();
  })
  .then(inspectores => {
    inspectores.forEach(i => {
      const opt = document.createElement('option');
      opt.value       = i.id;      // <-- Aquí ponemos el ID numérico
      opt.textContent = i.nombre;  // <-- El texto sí sigue siendo el nombre
      select.appendChild(opt);
    });
  })
  .catch(err => {
    console.error(err);
    alert('Error cargando inspectores. Revisa la consola.');
  });

select.addEventListener('change', () => {
  loginBtn.disabled = select.value === '';
});

loginBtn.addEventListener('click', () => {
  // Guardamos **ambos** en sessionStorage
  const inspectorId   = select.value;
  const inspectorName = select.options[select.selectedIndex].text;
  sessionStorage.setItem('inspectorId', inspectorId);
  sessionStorage.setItem('inspectorName', inspectorName);
  window.location.href = 'menuLotes.html';
});
