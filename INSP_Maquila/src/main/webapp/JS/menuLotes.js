// JS/menuLotes.js

// Define tu base de API aquí (ajusta puerto si cambia)
const API_BASE = 'http://localhost:9090/inspeccion';

const contenedor = document.getElementById('lotesContainer');
const salirBtn   = document.getElementById('salirBtn');

// Obtenemos el nombre del inspector de la sesión
const inspector = sessionStorage.getItem('inspector');
if (!inspector) {
  alert('No hay sesión activa.');
  window.location.href = 'index.html';
}

// Mostramos saludo (sobrescribe el placeholder “Hola Juan”)
document.querySelector('.saludo').textContent = `Hola ${inspector}`;

// Llamamos al endpoint de lotes asignados
fetch(`${API_BASE}/lotes-asignados/${encodeURIComponent(inspector)}`)
  .then(res => {
    if (!res.ok) throw new Error('Error al obtener lotes.');
    return res.json();
  })
  .then(lotes => {
    if (lotes.length === 0) {
      contenedor.innerHTML = '<p>No tienes lotes asignados.</p>';
      return;
    }
    lotes.forEach(lote => {
      const div = document.createElement('div');
      div.className = 'lote';
      div.innerHTML = `
        <img src="img/box_icon.png" alt="Caja">
        <div class="loteNombre">${lote.nombreLote}</div>
      `;
      div.addEventListener('click', () => {
        sessionStorage.setItem('idLote', lote.idLote);
        window.location.href = 'productosLote.html';
      });
      contenedor.appendChild(div);
    });
  })
  .catch(err => {
    console.error(err);
    contenedor.innerHTML = '<p>Error al cargar lotes.</p>';
  });

// Botón “Salir”
salirBtn.addEventListener('click', () => {
  sessionStorage.clear();
  window.location.href = 'index.html';
});
