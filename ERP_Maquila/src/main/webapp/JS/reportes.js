document.addEventListener('DOMContentLoaded', () => {
    const listaReportes = document.getElementById('listaReportes');

    const reportesGuardados = sessionStorage.getItem('reportesGenerados');

    if (reportesGuardados) {
        // Mostramos los reportes generados recientemente
        const reportes = JSON.parse(reportesGuardados);

        reportes.forEach((reporte, index) => {
            const item = document.createElement('div');
            item.className = 'reporte-item';

            const nombre = document.createElement('span');
            nombre.textContent = `Reporte #${index + 1} - Defecto: ${reporte.tipoDefecto} | Fecha: ${reporte.fechaComprendida}`;

            const botonVer = document.createElement('button');
            botonVer.className = 'ver-btn';
            botonVer.textContent = 'Ver';
            botonVer.addEventListener('click', () => {
                window.location.href = `detalleReporte.html?idError=${reporte.idError}`;
            });

            item.appendChild(nombre);
            item.appendChild(botonVer);
            listaReportes.appendChild(item);
        });
    } else {
        // Cargar historial normalmente
        fetch('http://localhost:9091/reportes/historial')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(reportes => {
                reportes.forEach((reporte, index) => {
                    const item = document.createElement('div');
                    item.className = 'reporte-item';

                    const nombre = document.createElement('span');
                    nombre.textContent = `Reporte #${index + 1} - Defecto: ${reporte.tipoDefecto} | Fecha: ${reporte.fechaComprendida}`;

                    const botonVer = document.createElement('button');
                    botonVer.className = 'ver-btn';
                    botonVer.textContent = 'Ver';
                    botonVer.addEventListener('click', () => {
                        window.location.href = `detalleReporte.html?id=${reporte.id}`;
                    });

                    item.appendChild(nombre);
                    item.appendChild(botonVer);
                    listaReportes.appendChild(item);
                });
            })
            .catch(error => {
                console.error('Error al obtener los reportes:', error);
                listaReportes.innerHTML = '<p>Error al cargar los reportes.</p>';
            });
    }
});
