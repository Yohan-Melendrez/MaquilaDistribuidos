let stompClient = null;
let panelContent = null;

document.addEventListener('DOMContentLoaded', () => {
    const iconoNotificaciones = document.getElementById('iconoNotificaciones');

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

        panelContent = document.createElement('div');
        panelContent.className = 'panel-content';

        conectarWebSocket();

        panelNotificaciones.appendChild(panelHeader);
        panelNotificaciones.appendChild(panelContent);
        notificacionesContainer.appendChild(panelNotificaciones);

        document.body.appendChild(notificacionesContainer);

        iconoNotificaciones.addEventListener('click', (e) => {
            e.preventDefault();
            const visible = notificacionesContainer.style.display === 'block';
            notificacionesContainer.style.display = visible ? 'none' : 'block';

            if (!visible) {
                cargarNotificacionesPendientes();
            }
        });
    }
});

function crearItemNotificacion(notificacion, panelContent, clave = null) {
    const item = document.createElement('div');
    item.className = 'notificacion-item';
    item.dataset.clave = clave || generarClaveUnica(notificacion);

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
        eliminarNotificacionPendiente(notificacion);
        item.remove();
    };

    item.appendChild(titulo);
    item.appendChild(tipo);
    item.appendChild(mensaje);
    item.appendChild(fecha);
    item.appendChild(cerrar);

    (panelContent || document.querySelector('#notificacionesContainer .panel-content'))?.prepend(item);
}

function generarClaveUnica(notificacion) {
    return `${notificacion.titulo}_${notificacion.tipo}_${notificacion.fechaEnvio}_${notificacion.mensaje}`;
}

function obtenerNotificacionesCerradas() {
    return JSON.parse(localStorage.getItem('notificacionesCerradas')) || [];
}

function guardarNotificacionCerrada(notificacion) {
    const cerradas = obtenerNotificacionesCerradas();
    const clave = generarClaveUnica(notificacion);
    if (!cerradas.includes(clave)) {
        cerradas.push(clave);
        localStorage.setItem('notificacionesCerradas', JSON.stringify(cerradas));
    }
}

function obtenerNotificacionesPendientes() {
    return JSON.parse(localStorage.getItem('notificacionesPendientes')) || [];
}

function guardarNotificacionPendiente(notificacion) {
    const pendientes = obtenerNotificacionesPendientes();
    const clave = generarClaveUnica(notificacion);
    if (!pendientes.some(n => generarClaveUnica(n) === clave)) {
        pendientes.push(notificacion);
        localStorage.setItem('notificacionesPendientes', JSON.stringify(pendientes));
    }
}

function eliminarNotificacionPendiente(notificacion) {
    const pendientes = obtenerNotificacionesPendientes();
    const clave = generarClaveUnica(notificacion);
    const nuevasPendientes = pendientes.filter(n => generarClaveUnica(n) !== clave);
    localStorage.setItem('notificacionesPendientes', JSON.stringify(nuevasPendientes));
}

function cargarNotificacionesPendientes() {
    const pendientes = obtenerNotificacionesPendientes();
    const cerradas = obtenerNotificacionesCerradas();

    pendientes.forEach(notificacion => {
        const clave = generarClaveUnica(notificacion);
        if (!cerradas.includes(clave)) {
            const yaExiste = panelContent.querySelector(`[data-clave="${clave}"]`);
            if (!yaExiste) {
                crearItemNotificacion(notificacion, panelContent, clave);
            }
        }
    });

    // Limpia solo las que se muestran
    localStorage.setItem('notificacionesPendientes', JSON.stringify(pendientes.filter(n => {
        const clave = generarClaveUnica(n);
        return panelContent.querySelector(`[data-clave="${clave}"]`) == null;
    })));
}

function conectarWebSocket() {
    const socket = new SockJS('https://localhost:8082/ws-notificaciones');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/notificaciones', (mensaje) => {
            const notificacion = JSON.parse(mensaje.body);
            const clave = generarClaveUnica(notificacion);

            if (!obtenerNotificacionesCerradas().includes(clave)) {
                const panel = document.querySelector('#notificacionesContainer');
                const visible = panel && panel.style.display === 'block';

                if (visible) {
                    const panelContentActual = document.querySelector('#notificacionesContainer .panel-content');
                    if (panelContentActual) {
                        const yaExiste = panelContentActual.querySelector(`[data-clave="${clave}"]`);
                        if (!yaExiste) {
                            crearItemNotificacion(notificacion, panelContentActual, clave);
                        }
                    }
                } else {
                    guardarNotificacionPendiente(notificacion);
                }
            }
        });
    }, (error) => {
        console.error("Error al conectar WebSocket:", error);
    });
}
