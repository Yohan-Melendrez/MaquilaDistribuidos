// JS/index.js

// Base de tu API (ajusta el puerto si cambia)
const API_ROOT = 'http://localhost:9090';

// Elementos del DOM
const select = document.getElementById('inspectorSelect');
const loginBtn = document.getElementById('loginBtn');

// 1) Traer inspectores desde el backend
fetch(`${API_ROOT}/inspeccion/inspectores`)
  .then(res => {
    if (!res.ok) throw new Error('No se pudo cargar inspectores.');
    return res.json();
  })
  .then(inspectores => {
    inspectores.forEach(i => {
      const opt = document.createElement('option');
      opt.value = i.nombre;      // ejemplo: "Inspector A"
      opt.textContent = i.nombre;
      select.appendChild(opt);
    });
  })
  .catch(err => {
    console.error(err);
    alert('Error cargando inspectores. Revisa la consola.');
  });

// 2) Habilitar el botón solo si hay selección
select.addEventListener('change', () => {
  loginBtn.disabled = select.value === '';
});

// 3) Al hacer click, guardamos sesión y redirigimos
loginBtn.addEventListener('click', () => {
  sessionStorage.setItem('inspector', select.value);
  window.location.href = 'menuLotes.html';
});
