/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const listaLotes = document.getElementById('listaLotes');
    const nivelesAtencion = ['BAJO', 'MEDIO', 'ALTO', 'CRITICO'];

    // Modal para mostrar mensajes y detalles
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.style.display = 'none';

    const modalContent = document.createElement('div');
    modalContent.className = 'modal-content';

    const mensaje = document.createElement('p');
    const closeButton = document.createElement('button');
    closeButton.className = 'close-button';
    closeButton.textContent = 'Cerrar';
    closeButton.addEventListener('click', () => modal.style.display = 'none');

    modalContent.appendChild(mensaje);
    modalContent.appendChild(closeButton);
    modal.appendChild(modalContent);
    document.body.appendChild(modal);

    // Función para mostrar un mensaje en el modal
    function mostrarMensaje(mensajeTexto, esHTML = false) {
        if (esHTML) {
            mensaje.innerHTML = mensajeTexto;
        } else {
            mensaje.textContent = mensajeTexto;
        }
        modal.style.display = 'flex';
    }

    // Cargar los lotes con errores
    fetch("https://localhost:8082/qa/lotesConErrores")
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error en la respuesta: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (!data || data.length === 0) {
                const noLotes = document.createElement('p');
                noLotes.textContent = 'No hay lotes con errores disponibles';
                listaLotes.appendChild(noLotes);
                return;
            }

            data.forEach(lote => {
                const item = document.createElement('div');
                item.className = 'lote-item';

                const descripcion = document.createElement('span');
                descripcion.textContent = `Lote ${lote.nombreLote}`;
                descripcion.className = 'fallo-descripcion';

                const seleccionContainer = document.createElement('div');
                seleccionContainer.className = 'seleccion-container';

                // Dropdown para nivel de atención
                const nivelDropdown = document.createElement('div');
                nivelDropdown.className = 'nivel-dropdown';

                const nivelSeleccionado = document.createElement('div');
                nivelSeleccionado.className = 'nivel-seleccionado';
                nivelSeleccionado.textContent = lote.nivelAtencion || 'Nivel de atención';

                const dropdownArrow = document.createElement('span');
                dropdownArrow.className = 'dropdown-arrow';
                dropdownArrow.innerHTML = '&#9662;';
                nivelSeleccionado.appendChild(dropdownArrow);
                nivelDropdown.appendChild(nivelSeleccionado);

                // Botón para asignar nivel
                const botonAsignar = document.createElement('button');
                botonAsignar.className = 'asignar-btn';
                botonAsignar.textContent = 'Asignar nivel';

                botonAsignar.addEventListener('click', () => {
                    const nivel = nivelSeleccionado.textContent.replace('▾', '').trim();
                    
                    if (nivel === 'Nivel de atención') {
                        mostrarMensaje('Por favor seleccione un nivel de atención');
                        return;
                    }
                    
                    const dto = {
                        idLote: lote.idLote,
                        nivelAtencion: nivel
                    };

                    // Llamada al endpoint para asignar nivel de atención
                    fetch("https://localhost:8082/qa/asignarNivelAtencion", {
                        method: "PUT",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(dto)
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`Error en la respuesta: ${response.status}`);
                        }
                        return response.text();
                    })
                    .then(() => {
                        // Guardar notificación automáticamente
                        const titulo = `Nivel ${nivel} asignado`;
                        const mensajeNoti = `Se ha asignado el nivel "${nivel}" al lote ${lote.nombreLote}`;
                        guardarNotificacionNivelAsignado(titulo, mensajeNoti, nivel, lote.idLote);
                        
                        mostrarMensaje(`Nivel "${nivel}" asignado al lote ${lote.nombreLote}`);
                        
                        // Actualizar el nivel visualmente sin recargar la página
                        lote.nivelAtencion = nivel;
                    })
                    .catch(error => {
                        console.error("Error al asignar nivel:", error);
                        mostrarMensaje(`Error al asignar nivel: ${error.message}`);
                    });
                });

                // Botón para ver detalles
                const botonDetalles = document.createElement('button');
                botonDetalles.className = 'asignar-btn';
                botonDetalles.textContent = 'Ver detalles';
                
                botonDetalles.addEventListener('click', () => {
                    let detalles = `
                        <strong>Nombre del Lote:</strong> ${lote.nombreLote}<br>
                        <strong>ID Lote:</strong> ${lote.idLote}<br>
                        <strong>Nivel de atención:</strong> ${lote.nivelAtencion || 'No asignado'}<br>
                        <strong>Errores encontrados:</strong><br>
                    `;
                    
                    // Lista de errores
                    let errorCount = 0;
                    
                    if (lote.productos && Array.isArray(lote.productos)) {
                        lote.productos.forEach(producto => {
                            // Accedemos directamente a los errores del producto
                            if (producto && producto.errores && Array.isArray(producto.errores) && producto.errores.length > 0) {
                                detalles += `<div class="error-producto">
                                    <strong>Producto:</strong> ${producto.nombre || 'Sin nombre'} (ID: ${producto.idProducto})<ul>`;
                                
                                producto.errores.forEach(error => {
                                    if (error) {
                                        // Mostramos nombre y descripción del error
                                        detalles += `<li><b>${error.nombre}:</b> ${error.descripcion} 
                                            <span class="error-costo">(Costo: ${error.costoUsd?.toFixed(2) || '0.00'} USD)</span></li>`;
                                        errorCount++;
                                    }
                                });
                                
                                detalles += `</ul></div>`;
                            }
                        });
                    }
                    
                    if (errorCount === 0) {
                        detalles += `<p>No se encontraron errores detallados para este lote.</p>`;
                    }
                    
                    mostrarMensaje(detalles, true);
                });

                seleccionContainer.appendChild(nivelDropdown);
                seleccionContainer.appendChild(botonAsignar);
                seleccionContainer.appendChild(botonDetalles);

                item.appendChild(descripcion);
                item.appendChild(seleccionContainer);
                listaLotes.appendChild(item);

                // Implementación del dropdown
                nivelDropdown.addEventListener('click', (e) => {
                    e.stopPropagation();
                    
                    // Cerrar otros dropdowns abiertos
                    const openDropdowns = document.querySelectorAll('.nivel-opciones');
                    openDropdowns.forEach(dropdown => dropdown.remove());
                    
                    const opciones = document.createElement('div');
                    opciones.className = 'nivel-opciones';

                    nivelesAtencion.forEach(nivel => {
                        const opcion = document.createElement('div');
                        opcion.className = 'nivel-opcion';
                        opcion.textContent = nivel;
                        
                        // Marcar la opción seleccionada actualmente
                        if (nivelSeleccionado.textContent.replace('▾', '').trim() === nivel) {
                            opcion.classList.add('selected');
                        }
                        
                        opcion.addEventListener('click', (e) => {
                            e.stopPropagation();
                            nivelSeleccionado.textContent = nivel;
                            nivelSeleccionado.appendChild(dropdownArrow);
                            opciones.remove();
                        });
                        
                        opciones.appendChild(opcion);
                    });

                    nivelDropdown.appendChild(opciones);

                    // Cerrar el dropdown cuando se hace clic fuera
                    document.addEventListener('click', function closeDropdown(e) {
                        if (!nivelDropdown.contains(e.target)) {
                            if (opciones.parentNode === nivelDropdown) {
                                nivelDropdown.removeChild(opciones);
                            }
                            document.removeEventListener('click', closeDropdown);
                        }
                    });
                });
            });
        })
        .catch(error => {
            console.error("Error al cargar los lotes con errores:", error.message);
            mostrarMensaje(`Error al cargar los lotes: ${error.message}`);
        });

    // Función para guardar notificación al asignar nivel de atención
    function guardarNotificacionNivelAsignado(titulo, mensaje, nivel, idLote) {
        // Determinar el tipo de notificación basado en el nivel
        let tipo = "ACTUALIZACION";
        if (nivel === "ALTO" || nivel === "CRITICO") {
            tipo = "ALERTA";
        }
        
        // Usar la función de guardar notificación del módulo de notificaciones
        if (window.guardarNotificacion) {
            window.guardarNotificacion(titulo, mensaje, tipo)
                .then(() => console.log("Notificación guardada correctamente"))
                .catch(error => console.error("Error al guardar notificación:", error));
        } else {
            // Fallback si la función global no está disponible
            const notificacionDTO = {
                titulo: titulo,
                mensaje: mensaje,
                tipo: tipo,
                fechaEnvio: new Date().toISOString(),
                idInspector: 1 // ID por defecto del inspector
            };
            
            fetch('https://localhost:8082/qa/guardarNotificacion', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(notificacionDTO)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error al guardar notificación: ${response.status}`);
                }
                console.log("Notificación guardada correctamente");
            })
            .catch(error => {
                console.error("Error al guardar notificación:", error);
            });
        }
    }
});