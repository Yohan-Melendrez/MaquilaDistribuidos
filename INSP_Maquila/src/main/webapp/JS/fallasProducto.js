const fallas = [
    "Problemas con el motor",
    "Problemas con las cuchillas",
    "Problemas con la batería",
    "Problemas con el interruptor de encendido"
];

const contenedor = document.getElementById('fallasContainer');
const btnReportar = document.getElementById('reportarBtn');
const btnCancelar = document.getElementById('cancelarBtn');

// Renderizar fallas
fallas.forEach((falla, index) => {
    const div = document.createElement('div');
    div.className = 'falla';
    div.innerHTML = `
        <label class="falla-label">
            ${falla}
            <input type="checkbox" class="falla-checkbox" id="falla-${index}">
        </label>
    `;
    contenedor.appendChild(div);
});

// Verificar si hay al menos una seleccionada
contenedor.addEventListener('change', () => {
    const seleccionadas = document.querySelectorAll('.falla-checkbox:checked');
    btnReportar.disabled = seleccionadas.length === 0;
});

// Acción: Cancelar
btnCancelar.addEventListener('click', () => {
    window.location.href = 'productosLote.html';
});

// Acción: Reportar (solo simulado)
btnReportar.addEventListener('click', () => {
    const modal = document.getElementById('modalConfirmacion');
    modal.style.display = 'flex';

    setTimeout(() => {
        window.location.href = 'menuLotes.html';
    }, 1200);
});

