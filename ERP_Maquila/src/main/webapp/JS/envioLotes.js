/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const listaLotes = document.getElementById('listaLotes');

    // Crear el modal de confirmación
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.style.display = 'none';

    const modalContent = document.createElement('div');
    modalContent.className = 'modal-content';

    const mensaje = document.createElement('p');
    mensaje.textContent = 'Lote enviado exitosamente.';

    const closeButton = document.createElement('button');
    closeButton.className = 'close-button';
    closeButton.textContent = 'Cerrar';
    closeButton.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    modalContent.appendChild(mensaje);
    modalContent.appendChild(closeButton);
    modal.appendChild(modalContent);
    document.body.appendChild(modal);

    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    // Obtener lotes desde el backend
    fetch('https://localhost:8081/lotes')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al obtener los lotes');
                }
                return response.json();
            })
            .then(lotes => {
                lotes.forEach(lote => {
                    const item = document.createElement('div');
                    item.className = 'lote-item';

                    const descripcion = document.createElement('span');
                    descripcion.textContent = `Lote ${lote.idLote} - ${lote.nombreLote}`;

                    const botonVer = document.createElement('button');
                    botonVer.className = 'ver-btn';
                    botonVer.textContent = 'Enviar lote';
                    botonVer.addEventListener('click', () => {
                        fetch(`https://localhost:8081/lotes/${lote.idLote}/enviar`, {
                            method: 'POST'
                        })
                                .then(response => {
                                    if (!response.ok) {
                                        throw new Error('Error al enviar el lote');
                                    }
                                    mensaje.textContent = `Lote ${lote.nombreLote} enviado exitosamente.`;
                                    modal.style.display = 'flex';
                                })
                                .catch(error => {
                                    console.error('Error:', error);
                                    mensaje.textContent = `Error al enviar el lote: ${error.message}`;
                                    modal.style.display = 'flex';
                                });
                    });


                    item.appendChild(descripcion);
                    item.appendChild(botonVer);
                    listaLotes.appendChild(item);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                listaLotes.textContent = 'No se pudieron cargar los lotes.';
            });
});