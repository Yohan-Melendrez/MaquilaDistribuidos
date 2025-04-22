/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


// Función para cambiar entre pantallas
function mostrarPantalla(nombreClase) {
  document.querySelectorAll('.pantalla').forEach(p => p.classList.add('oculto'));
  document.querySelector(`.pantalla-${nombreClase}`).classList.remove('oculto');
}

// Evento: ir a reportes
document.querySelector('.ir-reportes').addEventListener('click', () => {
  mostrarPantalla('reportes');
  renderizarReportes();
});

// Evento: ir a envío de lotes
document.querySelector('.ir-envios').addEventListener('click', () => {
  mostrarPantalla('envioLotes');
  renderizarEnvios();
});



