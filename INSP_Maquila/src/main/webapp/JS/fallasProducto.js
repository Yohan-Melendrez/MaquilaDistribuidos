const contenedor = document.getElementById('fallasContainer');
const btnReportar = document.getElementById('reportarBtn');
const btnCancelar = document.getElementById('cancelarBtn');

// Datos necesarios para el registro
const idLote = sessionStorage.getItem("idLote");
const idProducto = sessionStorage.getItem("idProducto");
const inspector = sessionStorage.getItem("inspector");
const origen = "INSPECCION";

if (!idLote || !idProducto || !inspector) {
    alert("Faltan datos para registrar la inspección.");
    window.location.href = "menuLotes.html";
}

// 1. Cargar errores reales desde backend
fetch(`http://localhost:9090/inspeccion/errores/${idProducto}`)
    .then(response => response.json())
    .then(fallas => {
        fallas.forEach((falla, index) => {
            const div = document.createElement('div');
            div.className = 'falla';
            div.innerHTML = `
                <label class="falla-label">
                    ${falla.nombre}
                    <input type="checkbox" class="falla-checkbox" data-id="${falla.idError}">
                </label>
            `;
            contenedor.appendChild(div);
        });
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
    const erroresSeleccionados = Array.from(seleccionadas).map(input => parseInt(input.dataset.id));

    const payload = {
        idLote: parseInt(idLote),
        idProducto: parseInt(idProducto),
        inspector,
        erroresSeleccionados,
        origen
    };

    fetch("http://localhost:9090/inspeccion/registrar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al registrar inspección.");
        document.getElementById('modalConfirmacion').style.display = 'flex';
        setTimeout(() => window.location.href = 'menuLotes.html', 1200);
    })
    .catch(error => {
        console.error(error);
        alert("Error al registrar inspección.");
    });
});
