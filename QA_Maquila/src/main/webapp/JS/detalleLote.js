document.addEventListener('DOMContentLoaded', () => {
    const QA = 'https://localhost:8082/qa';
    let lote = JSON.parse(localStorage.getItem('loteSeleccionado'));

    if (!lote)
        return alert('Sin lote') && location.reload();

    document.getElementById('loteNombre').value = lote.nombreLote;
    document.getElementById('loteCantidad').value = lote.productos[0]?.cantidad || 0;
    document.getElementById('loteDescripcion').value = lote.productos[0]?.descripcion || '';

    const modal = document.getElementById('modalAsignar');
    const errorBox = document.getElementById('modalError');
    const selInspect = document.getElementById('selectEmpleado');
    const btnAsig = document.getElementById('confirmarAsignacion');
    const btnCanc = document.getElementById('cancelarAsignacion');

    if (lote.idInspector) {
        const nombreInspector = lote.productos[0]?.inspector || "Desconocido";
        document.querySelector('.asignar').textContent = `Lote ya asignado a ${nombreInspector}`;
        btnAsig.disabled = true;

        const content = modal.querySelector('.modal-content');
        content.innerHTML = `
            <h3>Este lote ya está asignado a ${nombreInspector}</h3>
            <button id="cerrarModalError">Cerrar</button>
        `;
        modal.style.display = 'flex';

        document.getElementById('cerrarModalError').onclick = () => {
            modal.style.display = 'none';
        };
    }

    document.querySelector('.asignar').onclick = () => {
        fetch(`${QA}/lote/${lote.idLote}`)
                .then(r => r.ok ? r.json() : Promise.reject('No se pudo obtener el lote actualizado'))
                .then(loteActualizado => {
                    lote = loteActualizado;
                    localStorage.setItem('loteSeleccionado', JSON.stringify(lote));

                    if (lote.idInspector) {
                        // ✅ Mostrar mensaje de lote ya asignado en el modal existente
                        const content = modal.querySelector('.modal-content');
                        content.innerHTML = `
                    <h3>Este lote ya está asignado</h3>
                    <p>El lote <strong>${lote.nombreLote}</strong> ya fue asignado a un inspector.</p>
                    <button id="cerrarModal">Cerrar</button>
                `;
                        modal.style.display = 'flex';
                        document.getElementById('cerrarModal').onclick = () => {
                            modal.style.display = 'none';
                        };
                        return;
                    }

                    // ✅ Mostrar selector de inspectores si el lote no está asignado
                    errorBox.style.display = 'none';
                    selInspect.style.display = 'block';
                    btnAsig.style.display = 'inline-block';
                    btnCanc.style.display = 'inline-block';
                    modal.style.display = 'flex';
                    cargarInspectores();
                })
                .catch(err => showError(err));
    };

    btnCanc.onclick = () => modal.style.display = 'none';

    function cargarInspectores() {
        fetch(`${QA}/inspectores`)
                .then(r => r.ok ? r.json() : Promise.reject())
                .then(list => {
                    selInspect.innerHTML = '<option value="" disabled selected>Selecciona un empleado</option>';
                    list.forEach(i => {
                        const o = document.createElement('option');
                        o.value = i.idInspector;
                        o.text = i.nombre;
                        selInspect.append(o);
                    });
                })
                .catch(_ => showError('No pudimos cargar los inspectores.'));
    }

    function showError(msg) {
        errorBox.textContent = msg;
        errorBox.style.display = 'block';
    }

    btnAsig.onclick = () => {
        const idI = parseInt(selInspect.value, 10);
        if (!idI)
            return showError('Debes elegir un inspector.');

        fetch(`${QA}/lote/${lote.idLote}`)
                .then(r => r.ok ? r.json() : Promise.reject())
                .then(loteRefrescado => {
                    if (loteRefrescado.idInspector) {
                        return showError(`Este lote ya está asignado a otro inspector`);
                    }

                    const payload = {
                        idLote: lote.idLote,
                        idInspector: idI
                    };

                    return fetch(`${QA}/asignarLote`, {
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify(payload)
                    })
                            .then(r => r.ok ? r.text() : Promise.reject('Error en respuesta del servidor'))
                            .then(res => {
                                if (res === 'YA_ASIGNADO') {
                                    const content = modal.querySelector('.modal-content');
                                    content.innerHTML = `<h3>Este lote ya está asignado a otro inspector </h3>
                                    <button id="cerrarModalYaAsignado">Cerrar</button>`;
                                    selInspect.style.display = 'none';
                                    btnAsig.style.display = 'none';
                                    btnCanc.style.display = 'none';
                                    errorBox.style.display = 'none';
                                    modal.style.display = 'flex';
                                    document.getElementById('cerrarModalYaAsignado').onclick = () => {
                                        modal.style.display = 'none';
                                    };
                                    return;
                                }

                                if (res === 'ASIGNACION_EXITOSA') {
                                    lote.idInspector = idI;
                                    localStorage.setItem('loteSeleccionado', JSON.stringify(lote));

                                    const content = modal.querySelector('.modal-content');
                                    content.innerHTML = `<h3>Lote asignado con éxito al empleado</h3>`;
                                    selInspect.style.display = 'none';
                                    btnAsig.style.display = 'none';
                                    btnCanc.style.display = 'none';
                                    errorBox.style.display = 'none';

                                    setTimeout(() => {
                                        modal.style.display = 'none';
                                    }, 400);
                                } else {
                                    showError('Respuesta inesperada del servidor.');
                                }
                            });
                })
                .catch(_ => showError('Ocurrió un error al asignar el lote.'));
    };
});
