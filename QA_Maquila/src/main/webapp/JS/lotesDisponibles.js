/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const listaLotes = document.getElementById('listaLotes');

    fetch('http://localhost:8082/qa/lotes')
            .then(response => response.json())
            .then(lotes => {
                lotes.forEach(lote => {
                    const item = document.createElement('div');
                    item.className = 'lote-item';

                    const infoContainer = document.createElement('div');
                    infoContainer.className = 'lote-info';
                    infoContainer.style.display = 'flex';
                    infoContainer.style.alignItems = 'center';
                    infoContainer.style.gap = '15px';

                    const cantidad = document.createElement('span');
                    const cantidadProducto = lote.productos && lote.productos.length > 0 ? lote.productos[0].cantidad : 0;
                    cantidad.textContent = `x${cantidadProducto}`;
                    cantidad.style.color = '#2C5D75';
                    cantidad.style.fontWeight = 'bold';
                    cantidad.style.minWidth = '30px';

                    const nombre = document.createElement('span');
                    nombre.textContent = lote.nombreLote;

                    infoContainer.appendChild(cantidad);
                    infoContainer.appendChild(nombre);

                    const botonesContainer = document.createElement('div');
                    botonesContainer.style.display = 'flex';
                    botonesContainer.style.gap = '10px';

                    const botonNotificar = document.createElement('button');
                    botonNotificar.className = 'ver-btn';
                    botonNotificar.textContent = 'Notificar llegada';

                    botonNotificar.addEventListener('click', () => {
                        botonNotificar.disabled = true;
                        botonNotificar.textContent = 'Notificando...';

                        fetch('http://localhost:8082/qa/guardarNotificacion', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(lote)
                        })
                                .then(response => {
                                    if (!response.ok)
                                        throw new Error(`Error en la respuesta: ${response.status}`);
                                    return response.text();
                                })
                                .then(() => {
                                    const titulo = `Llegada de lote ${lote.nombreLote}`;
                                    const mensaje = `El lote ${lote.nombreLote} ha sido registrado en el sistema de control de calidad`;

                                    if (window.guardarNotificacion) {
                                        window.guardarNotificacion(titulo, mensaje, "INFORMACION")
                                                .then(() => {
                                                    botonNotificar.disabled = false;
                                                    botonNotificar.textContent = 'Notificar llegada';
                                                    mostrarModalConfirmacion(lote.nombreLote);
                                                })
                                                .catch(error => {
                                                    console.error("Error al guardar notificación:", error);
                                                    botonNotificar.disabled = false;
                                                    botonNotificar.textContent = 'Notificar llegada';
                                                    mostrarModalError("Error al crear notificación", error.message);
                                                });
                                    } else {
                                        const notificacionDTO = {
                                            titulo,
                                            mensaje,
                                            tipo: "INFORMACION",
                                            fechaEnvio: new Date().toISOString()
                                        };

                                        fetch('http://localhost:8082/qa/guardarNotificacion', {
                                            method: 'POST',
                                            headers: {'Content-Type': 'application/json'},
                                            body: JSON.stringify(notificacionDTO)
                                        })
                                                .then(resp => {
                                                    if (!resp.ok)
                                                        throw new Error(`Error al guardar notificación: ${resp.status}`);
                                                    botonNotificar.disabled = false;
                                                    botonNotificar.textContent = 'Notificar llegada';
                                                    mostrarModalConfirmacion(lote.nombreLote);
                                                })
                                                .catch(error => {
                                                    console.error("Error al guardar notificación directamente:", error);
                                                    botonNotificar.disabled = false;
                                                    botonNotificar.textContent = 'Notificar llegada';
                                                    mostrarModalError("Error al crear notificación", error.message);
                                                });
                                    }
                                })
                                .catch(error => {
                                    console.error('Error al notificar lote:', error);
                                    botonNotificar.disabled = false;
                                    botonNotificar.textContent = 'Notificar llegada';
                                    mostrarModalError("Error al registrar el lote", error.message);
                                });
                    });

                    const botonDetalles = document.createElement('button');
                    botonDetalles.className = 'ver-btn';
                    botonDetalles.textContent = 'Ver detalles';
                    botonDetalles.addEventListener('click', () => {
                        fetch(`http://localhost:8082/qa/lote/${lote.idLote}`) // Supongamos que existe este endpoint
                                .then(resp => resp.ok ? resp.json() : Promise.reject('No se pudo cargar el lote'))
                                .then(data => {
                                    localStorage.setItem('loteSeleccionado', JSON.stringify(data));
                                    window.location.href = 'detalleLote.html';
                                })
                                .catch(err => {
                                    console.error(err);
                                    mostrarModalError("Error al cargar el lote", err);
                                });
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
                mostrarModalError("Error al cargar los lotes", error.message);
            });

    crearModalNotificacion(); // Asegurarse que el modal exista
});

// =====================
// MODALES
// =====================

function crearModalNotificacion() {
    if (document.getElementById('modalNotificacion'))
        return;

    const modal = document.createElement('div');
    modal.id = 'modalNotificacion';
    modal.className = 'modal';

    // Asegúrate de que el modal tenga la estructura correcta
    Object.assign(modal.style, {
        display: 'none', // Inicialmente oculto
        position: 'fixed',
        top: '0',
        left: '0',
        width: '100%',
        height: '100%',
        backgroundColor: 'rgba(0,0,0,0.5)',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: '1000'
    });

    const modalContenido = document.createElement('div');
    modalContenido.className = 'modal-contenido';

    // Agrega un estilo para la estructura del modal
    Object.assign(modalContenido.style, {
        background: '#fff',
        padding: '20px',
        borderRadius: '10px',
        maxWidth: '400px',
        width: '90%', // Asegura que el modal no se vea muy pequeño en pantallas pequeñas
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
        textAlign: 'center'
    });

    modal.appendChild(modalContenido);
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    document.body.appendChild(modal);
}

function mostrarModalConfirmacion(nombreLote) {
    let modal = document.getElementById('modalNotificacion');
    if (!modal) {
        crearModalNotificacion();
        modal = document.getElementById('modalNotificacion');
    }

    let contenido = modal.querySelector('.modal-contenido');
    if (!contenido) {
        contenido = document.createElement('div');
        contenido.className = 'modal-contenido';
        modal.appendChild(contenido);
    }

    contenido.innerHTML = '';  // Limpiar contenido anterior

    const titulo = document.createElement('h3');
    titulo.textContent = 'Lote Registrado';
    titulo.style.color = '#28a745';  // Color verde para confirmación

    const mensaje = document.createElement('p');
    mensaje.textContent = `El lote ${nombreLote} ha sido registrado correctamente en el sistema de control de calidad.`;

    const cerrarBtn = document.createElement('button');
    cerrarBtn.textContent = 'Cerrar';
    cerrarBtn.className = 'btn-cerrar';  // Puedes agregar una clase CSS si lo deseas
    cerrarBtn.addEventListener('click', () => modal.style.display = 'none');

    contenido.appendChild(titulo);
    contenido.appendChild(mensaje);
    contenido.appendChild(cerrarBtn);

    modal.style.display = 'flex';  // Mostrar el modal
}

function mostrarModalError(titulo, mensaje) {
    let modal = document.getElementById('modalNotificacion');
    if (!modal) {
        crearModalNotificacion();
        modal = document.getElementById('modalNotificacion');
    }

    let contenido = modal.querySelector('.modal-contenido');
    if (!contenido) {
        contenido = document.createElement('div');
        contenido.className = 'modal-contenido';
        modal.appendChild(contenido);
    }

    contenido.innerHTML = '';  // Limpiar contenido anterior

    const tituloElem = document.createElement('h3');
    tituloElem.textContent = titulo;
    tituloElem.style.color = '#dc3545';  // Color rojo para errores

    const mensajeElem = document.createElement('p');
    mensajeElem.textContent = mensaje;

    const cerrarBtn = document.createElement('button');
    cerrarBtn.textContent = 'Cerrar';
    cerrarBtn.className = 'btn-cerrar';  // Puedes agregar una clase CSS si lo deseas
    cerrarBtn.addEventListener('click', () => modal.style.display = 'none');

    contenido.appendChild(tituloElem);
    contenido.appendChild(mensajeElem);
    contenido.appendChild(cerrarBtn);

    modal.style.display = 'flex';  // Mostrar el modal
}
 