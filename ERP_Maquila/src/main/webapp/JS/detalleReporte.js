/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
    // 1. Obtener el ID del reporte desde la URL
    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');

    if (!id) {
        document.getElementById('detalleInfo').innerHTML = '<p>ID de reporte no proporcionado.</p>';
        return;
    }

    // 2. Hacer fetch al endpoint con el ID
    fetch(`http://localhost:9091/reportes/historial/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error al obtener reporte: ${response.status}`);
                }
                return response.json();
            })
            .then(reporte => {
                document.getElementById('tipo').textContent = reporte.tipoDefecto;

                const inputs = document.querySelectorAll('.valor');
                if (inputs.length >= 5) {
                    inputs[0].value = reporte.totalPiezasRechazadas || '';
                    inputs[1].value = reporte.fechaComprendida || '';  // nuevo campo de fecha
                    inputs[2].value = `$${reporte.costoTotalUsd} USD`;
                    inputs[3].value = `$${reporte.costoTotalMxn} MXN`;
                    inputs[4].value = reporte.detallesRechazo || '';
                }
            })

            .catch(error => {
                console.error('Error al cargar el detalle del reporte:', error);
                document.getElementById('detalleInfo').innerHTML = '<p>No se pudo cargar el reporte.</p>';
            });
});
