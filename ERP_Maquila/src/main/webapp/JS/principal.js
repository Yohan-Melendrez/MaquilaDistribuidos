/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
  // Mostrar nombre del usuario
  const usuario = JSON.parse(sessionStorage.getItem('usuarioERP'));
  if (usuario && usuario.nombre) {
      const span = document.getElementById('nombreUsuario');
      if (span) span.textContent = `Bienvenido, ${usuario.nombre}`;
  }

  // Función para cambiar entre pantallas (si las tienes como .pantalla-X)
  function mostrarPantalla(nombreClase) {
      document.querySelectorAll('.pantalla').forEach(p => p.classList.add('oculto'));
      document.querySelector(`.pantalla-${nombreClase}`).classList.remove('oculto');
  }

  // Evento: ir a reportes
  const botonReportes = document.querySelector('.ir-reportes');
  if (botonReportes) {
      botonReportes.addEventListener('click', () => {
          mostrarPantalla('reportes');
          renderizarReportes();
      });
  }

  // Evento: ir a envío de lotes
  const botonEnvios = document.querySelector('.ir-envios');
  if (botonEnvios) {
      botonEnvios.addEventListener('click', () => {
          mostrarPantalla('envioLotes');
          renderizarEnvios();
      });
  }

  // Lógica para cerrar sesión
  const cerrarBtn = document.getElementById('cerrarSesionBtn');
  if (cerrarBtn) {
      cerrarBtn.addEventListener('click', () => {
          sessionStorage.removeItem('usuarioERP');
          window.location.href = 'index.html';
      });
  }
});
