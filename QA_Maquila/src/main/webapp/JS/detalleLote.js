/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const lote = JSON.parse(localStorage.getItem('loteSeleccionado'));

    if (lote) {
        document.getElementById('loteId').value = lote.id;
        document.getElementById('loteNombre').value = lote.nombre;
        document.getElementById('loteCantidad').value = lote.cantidad;
        document.getElementById('loteDescripcion').value = lote.descripcion;
    } else {
        alert('No se encontró información del lote.');
        window.location.href = 'lotesDisponibles.html';
    }
});
document.addEventListener('DOMContentLoaded', () => {
    const asignarBtn = document.querySelector('.asignar');
    const modal = document.getElementById('modalAsignar');
    const modalContent = modal.querySelector('.modal-content');
    const cancelarBtn = document.getElementById('cancelarAsignacion');
    const confirmarBtn = document.getElementById('confirmarAsignacion');
    const selectEmpleado = document.getElementById('selectEmpleado');

    asignarBtn.addEventListener('click', () => {
        resetModal();
        modal.style.display = 'flex';
    });

    cancelarBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    confirmarBtn.addEventListener('click', () => {
        const empleadoId = selectEmpleado.value;
        const empleadoTexto = selectEmpleado.options[selectEmpleado.selectedIndex].text;

        if (!empleadoId) {
            mostrarMensajeError("Selecciona un empleado antes de continuar.");
            return;
        }

        modalContent.innerHTML = `
            <h3>Lote Asignado Exitosamente</h3>
            <p>El lote fue asignado a <strong>${empleadoTexto}</strong>.</p>
            <button class="btn-confirmar" id="cerrarModalFinal">Cerrar</button>
        `;

        document.getElementById('cerrarModalFinal').addEventListener('click', () => {
            modal.style.display = 'none';
        });
    });

    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    function resetModal() {
        modalContent.innerHTML = `
            <h3>Asignar a Empleado</h3>
            <select id="selectEmpleado">
                <option value="" disabled selected>Selecciona un empleado</option>
                <option value="1">Carlos Ramírez</option>
                <option value="2">Ana Torres</option>
                <option value="3">Luis Gómez</option>
                <option value="4">María Pérez</option>
            </select>
            <div class="modal-botones">
                <button class="btn-cancelar" id="cancelarAsignacion">Cancelar</button>
                <button class="btn-confirmar" id="confirmarAsignacion">Asignar</button>
            </div>
        `;

        document.getElementById('cancelarAsignacion').addEventListener('click', () => {
            modal.style.display = 'none';
        });

        document.getElementById('confirmarAsignacion').addEventListener('click', () => {
            const empleadoId = document.getElementById('selectEmpleado').value;
            const empleadoTexto = document.getElementById('selectEmpleado').options[document.getElementById('selectEmpleado').selectedIndex].text;

            if (!empleadoId) {
                mostrarMensajeError("Selecciona un empleado antes de continuar.");
                return;
            }

            modalContent.innerHTML = `
                <h3>Lote Asignado Exitosamente</h3>
                <p>El lote fue asignado a <strong>${empleadoTexto}</strong>.</p>
                <button class="btn-confirmar" id="cerrarModalFinal">Cerrar</button>
            `;

            document.getElementById('cerrarModalFinal').addEventListener('click', () => {
                modal.style.display = 'none';
            });
        });
    }

    function mostrarMensajeError(mensaje) {
        const errorParrafo = document.createElement('p');
        errorParrafo.style.color = 'red';
        errorParrafo.textContent = mensaje;

        const existente = modalContent.querySelector('p[style="color: red;"]');
        if (!existente) {
            modalContent.insertBefore(errorParrafo, modalContent.querySelector('.modal-botones'));
        }
    }
});


