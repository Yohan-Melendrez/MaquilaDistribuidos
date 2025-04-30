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

// Crear el lote y guardar en sessionStorage, luego mostrar modal
function crearLote() {
    const loteFinal = productos.filter(p => p.cantidad > 0);
    if (loteFinal.length === 0) {
        alert("Agrega al menos un producto al lote.");
        return;
    }

    const nombreLote = document.getElementById("nombreLote").value.trim();
    if (!nombreLote) {
        mostrarModalError("Debes ingresar un nombre para el lote.");
        return;
    }
    

    const lote = {
        id: Date.now(),
        descripcion: nombreLote,
        productos: loteFinal
    };


    const lotesGuardados = JSON.parse(sessionStorage.getItem("lotesCreados")) || [];
    lotesGuardados.push(lote);
    sessionStorage.setItem("lotesCreados", JSON.stringify(lotesGuardados));

    mostrarModalConfirmacion();
}

// Modal animado para confirmar creación (no envío)
function mostrarModalConfirmacion() {
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-contenido">
            <i class="fas fa-box-open fa-bounce fa-3x" style="color:#2d6073;"></i>
            <p>¡Lote creado exitosamente!</p>
        </div>
    `;
    document.body.appendChild(modal);
    modal.style.display = 'flex';

    setTimeout(() => {
        modal.remove();
        window.location.href = 'envioLotes.html';
    }, 1800);
}

document.getElementById('crearLoteBtn').addEventListener('click', crearLote);

// Inicializar
renderizarProductos();

function mostrarModalError(mensaje) {
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-contenido" style="background:#ffe6e6;">
            <i class="fas fa-triangle-exclamation fa-shake fa-2x" style="color:#c0392b;"></i>
            <p style="color:#c0392b; font-weight:bold;">${mensaje}</p>
        </div>
    `;
    document.body.appendChild(modal);
    modal.style.display = 'flex';

    setTimeout(() => {
        modal.remove();
    }, 1400);
}

