// JS/menuLotes.js
const API_ROOT    = 'http://localhost:9090';
const contenedor  = document.getElementById('lotesContainer');
const salirBtn    = document.getElementById('salirBtn');

const inspectorIdRaw  = sessionStorage.getItem('inspectorId');
const inspectorId     = parseInt(inspectorIdRaw, 10);
const inspectorName   = sessionStorage.getItem('inspectorName');

if (!inspectorId || isNaN(inspectorId)) {
  alert('ID de inspector inválido. Vuelve a iniciar sesión.');
  window.location.href = 'index.html';
}

document.querySelector('.saludo').textContent = `Hola ${inspectorName}`;

fetch(`${API_ROOT}/inspeccion/lotes-asignados/${inspectorId}`)
  .then(res => {
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
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
    console.error('Error al obtener lotes:', err);
    contenedor.innerHTML = '<p>Error al cargar lotes.</p>';
  });

salirBtn.addEventListener('click', () => {
  sessionStorage.clear();
  window.location.href = 'index.html';
});
