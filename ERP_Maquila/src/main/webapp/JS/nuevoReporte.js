document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('formNuevoReporte');
    const tipoDefectoSelect = document.getElementById('tipoDefecto');
    const mensaje = document.getElementById('mensaje');

    function mostrarMensaje(texto, esError = false) {
        mensaje.textContent = texto;
        mensaje.className = esError ? 'error mostrar' : 'mostrar';
        setTimeout(() => {
            mensaje.classList.remove('mostrar');
        }, 3000);
    }

    // Cargar errores desde el backend
    fetch('http://localhost:9091/reportes/errores')
            .then(res => res.json())
            .then(data => {
                data.forEach(error => {
                    const option = document.createElement('option');
                    option.value = error.id;
                    option.textContent = error.nombre;
                    tipoDefectoSelect.appendChild(option);
                });
            })
            .catch(err => {
                console.error('Error cargando errores:', err);
                mostrarMensaje('Error al cargar errores.', true);
            });

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const errorId = tipoDefectoSelect.value;
        const desde = document.getElementById('fechaDesde').value;
        const hasta = document.getElementById('fechaHasta').value;

        if (!errorId || !desde || !hasta) {
            mostrarMensaje('Por favor, completa todos los campos.', true);
            return;
        }

        const url = 'http://localhost:9091/reportes/generarReporte';
        const inicio = `${desde}T00:00:00`;
        const fin = `${hasta}T23:59:00`;
        const filtro = {inicio, fin, idError: errorId};

        fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(filtro)
        })
                .then(res => {
                    if (!res.ok) {
                        return res.json().then(err => {
                            const modal = document.getElementById('modalRechazo');
                            modal.style.display = 'flex';
                            setTimeout(() => {
                                modal.style.display = 'none';
                            }, 3000); // 
                            throw new Error('Error en el servidor');
                        });
                    }
                    return res.json();
                })
                .then(data => {
                    document.getElementById('modalConfirmacion').style.display = 'flex';
                    setTimeout(() => window.location.href = 'reportes.html', 1500);
                })
                .catch(err => {
                    console.error('Error generando el reporte:', err);
                });
    });
});
