// JS/productosLote.js
const API_ROOT         = 'http://localhost:9090';
const contenedor       = document.getElementById('productosContainer');
const botonReportar    = document.getElementById('reportarBtn');
const botonCancelar    = document.getElementById('cancelarBtn');

const idLoteRaw   = sessionStorage.getItem('idLote');
const idLote      = parseInt(idLoteRaw, 10);

if (!idLote || isNaN(idLote)) {
  alert('No se seleccionó ningún lote.');
  window.location.href = 'menuLotes.html';
}

let productoSeleccionado = null;

fetch(`${API_ROOT}/inspeccion/productos-del-lote/${idLote}`)
  .then(res => {
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    return res.json();
  })
  .then(productos => {
    if (productos.length === 0) {
      contenedor.innerHTML = '<p>No hay productos en este lote.</p>';
      return;
    }
    productos.forEach(p => {
      const div = document.createElement('div');
      div.className = 'producto';
      div.innerHTML = `
        <img src="img/producto.png" alt="Producto">
        <div>${p.nombre}</div>
      `;
      div.addEventListener('click', () => {
        productoSeleccionado = p.idProducto;
        sessionStorage.setItem('idProducto', productoSeleccionado);
        document.querySelectorAll('.producto').forEach(x => x.classList.remove('seleccionado'));
        div.classList.add('seleccionado');
        botonReportar.disabled = false;
      });
      contenedor.appendChild(div);
    });
  })
  .catch(err => {
    console.error('Error al cargar productos:', err);
    contenedor.innerHTML = '<p>Error al cargar productos.</p>';
  });

botonReportar.addEventListener('click', () => {
  if (productoSeleccionado) {
    window.location.href = 'fallasProducto.html';
  }
});
botonCancelar.addEventListener('click', () => {
  window.location.href = 'menuLotes.html';
});
