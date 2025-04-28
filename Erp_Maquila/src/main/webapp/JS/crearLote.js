const productos = [
    { nombre: "Rasuradoras XXX", cantidad: 1 },
    { nombre: "Jabon marca XXX", cantidad: 2 },
    { nombre: "Secadora marca XXX", cantidad: 1 },
    { nombre: "Shampoo marca XXX", cantidad: 5 },
    { nombre: "Cepillos XXX", cantidad: 1 }
];

// Función para renderizar la lista de productos
function renderizarProductos() {
    const contenedor = document.getElementById('contenedorProductos');
    contenedor.innerHTML = '';

    productos.forEach((producto, index) => {
        const item = document.createElement('div');
        item.className = 'producto';

        item.innerHTML = `
            <span>x${producto.cantidad} ${producto.nombre}</span>
            <div class="botones">
                <button onclick="disminuirCantidad(${index})">-</button>
                <button onclick="aumentarCantidad(${index})">+</button>
            </div>
        `;

        contenedor.appendChild(item);
    });
}

// Funciones para aumentar/disminuir cantidad
function aumentarCantidad(index) {
    productos[index].cantidad++;
    renderizarProductos();
}

function disminuirCantidad(index) {
    if (productos[index].cantidad > 0) {
        productos[index].cantidad--;
        renderizarProductos();
    }
}

// Función para crear el lote (puedes personalizar qué quieres que haga)
function crearLote() {
    const loteFinal = productos.filter(p => p.cantidad > 0);
    console.log("Lote creado:", loteFinal);
    alert("¡Lote creado exitosamente!");
}

document.getElementById('crearLoteBtn').addEventListener('click', crearLote);

// Inicializar
renderizarProductos();