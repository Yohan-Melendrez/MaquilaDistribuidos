const productos = [
    { id: "A9B7C2D1", imagen: "img/producto.png" },
    { id: "E9B6C2H1", imagen: "img/producto.png" },
    { id: "A9B7R2D3", imagen: "img/producto.png" }
];

const contenedor = document.getElementById('productosContainer');

productos.forEach(producto => {
    const div = document.createElement('div');
    div.className = 'producto';
    div.innerHTML = `
        <img src="${producto.imagen}" alt="Producto">
        <div>${producto.id}</div>
    `;
    contenedor.appendChild(div);
});