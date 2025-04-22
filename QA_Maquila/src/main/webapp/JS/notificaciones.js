/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', () => {
    // Buscar el icono de notificaciones en la barra superior
    const iconoNotificaciones = document.querySelector('.iconoNav img[src*="noti.svg"]').parentElement;
    
    // Crear el contenedor de notificaciones
    const notificacionesContainer = document.createElement('div');
    notificacionesContainer.className = 'notificaciones-container';
    notificacionesContainer.id = 'notificacionesContainer';
    
    // Crear el panel de notificaciones
    const panelNotificaciones = document.createElement('div');
    panelNotificaciones.className = 'panel-notificaciones';
    
    // Crear el encabezado con el título y botón para cerrar
    const panelHeader = document.createElement('div');
    panelHeader.className = 'panel-header';
    
    const titulo = document.createElement('h3');
    titulo.textContent = 'Notificaciones';
    
    const cerrarBtn = document.createElement('button');
    cerrarBtn.className = 'cerrar-btn';
    cerrarBtn.innerHTML = '&times;';
    cerrarBtn.addEventListener('click', () => {
        notificacionesContainer.style.display = 'none';
    });
    
    panelHeader.appendChild(titulo);
    panelHeader.appendChild(cerrarBtn);
    
    // Crear el contenido del panel donde se mostrarán las notificaciones
    const panelContent = document.createElement('div');
    panelContent.className = 'panel-content';
    
    // Datos simulados de notificaciones
    const notificaciones = [
        { id: 1, mensaje: 'Se ha notificado una falla en el lote de jabones', leido: false },
        { id: 2, mensaje: 'Se ha notificado una falla en el lote de shampoos', leido: false },
        { id: 3, mensaje: 'Ha llegado un lote al departamento', leido: false }
    ];
    
    // Generar las notificaciones
    notificaciones.forEach(notificacion => {
        const notificacionItem = document.createElement('div');
        notificacionItem.className = 'notificacion-item';
        notificacionItem.dataset.id = notificacion.id;
        
        const mensaje = document.createElement('p');
        mensaje.textContent = notificacion.mensaje;
        
        const cerrarNotificacion = document.createElement('button');
        cerrarNotificacion.className = 'cerrar-notificacion';
        cerrarNotificacion.innerHTML = '&times;';
        cerrarNotificacion.addEventListener('click', (e) => {
            e.stopPropagation();
            notificacionItem.remove();
            
            // Si no quedan notificaciones, mostrar mensaje
            if (panelContent.querySelectorAll('.notificacion-item').length === 0) {
                const noNotificaciones = document.createElement('p');
                noNotificaciones.className = 'no-notificaciones';
                noNotificaciones.textContent = 'No tienes notificaciones nuevas';
                panelContent.appendChild(noNotificaciones);
            }
        });
        
        notificacionItem.appendChild(mensaje);
        notificacionItem.appendChild(cerrarNotificacion);
        panelContent.appendChild(notificacionItem);
    });
    
    // Construir el panel completo
    panelNotificaciones.appendChild(panelHeader);
    panelNotificaciones.appendChild(panelContent);
    notificacionesContainer.appendChild(panelNotificaciones);
    
    // Añadir el panel al body para que esté disponible en toda la página
    document.body.appendChild(notificacionesContainer);
    
    // Mostrar/ocultar el panel al hacer clic en el icono de notificaciones
    iconoNotificaciones.addEventListener('click', (e) => {
        e.preventDefault();
        if (notificacionesContainer.style.display === 'block') {
            notificacionesContainer.style.display = 'none';
        } else {
            notificacionesContainer.style.display = 'block';
        }
    });
    
    // Cerrar el panel si se hace clic fuera de él
    window.addEventListener('click', (e) => {
        if (!panelNotificaciones.contains(e.target) && 
            !iconoNotificaciones.contains(e.target) && 
            notificacionesContainer.style.display === 'block') {
            notificacionesContainer.style.display = 'none';
        }
    });
});
