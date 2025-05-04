// define aquí tu base de API (ajusta el puerto si lo cambias)
const API_BASE = 'http://localhost:9090/inspeccion';

const contenedor     = document.getElementById('productosContainer');
const botonReportar = document.getElementById('reportarBtn');
const botonCancelar = document.getElementById('cancelarBtn');

// Recuperar y validar ID del lote
const idLote = Number(sessionStorage.getItem('idLote'));
if (!idLote) {
    alert('No se seleccionó ningún lote.');
    window.location.href = 'menuLotes.html';
}

let productoSeleccionado = null;

// Cargar productos del backend
fetch(`${API_BASE}/productos-del-lote/${idLote}`)
    .then(response => {
        if (!response.ok) throw new Error('Error al obtener productos.');
        return response.json();
    })
    .then(productos => {
        if (productos.length === 0) {
            contenedor.innerHTML = '<p>No hay productos en este lote.</p>';
            return;
        }
        productos.forEach(producto => {
            const div = document.createElement('div');
            div.className = 'producto';
            div.innerHTML = `
                <img src="img/producto.png" alt="Producto">
                <div>${producto.nombre}</div>
            `;
            div.addEventListener('click', () => {
                productoSeleccionado = producto.idProducto;
                sessionStorage.setItem('idProducto', productoSeleccionado);
                // resaltado visual
                document.querySelectorAll('.producto').forEach(p => p.classList.remove('seleccionado'));
                div.classList.add('seleccionado');
                botonReportar.disabled = false;
            });
            contenedor.appendChild(div);
        });
    })
    .catch(error => {
        console.error(error);
        contenedor.innerHTML = '<p>Error al cargar productos.</p>';
    });

// Cuando pulsan “Reportar”, vamos a la lista de fallas
botonReportar.addEventListener('click', () => {
    if (productoSeleccionado) {
        window.location.href = 'fallasProducto.html';
    }
});

// “Cancelar” vuelve atrás
botonCancelar.addEventListener('click', () => {
    window.location.href = 'menuLotes.html';
});
