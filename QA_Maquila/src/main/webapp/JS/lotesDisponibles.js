/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const listaLotes = document.getElementById('listaLotes');

    // Realizar una solicitud GET al endpoint "/qa/lotes"
    fetch('http://localhost:8081/qa/lotes')
            .then(response => response.json())
            .then(lotes => {
                // Crear los lotes dinámicamente
                lotes.forEach(lote => {
                    const item = document.createElement('div');
                    item.className = 'lote-item';

                    const infoContainer = document.createElement('div');
                    infoContainer.className = 'lote-info';
                    infoContainer.style.display = 'flex';
                    infoContainer.style.alignItems = 'center';
                    infoContainer.style.gap = '15px';

                    const cantidad = document.createElement('span');
                    cantidad.textContent = `x${lote.cantidad}`;
                    cantidad.style.color = '#2C5D75';
                    cantidad.style.fontWeight = 'bold';
                    cantidad.style.minWidth = '30px';

                    const nombre = document.createElement('span');
                    nombre.textContent = lote.nombre;

                    infoContainer.appendChild(cantidad);
                    infoContainer.appendChild(nombre);

                    const botonesContainer = document.createElement('div');
                    botonesContainer.style.display = 'flex';
                    botonesContainer.style.gap = '10px';

                    const botonNotificar = document.createElement('button');
                    botonNotificar.className = 'ver-btn';
                    botonNotificar.textContent = 'Notificar llegada';

                    botonNotificar.addEventListener('click', () => {
                        // Aquí puedes integrar la lógica de notificación al servicio
                        // por ejemplo, hacer una llamada al servicio para notificar el defecto
                        fetch('/qa/notificarDefecto', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(lote),
                        })
                                .then(response => response.json())
                                .then(data => {
                                    console.log(data); // Verifica que la notificación se haya enviado correctamente
                                })
                                .catch(error => {
                                    console.error('Error al notificar defecto:', error);
                                });
                    });

                    const botonDetalles = document.createElement('button');
                    botonDetalles.className = 'ver-btn';
                    botonDetalles.textContent = 'Ver detalles';

                    botonDetalles.addEventListener('click', () => {
                        // Guardar los datos del lote seleccionado en localStorage
                        localStorage.setItem('loteSeleccionado', JSON.stringify(lote));
                        window.location.href = 'detalleLote.html';
                    });

                    botonesContainer.appendChild(botonNotificar);
                    botonesContainer.appendChild(botonDetalles);

                    item.appendChild(infoContainer);
                    item.appendChild(botonesContainer);
                    listaLotes.appendChild(item);
                });
            })
            .catch(error => {
                console.error('Error al cargar los lotes:', error);
            });
});

