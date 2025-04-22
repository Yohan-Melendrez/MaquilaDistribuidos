document.addEventListener('DOMContentLoaded', () => {
  const listaLotes = document.getElementById('listaLotes');

  // Datos simulados de fallos de producción
  const fallos = [
    { id: 1, descripcion: '5 Lotes de rasuradoras rotas', nivelAtencion: 'Nivel de atención' },
    { id: 2, descripcion: '2 Lotes de cepillos defectuosos', nivelAtencion: 'Nivel de atención' },
    { id: 3, descripcion: '1 lote de jabones dañado', nivelAtencion: 'Nivel de atención' },
    { id: 4, descripcion: '3 Lotes de shampoos mal etiquetados', nivelAtencion: 'Nivel de atención' },
  ];

  // Niveles de atención disponibles
  const nivelesAtencion = ['Bajo', 'Medio', 'Alto', 'Crítico'];

  // Crear el modal de confirmación
  const modal = document.createElement('div');
  modal.className = 'modal';
  modal.style.display = 'none';
  
  const modalContent = document.createElement('div');
  modalContent.className = 'modal-content';
  
  const mensaje = document.createElement('p');
  mensaje.textContent = 'Nivel de atención asignado correctamente.';
  
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

  // Cerrar el modal si se hace clic fuera del contenido
  window.addEventListener('click', (event) => {
    if (event.target === modal) {
      modal.style.display = 'none';
    }
  });

  // Generar los elementos de la lista de fallos
  fallos.forEach(fallo => {
    const item = document.createElement('div');
    item.className = 'lote-item';

    const descripcion = document.createElement('span');
    descripcion.textContent = fallo.descripcion;
    descripcion.className = 'fallo-descripcion';

    const seleccionContainer = document.createElement('div');
    seleccionContainer.className = 'seleccion-container';

    // Crear el dropdown de nivel de atención
    const nivelDropdown = document.createElement('div');
    nivelDropdown.className = 'nivel-dropdown';
    
    const nivelSeleccionado = document.createElement('div');
    nivelSeleccionado.className = 'nivel-seleccionado';
    nivelSeleccionado.textContent = fallo.nivelAtencion;
    
    const dropdownArrow = document.createElement('span');
    dropdownArrow.className = 'dropdown-arrow';
    dropdownArrow.innerHTML = '&#9662;'; // Triángulo hacia abajo
    
    nivelSeleccionado.appendChild(dropdownArrow);
    nivelDropdown.appendChild(nivelSeleccionado);

    // Botón de asignar nivel
    const botonAsignar = document.createElement('button');
    botonAsignar.className = 'asignar-btn';
    botonAsignar.textContent = 'Asignar nivel';
    botonAsignar.addEventListener('click', () => {
      // Actualizar el nivel de atención seleccionado
      mensaje.textContent = `Nivel de atención "${nivelSeleccionado.textContent.replace('▾', '').trim()}" asignado a "${fallo.descripcion}".`;
      modal.style.display = 'flex';
    });

    seleccionContainer.appendChild(nivelDropdown);
    seleccionContainer.appendChild(botonAsignar);

    item.appendChild(descripcion);
    item.appendChild(seleccionContainer);
    listaLotes.appendChild(item);
    
    // Funcionalidad de dropdown (simulado)
    nivelDropdown.addEventListener('click', () => {
      // Si hay un menú desplegable abierto, lo cierra primero
      const openDropdown = document.querySelector('.nivel-opciones');
      if (openDropdown) {
        openDropdown.remove();
      }
      
      // Crear menú desplegable
      const opciones = document.createElement('div');
      opciones.className = 'nivel-opciones';
      
      nivelesAtencion.forEach(nivel => {
        const opcion = document.createElement('div');
        opcion.className = 'nivel-opcion';
        opcion.textContent = nivel;
        opcion.addEventListener('click', (e) => {
          e.stopPropagation();
          nivelSeleccionado.textContent = nivel;
          dropdownArrow.innerHTML = '&#9662;';
          nivelSeleccionado.appendChild(dropdownArrow);
          opciones.remove();
        });
        opciones.appendChild(opcion);
      });
      
      nivelDropdown.appendChild(opciones);
      
      // Cierra el dropdown al hacer clic fuera
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