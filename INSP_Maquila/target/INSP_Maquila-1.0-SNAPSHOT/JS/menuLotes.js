const contenedor = document.getElementById('lotesContainer');
const inspector = sessionStorage.getItem("inspector");

if (!inspector) {
    alert("No hay sesión activa.");
    window.location.href = "index.html";
}

// Mostrar saludo
document.querySelector('.saludo').textContent = `Hola ${inspector}`;

// Cargar lotes reales del backend
fetch(`http://localhost:9090/inspeccion/lotes-asignados/${inspector}`)
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener lotes.");
        }
        return response.json();
    })
    .then(lotes => {
        lotes.forEach(lote => {
            const div = document.createElement('div');
            div.className = 'lote';
            div.innerHTML = `
                <img src="img/box_icon.png" alt="Caja">
                <div class="loteNombre">${lote.nombreLote}</div>
            `;

            div.addEventListener('click', () => {
                sessionStorage.setItem("idLote", lote.idLote);
                window.location.href = 'productosLote.html';
            });

            contenedor.appendChild(div);
        });

        if (lotes.length === 0) {
            contenedor.innerHTML = "<p>No tienes lotes asignados.</p>";
        }
    })
    .catch(error => {
        console.error(error);
        contenedor.innerHTML = "<p>Error al cargar lotes.</p>";
    });

document.getElementById('salirBtn').addEventListener('click', () => {
    sessionStorage.clear();
    window.location.href = 'index.html';
});
