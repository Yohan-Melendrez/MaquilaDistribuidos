/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const iconoNotificaciones = document.querySelector('.iconoNav img[src*="noti.svg"]')?.parentElement;

    if (iconoNotificaciones) {
        const notificacionesContainer = document.createElement('div');
        notificacionesContainer.className = 'notificaciones-container';
        notificacionesContainer.id = 'notificacionesContainer';

        const panelNotificaciones = document.createElement('div');
        panelNotificaciones.className = 'panel-notificaciones';

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

        const panelContent = document.createElement('div');
        panelContent.className = 'panel-content';

        cargarNotificaciones(panelContent);

        panelNotificaciones.appendChild(panelHeader);
        panelNotificaciones.appendChild(panelContent);
        notificacionesContainer.appendChild(panelNotificaciones);

        document.body.appendChild(notificacionesContainer);

        iconoNotificaciones.addEventListener('click', (e) => {
            e.preventDefault();
            if (notificacionesContainer.style.display === 'block') {
                notificacionesContainer.style.display = 'none';
            } else {
                notificacionesContainer.style.display = 'block';
                cargarNotificaciones(panelContent);
            }
        });
    }
});

function cargarNotificaciones(panelContent) {
    panelContent.innerHTML = '';

    const cargando = document.createElement('p');
    cargando.className = 'cargando-notificaciones';
    cargando.textContent = 'Cargando notificaciones...';
    panelContent.appendChild(cargando);

    fetch('http://localhost:8081/qa/notificaciones')
            .then(response => response.json())
            .then(notificaciones => {
                panelContent.removeChild(cargando);

                if (!notificaciones || notificaciones.length === 0) {
                    const noNotificaciones = document.createElement('p');
                    noNotificaciones.className = 'no-notificaciones';
                    noNotificaciones.textContent = 'No tienes notificaciones nuevas';
                    panelContent.appendChild(noNotificaciones);
                    return;
                }

                notificaciones
                        .filter(n => !estaNotificacionCerrada(n))
                        .sort((a, b) => new Date(b.fechaEnvio) - new Date(a.fechaEnvio))
                        .forEach(notificacion => {
                            crearItemNotificacion(notificacion, panelContent);
                        });

            })
            .catch(error => {
                console.error("Error al cargar notificaciones:", error);
                panelContent.removeChild(cargando);
                const errorMsg = document.createElement('p');
                errorMsg.className = 'error-notificaciones';
                errorMsg.textContent = 'Error al cargar las notificaciones';
                panelContent.appendChild(errorMsg);
            });
}

function crearItemNotificacion(notificacion, panelContent) {
    const item = document.createElement('div');
    item.className = 'notificacion-item';

    const titulo = document.createElement('strong');
    titulo.textContent = notificacion.titulo || 'Sin título';

    const tipo = document.createElement('span');
    tipo.className = 'notificacion-tipo';
    tipo.textContent = ` [${notificacion.tipo || 'N/A'}]`;

    const mensaje = document.createElement('p');
    mensaje.textContent = notificacion.mensaje;

    const fecha = document.createElement('small');
    const fechaObj = new Date(notificacion.fechaEnvio);
    fecha.textContent = fechaObj.toLocaleString();

    const cerrar = document.createElement('button');
    cerrar.className = 'cerrar-notificacion';
    cerrar.innerHTML = '&times;';
    cerrar.onclick = () => {
        guardarNotificacionCerrada(notificacion);
        item.remove();
    };
    item.appendChild(titulo);
    item.appendChild(tipo);
    item.appendChild(mensaje);
    item.appendChild(fecha);
    item.appendChild(cerrar);

    panelContent.appendChild(item);
}
function guardarNotificacionCerrada(notificacion) {
    const cerradas = JSON.parse(localStorage.getItem('notificacionesCerradas')) || [];
    const clave = `${notificacion.titulo}_${notificacion.fechaEnvio}`;
    if (!cerradas.includes(clave)) {
        cerradas.push(clave);
        localStorage.setItem('notificacionesCerradas', JSON.stringify(cerradas));
    }
}

function estaNotificacionCerrada(notificacion) {
    const cerradas = JSON.parse(localStorage.getItem('notificacionesCerradas')) || [];
    const clave = `${notificacion.titulo}_${notificacion.fechaEnvio}`;
    return cerradas.includes(clave);
}
