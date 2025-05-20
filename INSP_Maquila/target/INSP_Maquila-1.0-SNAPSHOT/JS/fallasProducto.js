// JS/fallasProducto.js
const API_ROOT = 'https://localhost:9090';
const contenedor = document.getElementById('fallasContainer');
const btnReportar = document.getElementById('reportarBtn');
const btnCancelar = document.getElementById('cancelarBtn');

const idLoteRaw = sessionStorage.getItem('idLote');
const idProductoRaw = sessionStorage.getItem('idProducto');
const inspectorName = sessionStorage.getItem('inspectorName');
const inspectorId = sessionStorage.getItem('inspectorId');

const idLote = parseInt(idLoteRaw, 10);
const idProducto = parseInt(idProductoRaw, 10);

if (![idLote, idProducto, inspectorId].every(x => x && !isNaN(x))) {
    alert('Faltan datos para registrar la inspección.');
    window.location.href = 'menuLotes.html';
}

fetch(`${API_ROOT}/inspeccion/errores/${idProducto}`)
    .then(res => {
        if (!res.ok)
            throw new Error(`HTTP ${res.status}`);
        return res.json();
    })
    .then(fallas => {
        if (fallas.length === 0) {
            contenedor.innerHTML = '<p>No hay errores configurados para este producto.</p>';
            return;
        }
        fallas.forEach(e => {
            const labelText = e.nombre ?? 'Falla sin nombre';
            const div = document.createElement('div');
            div.className = 'falla';
            div.innerHTML = `
        <label>
          <input type="checkbox" class="falla-checkbox" data-id="${e.idError}">
          ${labelText}
        </label>
      `;
            contenedor.appendChild(div);
        });

    })
    .catch(err => {
        console.error('Error al cargar fallas:', err);
        contenedor.innerHTML = '<p>Error al cargar fallas.</p>';
    });

contenedor.addEventListener('change', () => {
    btnReportar.disabled = document.querySelectorAll('.falla-checkbox:checked').length === 0;
});

btnCancelar.addEventListener('click', () => {
    window.location.href = 'productosLote.html';
});

let enviado = false;

btnReportar.addEventListener('click', () => {
    if (enviado)
        return; // prevenir reenvío
    enviado = true;
    btnReportar.disabled = true;
    console.log('Enviando reporte...');

    const seleccionadas = Array
        .from(document.querySelectorAll('.falla-checkbox:checked'))
        .map(cb => parseInt(cb.dataset.id, 10));
    const detalleFalla = document.getElementById('detalleFalla').value.trim();

    const payload = {
        idLote,
        idProducto,
        inspector: inspectorName,
        erroresSeleccionados: seleccionadas,
        detalleError: detalleFalla
    };


    fetch(`${API_ROOT}/inspeccion/registrar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
        .then(res => {
            if (!res.ok)
                throw new Error(`HTTP ${res.status}`);
            document.getElementById('modalConfirmacion').style.display = 'flex';
            setTimeout(() => window.location.href = 'menuLotes.html', 1200);
        })
        .catch(err => {
            console.error('Error al reportar falla:', err);
            alert('Error al registrar inspección.');
            enviado = false; // permitir reintento en caso de error
            btnReportar.disabled = false;
        });
});
