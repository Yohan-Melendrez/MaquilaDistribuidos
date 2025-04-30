const lotes = [
    { id: "Lote A" },
    { id: "Lote B" },
    { id: "Lote C" },
    { id: "Lote D" },
    { id: "Lote E" },
    { id: "Lote F" }
];

const contenedor = document.getElementById('lotesContainer');

lotes.forEach(lote => {
    const div = document.createElement('div');
    div.className = 'lote';
    div.innerHTML = `
        <img src="img/box_icon.png" alt="Caja">
        <div class="loteNombre">${lote.id}</div>
    `;

    div.addEventListener('click', () => {
        window.location.href = 'productosLote.html';
    });
    contenedor.appendChild(div);

    document.getElementById('salirBtn').addEventListener('click', () => {
        window.location.href = 'index.html';
    });
});