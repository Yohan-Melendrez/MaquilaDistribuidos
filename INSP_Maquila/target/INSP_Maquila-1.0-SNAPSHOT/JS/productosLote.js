const productos = [
    { id: "A9B7C2D1", imagen: "img/producto.png" },
    { id: "E9B6C2H1", imagen: "img/producto.png" },
    { id: "A9B7R2D3", imagen: "img/producto.png" }
];

let productoSeleccionado = null;

const contenedor = document.getElementById('productosContainer');
const botonReportar = document.getElementById('reportarBtn');
const botonCancelar = document.getElementById('cancelarBtn');

// Renderiza los productos y habilita selección
productos.forEach(producto => {
    const div = document.createElement('div');
    div.className = 'producto';
    div.innerHTML = `
        <img src="${producto.imagen}" alt="Producto">
        <div>${producto.id}</div>
    `;

    div.addEventListener('click', () => {
        productoSeleccionado = producto.id;

        // Marcar visualmente el producto
        document.querySelectorAll('.producto').forEach(p => p.classList.remove('seleccionado'));
        div.classList.add('seleccionado');

        // Habilitar botón
        botonReportar.disabled = false;
    });

    contenedor.appendChild(div);
});

// Acción al hacer clic en "Reportar"
botonReportar.addEventListener('click', () => {
    if (productoSeleccionado) {
        window.location.href = 'fallasProducto.html';
    }
});

// Acción al hacer clic en "Cancelar"
botonCancelar.addEventListener('click', () => {
    window.location.href = 'menuLotes.html';
});
