let productos = [];

// Renderiza la lista de productos sin mostrar el ID
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

// Aumenta la cantidad de un producto
function aumentarCantidad(index) {
  productos[index].cantidad++;
  renderizarProductos();
}

// Disminuye la cantidad de un producto
function disminuirCantidad(index) {
  if (productos[index].cantidad > 0) {
    productos[index].cantidad--;
    renderizarProductos();
  }
}

// Carga productos desde el backend
function cargarProductos() {
  fetch('https://localhost:8081/productos')
    .then(response => {
      if (!response.ok) {
        throw new Error("Error al obtener productos");
      }
      return response.json();
    })
    .then(data => {
      productos = data.map(p => ({ ...p, cantidad: 0 }));
      renderizarProductos();
    })
    .catch(error => {
      console.error('Error cargando productos:', error);
      alert("No se pudieron cargar los productos.");
    });
}

// Crear lote
function crearLote() {
  const nombreLote = prompt("Ingresa el nombre del lote:");
  if (!nombreLote || nombreLote.trim() === "") {
    alert("Debes ingresar un nombre válido para el lote.");
    return;
  }

  const productosSeleccionados = productos
    .filter(p => p.cantidad > 0)
    .map(p => ({
      idProducto: p.idProducto,
      cantidad: p.cantidad
    }));

  if (productosSeleccionados.length === 0) {
    alert("No has seleccionado productos para el lote.");
    return;
  }

  const dto = {
    nombreLote: nombreLote,
    productos: productosSeleccionados
  };

  fetch("https://localhost:8081/lotes", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(dto)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Error al crear el lote");
      }
      return response.text();
    })
    .then(msg => {
      alert(msg);
      location.reload();
    })
    .catch(error => {
      console.error("Error al crear lote:", error);
      alert("No se pudo crear el lote.");
    });
}

// Inicializar
document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('crearLoteBtn').addEventListener('click', crearLote);
  cargarProductos();
});
