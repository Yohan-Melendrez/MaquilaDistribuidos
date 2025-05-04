/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('formNuevoReporte');
    const tipoDefectoSelect = document.getElementById('tipoDefecto');
    var nombreError;

    // Cargar errores desde el backend
    fetch('http://localhost:9091/reportes/errores') // Cambia al endpoint real
            .then(res => res.json())
            .then(data => {
                data.forEach(error => {
                    const option = document.createElement('option');
                    option.value = error.id; // o error.codigo si es tu identificador
                    option.textContent = error.nombre;
                    nombreError= error.nombre;
                    tipoDefectoSelect.appendChild(option);
                });
            })
            .catch(err => console.error('Error cargando errores:', err));

            // Manejo de envío del formulario
    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const errorId = tipoDefectoSelect.value;
        const desde = document.getElementById('fechaDesde').value;
        const hasta = document.getElementById('fechaHasta').value;

        if (!errorId || !desde || !hasta) {
            alert('Por favor, completa todos los campos.');
            return;
        }

        const url = 'http://localhost:9091/reportes/generarReporte';

        // Agregar hora específica
        const inicio = `${desde}T00:00:00`;
        const fin = `${hasta}T23:59:00`;

        const filtro = {
            inicio: inicio,
            fin: fin,
            idError: errorId,
        };

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(filtro)
        })
                .then(res => {
                    if (!res.ok)
                        throw new Error('Error en la respuesta del servidor');
                    return res.json();
                })
                .then(data => {
                    alert('Reporte generado y guardado con éxito.');
                    window.location.href = 'reportes.html';
                })
                .catch(err => {
                    console.error('Error generando el reporte:', err);
                    alert('Hubo un error al generar el reporte.');
                });

    });
});


