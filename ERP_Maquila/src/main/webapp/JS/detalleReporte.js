document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const idError = parseInt(params.get('idError'));

    const dataGuardada = sessionStorage.getItem('reportesGenerados');

    if (!dataGuardada || isNaN(idError)) {
        document.getElementById('detalleInfo').innerHTML = '<p>No se encontró la información del reporte.</p>';
        return;
    }

    const reportes = JSON.parse(dataGuardada);

    const reporte = reportes.find(r => r.idError === idError);

    if (!reporte) {
        document.getElementById('detalleInfo').innerHTML = '<p>Reporte no encontrado.</p>';
        return;
    }

    document.getElementById('tipo').textContent = reporte.tipoDefecto;

    const inputs = document.querySelectorAll('.valor');
    if (inputs.length >= 5) {
        inputs[0].value = reporte.totalPiezasRechazadas || '';
        inputs[1].value = reporte.fechaComprendida || '';
        inputs[2].value = `$${reporte.costoTotalUsd} USD`;
        inputs[3].value = `$${reporte.costoTotalMxn} MXN`;
        inputs[4].value = reporte.detallesRechazo || '';
    }
});
