/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
  const listaLotes = document.getElementById('listaLotes');

  // Datos simulados de lotes
  const lotes = [
    { id: 101, descripcion: 'Lote A - Línea 1' },
    { id: 102, descripcion: 'Lote B - Línea 2' },
    { id: 103, descripcion: 'Lote C - Empaque' },
    { id: 104, descripcion: 'Lote D - Auditoría' },
    { id: 105, descripcion: 'Lote E - Presentación Especial' },
  ];

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

    const descripcion = document.createElement('span');
    descripcion.textContent = lote.descripcion;

    const botonVer = document.createElement('button');
    botonVer.className = 'ver-btn';
    botonVer.textContent = 'Enviar lote';
    botonVer.addEventListener('click', () => {
      // Mostrar el modal con el mensaje de confirmación
      mensaje.textContent = `Lote ${lote.descripcion} enviado exitosamente.`;
      modal.style.display = 'flex';
    });

    item.appendChild(descripcion);
    item.appendChild(botonVer);
    listaLotes.appendChild(item);
  });
});