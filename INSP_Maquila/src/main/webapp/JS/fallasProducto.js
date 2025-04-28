const fallas = [
    "Problemas con el motor",
    "Problemas con las cuchillas",
    "Problemas con la batería",
    "Problemas con el interruptor de encendido"
];

const contenedor = document.getElementById('fallasContainer');

fallas.forEach(falla => {
    const div = document.createElement('div');
    div.className = 'falla';
    div.innerHTML = `
        <span>${falla}</span>
        <input type="checkbox">
    `;
    contenedor.appendChild(div);
});