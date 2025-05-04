// JS/fallasProducto.js

// Define aquí tu base de API (ajusta el puerto si lo cambias)
const API_BASE = 'http://localhost:9090/inspeccion';

const contenedor = document.getElementById('fallasContainer');
const btnReportar = document.getElementById('reportarBtn');
const btnCancelar = document.getElementById('cancelarBtn');

// Datos necesarios para el registro
const idLote     = Number(sessionStorage.getItem('idLote'));
const idProducto = Number(sessionStorage.getItem('idProducto'));
const inspector  = sessionStorage.getItem('inspector');

if (!idLote || !idProducto || !inspector) {
  alert('Faltan datos para registrar la inspección.');
  window.location.href = 'menuLotes.html';
}

// 1. Cargar errores reales desde backend
fetch(`${API_BASE}/errores/${idProducto}`)
  .then(response => {
    if (!response.ok) throw new Error('Error al obtener errores.');
    return response.json();
  })
  .then(fallas => {
    if (fallas.length === 0) {
      contenedor.innerHTML = '<p>No hay errores configurados para este producto.</p>';
      return;
    }
    fallas.forEach(falla => {
      const div = document.createElement('div');
      div.className = 'falla';
      div.innerHTML = `
        <label class="falla-label">
          ${falla.nombre}
          <input
            type="checkbox"
            class="falla-checkbox"
            data-id="${falla.idError}"
          >
        </label>
      `;
      contenedor.appendChild(div);
    });
  })
  .catch(error => {
    console.error(error);
    contenedor.innerHTML = '<p>Error al cargar fallas.</p>';
  });

// 2. Habilitar botón si hay una falla marcada
contenedor.addEventListener('change', () => {
  const seleccionadas = document.querySelectorAll('.falla-checkbox:checked');
  btnReportar.disabled = seleccionadas.length === 0;
});

// 3. Acción: Cancelar
btnCancelar.addEventListener('click', () => {
  window.location.href = 'productosLote.html';
});

// 4. Acción: Reportar
btnReportar.addEventListener('click', () => {
  const seleccionadas = document.querySelectorAll('.falla-checkbox:checked');
  const erroresSeleccionados = Array.from(seleccionadas)
    .map(input => parseInt(input.dataset.id, 10));

  const payload = {
    idLote,
    idProducto,
    inspector,
    erroresSeleccionados
  };

  fetch(`${API_BASE}/registrar`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  })
  .then(response => {
    if (!response.ok) throw new Error('Error al registrar inspección.');
    // Mostrar modal de confirmación (debes tenerlo en tu HTML)
    document.getElementById('modalConfirmacion').style.display = 'flex';
    setTimeout(() => window.location.href = 'menuLotes.html', 1200);
  })
  .catch(error => {
    console.error(error);
    alert('Error al registrar inspección.');
  });
});
