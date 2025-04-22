/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
  const listaLotes = document.getElementById('listaLotes');

  // Datos simulados de lotes disponibles
  const lotes = [
    { id: 1, cantidad: 'x1', descripcion: 'Lote de Rasuradoras' },
    { id: 2, cantidad: 'x2', descripcion: 'Lote de Jabon' },
    { id: 3, cantidad: 'x1', descripcion: 'Lote de secadoras' },
    { id: 4, cantidad: 'x5', descripcion: 'Lote de shampoo' },
    { id: 5, cantidad: 'x1', descripcion: 'Lote de cepillos' },
  ];

  // Crear el modal de confirmación
  const modal = document.createElement('div');
  modal.className = 'modal';
  modal.style.display = 'none';
  
  const modalContent = document.createElement('div');
  modalContent.className = 'modal-content';
  
  const mensaje = document.createElement('p');
  mensaje.textContent = 'Notificación enviada correctamente.';
  
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

  // También cerrar el modal si se hace clic fuera del contenido
  window.addEventListener('click', (event) => {
    if (event.target === modal) {
      modal.style.display = 'none';
    }
  });

  // Generar los elementos de la lista de lotes
  lotes.forEach(lote => {
    const item = document.createElement('div');
    item.className = 'lote-item';

    // Contenedor para la cantidad y descripción
    const infoContainer = document.createElement('div');
    infoContainer.className = 'lote-info';
    infoContainer.style.display = 'flex';
    infoContainer.style.alignItems = 'center';
    infoContainer.style.gap = '15px';

    // Elemento para la cantidad (x1, x2, etc.)
    const cantidad = document.createElement('span');
    cantidad.textContent = lote.cantidad;
    cantidad.style.color = '#2C5D75';
    cantidad.style.fontWeight = 'bold';
    cantidad.style.minWidth = '30px';

    // Elemento para la descripción
    const descripcion = document.createElement('span');
    descripcion.textContent = lote.descripcion;

    // Agregar cantidad y descripción al contenedor de info
    infoContainer.appendChild(cantidad);
    infoContainer.appendChild(descripcion);

    // Botón de notificación
    const botonNotificar = document.createElement('button');
    botonNotificar.className = 'ver-btn';
    botonNotificar.textContent = 'Notificar llegada';
    botonNotificar.addEventListener('click', () => {
      // Mostrar el modal con el mensaje de confirmación
      mensaje.textContent = `Notificación de llegada del ${lote.descripcion} enviada correctamente.`;
      modal.style.display = 'flex';
    });

    item.appendChild(infoContainer);
    item.appendChild(botonNotificar);
    listaLotes.appendChild(item);
  });
});

