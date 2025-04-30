document.addEventListener('DOMContentLoaded', () => {
  const listaLotes = document.getElementById('listaLotes');

  // Lotes fijos simulados
  const lotesFijos = [
    { id: 101, descripcion: 'Lote A - Línea 1' },
    { id: 102, descripcion: 'Lote B - Línea 2' },
    { id: 103, descripcion: 'Lote C - Empaque' },
    { id: 104, descripcion: 'Lote D - Auditoría' },
    { id: 105, descripcion: 'Lote E - Presentación Especial' }
  ];

  // Lotes creados desde crearLote.html
  const lotesCreados = JSON.parse(sessionStorage.getItem("lotesCreados")) || [];

  // Combinar ambos
  const todosLosLotes = [...lotesFijos, ...lotesCreados];

  // Crear modal
  const modal = document.createElement('div');
  modal.className = 'modal';
  modal.style.display = 'none';

  const modalContent = document.createElement('div');
  modalContent.className = 'modal-content';

  const mensaje = document.createElement('p');
  mensaje.textContent = '';

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

  // Cerrar modal al hacer clic fuera del contenido
  window.addEventListener('click', (event) => {
    if (event.target === modal) {
      modal.style.display = 'none';
    }
  });

  // Renderizar todos los lotes
  todosLosLotes.forEach(lote => {
    const item = document.createElement('div');
    item.className = 'lote-item';

    const descripcion = document.createElement('span');
    descripcion.textContent = lote.descripcion;

    const botonVer = document.createElement('button');
    botonVer.className = 'ver-btn';
    botonVer.textContent = 'Enviar lote';
    botonVer.setAttribute('aria-label', `Enviar ${lote.descripcion}`);
    botonVer.addEventListener('click', () => {
      mensaje.textContent = `Lote "${lote.descripcion}" enviado exitosamente.`;
      modal.style.display = 'flex';
    });

    item.appendChild(descripcion);
    item.appendChild(botonVer);
    listaLotes.appendChild(item);
  });
});
