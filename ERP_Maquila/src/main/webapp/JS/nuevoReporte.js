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

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const desde = document.getElementById('fechaDesde').value;
        const hasta = document.getElementById('fechaHasta').value;

        if (!desde || !hasta) {
            mostrarMensaje('Por favor, completa todos los campos.', true);
            return;
        }

        const url = 'http://localhost:9091/reportes/generarReporte';
        const inicio = `${desde}T00:00:00`;
        const fin = `${hasta}T23:59:00`;
        const filtro = {inicio, fin};

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
                    // Guardamos los reportes generados en sessionStorage
                    sessionStorage.setItem('reportesGenerados', JSON.stringify(data));
                    // Redirigimos
                    setTimeout(() => window.location.href = 'reportes.html', 1500);
                })
                .catch(err => {
                    console.error('Error generando el reporte:', err);
                });
    });
});
