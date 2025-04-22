/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.querySelector('.login-form').addEventListener('submit', function (e) {
  e.preventDefault();

  // Puedes agregar validación real aquí si gustas
  const usuario = document.getElementById('usuario').value;
  const contrasena = document.getElementById('contrasena').value;

  if (usuario === '1' && contrasena === '1') {
    window.location.href = 'principal.html'; // Redirige a la pantalla principal
  } else {
    alert('Usuario o contraseña incorrectos');
  }
});

