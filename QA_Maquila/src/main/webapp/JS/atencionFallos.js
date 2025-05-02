document.addEventListener('DOMContentLoaded', () => {
    const listaLotes = document.getElementById('listaLotes');
    const nivelesAtencion = ['BAJO', 'MEDIO', 'ALTO', 'CRITICO'];

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

    fetch("http://localhost:8081/qa/lotesConErrores")
            .then(response => response.json())
            .then(data => {
                data.forEach(lote => {
                    lote.productos.forEach(prod => {
                        prod.producto.errores.forEach(error => {
                            const item = document.createElement('div');
                            item.className = 'lote-item';

                            const descripcion = document.createElement('span');
                            descripcion.textContent = `Lote ${lote.nombreLote}: ${error.descripcion}`;
                            descripcion.className = 'fallo-descripcion';

                            const seleccionContainer = document.createElement('div');
                            seleccionContainer.className = 'seleccion-container';

                            const nivelDropdown = document.createElement('div');
                            nivelDropdown.className = 'nivel-dropdown';

                            const nivelSeleccionado = document.createElement('div');
                            nivelSeleccionado.className = 'nivel-seleccionado';
                            nivelSeleccionado.textContent = error.nivelAtencion || 'Nivel de atención';

                            const dropdownArrow = document.createElement('span');
                            dropdownArrow.className = 'dropdown-arrow';
                            dropdownArrow.innerHTML = '&#9662;';
                            nivelSeleccionado.appendChild(dropdownArrow);
                            nivelDropdown.appendChild(nivelSeleccionado);

                            const botonAsignar = document.createElement('button');
                            botonAsignar.className = 'asignar-btn';
                            botonAsignar.textContent = 'Asignar nivel';

                            botonAsignar.addEventListener('click', () => {
                                const nivel = nivelSeleccionado.textContent.replace('▾', '').trim();
                                const dto = {
                                    idError: error.idError,
                                    idLote: lote.idLote,
                                    nivelAtencion: nivel
                                };

                                fetch("http://localhost:8081/qa/asignarNivelAtencion", {
                                    method: "PUT",
                                    headers: {"Content-Type": "application/json"},
                                    body: JSON.stringify(dto)
                                })
                                        .then(response => response.text())
                                        .then(() => {
                                            mensaje.textContent = `Nivel "${nivel}" asignado a error de lote ${lote.nombreLote}`;
                                            modal.style.display = 'flex';
                                        })
                                        .catch(error => {
                                            mensaje.textContent = `Error: ${error.message}`;
                                            modal.style.display = 'flex';
                                        });
                            });

                            const botonDetalles = document.createElement('button');
                            botonDetalles.className = 'asignar-btn';
                            botonDetalles.textContent = 'Ver detalles';
                            botonDetalles.addEventListener('click', () => {
                                const detalles = `
                                <strong>Nombre del Lote:</strong> ${lote.nombreLote}<br>
                                <strong>ID Lote:</strong> ${lote.idLote}<br>
                                <strong>Producto:</strong> ${prod.producto.nombre}<br>
                                <strong>ID Producto:</strong> ${prod.producto.idProducto}<br>
                                <strong>Error:</strong> ${error.descripcion}<br>
                                <strong>Nivel actual:</strong> ${error.nivelAtencion || 'No asignado'}
                                `;
                                mensaje.innerHTML = detalles;
                                modal.style.display = 'flex';
                            });

                            seleccionContainer.appendChild(nivelDropdown);
                            seleccionContainer.appendChild(botonAsignar);
                            seleccionContainer.appendChild(botonDetalles);

                            item.appendChild(descripcion);
                            item.appendChild(seleccionContainer);
                            listaLotes.appendChild(item);

                            // Dropdown manual
                            nivelDropdown.addEventListener('click', () => {
                                const openDropdown = document.querySelector('.nivel-opciones');
                                if (openDropdown)
                                    openDropdown.remove();

                                const opciones = document.createElement('div');
                                opciones.className = 'nivel-opciones';

                                nivelesAtencion.forEach(nivel => {
                                    const opcion = document.createElement('div');
                                    opcion.className = 'nivel-opcion';
                                    opcion.textContent = nivel;
                                    opcion.addEventListener('click', (e) => {
                                        e.stopPropagation();
                                        nivelSeleccionado.textContent = nivel;
                                        nivelSeleccionado.appendChild(dropdownArrow);
                                        opciones.remove();
                                    });
                                    opciones.appendChild(opcion);
                                });

                                nivelDropdown.appendChild(opciones);

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
                    });
                });
            })
            .catch(error => {
                console.error("Error al cargar los lotes con errores:", error.message);
            });
});
