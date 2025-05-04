document.addEventListener('DOMContentLoaded', () => {
    const QA = 'http://localhost:8081/qa';
    const lote = JSON.parse(localStorage.getItem('loteSeleccionado'));
    
    if (!lote)
        return alert('Sin lote') && location.reload();

    // Rellenar datos del lote
    document.getElementById('loteNombre').value = lote.nombreLote;
    document.getElementById('loteCantidad').value = lote.productos[0]?.cantidad || 0;
    document.getElementById('loteDescripcion').value = lote.productos[0]?.descripcion || '';

    const modal = document.getElementById('modalAsignar');
    const errorBox = document.getElementById('modalError');
    const selInspect = document.getElementById('selectEmpleado');
    const btnAsig = document.getElementById('confirmarAsignacion');
    const btnCanc = document.getElementById('cancelarAsignacion');

    // Verificar si ya tiene un inspector asignado al cargar la página
    if (lote.idInspector) {
        const nombreInspector = lote.productos[0]?.inspector || "Desconocido";
        document.querySelector('.asignar').textContent = `Lote ya asignado a ${nombreInspector}`;
        // Deshabilitar el botón de asignación y mostrar el modal
        btnAsig.disabled = true;
        
        // Mostrar el modal inmediatamente si ya está asignado
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

    // Abrir modal al dar clic en "Asignar"
    document.querySelector('.asignar').onclick = () => {
        if (lote.idInspector) {
            const nombreInspector = lote.productos[0]?.inspector || "Desconocido";
            const content = modal.querySelector('.modal-content');
            content.innerHTML = `
                <h3>Este lote ya está asignado a ${nombreInspector}</h3>
                <button id="cerrarModalError">Cerrar</button>
            `;
            modal.style.display = 'flex';
            document.getElementById('cerrarModalError').onclick = () => {
                modal.style.display = 'none';
            };
        } else {
            // Si no está asignado, permite seleccionar un inspector
            fetch(`${QA}/lote/${lote.idLote}`)
                .then(r => r.ok ? r.json() : Promise.reject('No se pudo obtener el lote actualizado'))
                .then(loteActualizado => {
                    localStorage.setItem('loteSeleccionado', JSON.stringify(loteActualizado));
                    errorBox.style.display = 'none';
                    selInspect.style.display = 'block';
                    btnAsig.style.display = 'inline-block';
                    btnCanc.style.display = 'inline-block';
                    modal.style.display = 'flex';
                    cargarInspectores();
                })
                .catch(err => showError(err));
        }
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

        // Verificar de nuevo antes de asignar
        if (lote.idInspector) {
            const nombreInspector = lote.productos[0]?.inspector || "Desconocido";
            return showError(`Este lote ya está asignado a ${nombreInspector}`);
        }

        const payload = {
            idLote: lote.idLote,
            idInspector: idI
        };

        fetch(`${QA}/asignarLote`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload)
        })
            .then(r => r.ok ? r.json() : Promise.reject())
            .then(res => {
                const inp = document.getElementById('loteCantidad');
                const n = Math.max(parseInt(inp.value, 10) - 1, 0);
                inp.value = n;
                lote.productos[0].cantidad = n;
                lote.idInspector = idI;
                localStorage.setItem('loteSeleccionado', JSON.stringify(lote));

                const content = modal.querySelector('.modal-content');
                content.innerHTML = `
                    <h3>Lote asignado con éxito al empleado</h3>
                `;

                selInspect.style.display = 'none';
                btnAsig.style.display = 'none';
                btnCanc.style.display = 'none';
                errorBox.style.display = 'none';

                setTimeout(() => {
                    modal.style.display = 'none';
                }, 500);
            })
            .catch(_ => showError('Ocurrió un error al asignar el lote.'));
    };
});
