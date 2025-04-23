/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const listaLotes = document.getElementById('listaLotes');

    // Lotes enriquecidos con más información
    const lotes = [
        {
            id: 645,
            cantidad: 1,
            nombre: 'Lote de Rasuradoras',
            descripcion: 'Rasuradoras defectuosas. Algunas no encienden o no cortan correctamente.'
        },
        {
            id: 646,
            cantidad: 2,
            nombre: 'Lote de Jabón',
            descripcion: 'Lote con piezas con fragancia incorrecta.'
        },
        {
            id: 647,
            cantidad: 1,
            nombre: 'Lote de Secadoras',
            descripcion: 'Secadoras con falla en el botón de encendido.'
        },
        {
            id: 648,
            cantidad: 5,
            nombre: 'Lote de Shampoo',
            descripcion: 'Botellas mal selladas, fugas detectadas.'
        },
        {
            id: 649,
            cantidad: 1,
            nombre: 'Lote de Cepillos',
            descripcion: 'Cerdas mal colocadas, afectan el cepillado.'
        }
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

    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

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
            modal.style.display = 'flex';
        });

        const botonDetalles = document.createElement('button');
        botonDetalles.className = 'ver-btn';
        botonDetalles.textContent = 'Ver detalles';

        botonDetalles.addEventListener('click', () => {
            localStorage.setItem('loteSeleccionado', JSON.stringify(lote));
            window.location.href = 'detalleLote.html';
        });

        botonesContainer.appendChild(botonNotificar);
        botonesContainer.appendChild(botonDetalles);

        item.appendChild(infoContainer);
        item.appendChild(botonesContainer);
        listaLotes.appendChild(item);
    });
});

