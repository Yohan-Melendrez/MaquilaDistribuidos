document.addEventListener('DOMContentLoaded', () => {
    const QA = 'http://localhost:8081/qa';
    const lote = JSON.parse(localStorage.getItem('loteSeleccionado'));
    if (!lote) return alert('Sin lote') && location.reload();
  
    // Rellenar datos
    document.getElementById('loteNombre').value    = lote.nombreLote;
    document.getElementById('loteCantidad').value = lote.productos[0]?.cantidad || 0;
    document.getElementById('loteDescripcion').value =
      lote.productos[0]?.descripcion || '';
  
    const modal      = document.getElementById('modalAsignar');
    const errorBox   = document.getElementById('modalError');
    const selInspect = document.getElementById('selectEmpleado');
    const btnAsig    = document.getElementById('confirmarAsignacion');
    const btnCanc    = document.getElementById('cancelarAsignacion');
  
    // Abrir y cancelar
    document.querySelector('.asignar').onclick = () => {
      errorBox.style.display = 'none';
      modal.style.display = 'flex';
      cargarInspectores();
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
            o.text  = i.nombre;
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
      const idI = parseInt(selInspect.value,10);
      if (!idI) return showError('Debes elegir un inspector.');
  
      const payload = {
        idLote: lote.idLote,
        idInspector: idI
      };
  
      fetch(`${QA}/asignarLote`, {
        method: 'POST',
        headers: { 'Content-Type':'application/json' },
        body: JSON.stringify(payload)
      })
      .then(r => r.ok ? r.json() : Promise.reject())
      .then(res => {
        // Reducir cantidad en pantalla + storage
        const inp = document.getElementById('loteCantidad');
        const n = Math.max(parseInt(inp.value,10)-1,0);
        inp.value = n;
        lote.productos[0].cantidad = n;
        localStorage.setItem('loteSeleccionado', JSON.stringify(lote));
  
        // Mensaje de éxito
        document.querySelector('.modal-content h3').textContent = res.mensaje;
        errorBox.style.display = 'none';
      })
      .catch(_ => showError('Ocurrió un error al asignar el lote.'));
    };
  });
  