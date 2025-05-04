/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
    const lote = JSON.parse(localStorage.getItem('loteSeleccionado'));

    if (!lote) {
        alert('No se encontró información del lote.');
        window.location.href = 'lotesDisponibles.html';
        return;
    }

    document.getElementById('loteNombre').value = lote.nombreLote;
    document.getElementById('loteCantidad').value = lote.productos.length > 0 ? lote.productos[0].cantidad : 0;
    document.getElementById('loteDescripcion').value = lote.productos.length > 0 ? lote.productos[0].descripcion : 'Descripción no disponible';
});

document.addEventListener('DOMContentLoaded', () => {
    const asignarBtn = document.querySelector('.asignar');
    const modal = document.getElementById('modalAsignar');
    const modalContent = modal.querySelector('.modal-content');
    const lote = JSON.parse(localStorage.getItem('loteSeleccionado'));

    asignarBtn.addEventListener('click', () => {
        resetModal();
        modal.style.display = 'flex';
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
        </select>
        <div class="modal-botones">
            <button class="btn-cancelar" id="cancelarAsignacion">Cancelar</button>
            <button class="btn-confirmar" id="confirmarAsignacion">Asignar</button>
        </div>
    `;

        fetch('http://localhost:8081/qa/inspectores')
                .then(response => response.json())
                .then(inspectores => {
                    const selectEmpleado = document.getElementById('selectEmpleado');
                    inspectores.forEach(inspector => {
                        const option = document.createElement('option');
                        option.value = inspector.idInspector;
                        option.textContent = inspector.nombre;  // Asegúrate de que en tu entidad tengas `getNombre()`
                        selectEmpleado.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error al cargar inspectores:', error);
                    mostrarMensajeError("Error al cargar inspectores.");
                });

        document.getElementById('cancelarAsignacion').addEventListener('click', () => {
            modal.style.display = 'none';
        });

        document.getElementById('confirmarAsignacion').addEventListener('click', () => {
            const selectEmpleado = document.getElementById('selectEmpleado');
            const empleadoId = selectEmpleado.value;
            const empleadoTexto = selectEmpleado.options[selectEmpleado.selectedIndex].text;

            if (!empleadoId) {
                mostrarMensajeError("Selecciona un empleado antes de continuar.");
                return;
            }

            const asignacionLoteDTO = {
                idLote: lote.id,
                idInspector: empleadoId,
            };

            fetch('http://localhost:8081/qa/asignarLote', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(asignacionLoteDTO),
            })
                    .then(response => response.json())
                    .then(data => {
                        modalContent.innerHTML = `
                    <h3>Lote Asignado Exitosamente</h3>
                    <p>El lote fue asignado a <strong>${empleadoTexto}</strong>.</p>
                    <button class="btn-confirmar" id="cerrarModalFinal">Cerrar</button>
                `;
                        document.getElementById('cerrarModalFinal').addEventListener('click', () => {
                            modal.style.display = 'none';
                        });
                    })
                    .catch(error => {
                        console.error('Error al asignar el lote:', error);
                        mostrarMensajeError("Ocurrió un error al asignar el lote.");
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



