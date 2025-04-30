document.querySelector('.login-form').addEventListener('submit', function (e) {
  e.preventDefault();

  const usuario = document.getElementById('usuario').value;
  const contrasena = document.getElementById('contrasena').value;

  if (usuario === '1' && contrasena === '1') {
    sessionStorage.setItem('usuarioERP', JSON.stringify({ nombre: usuario }));
    window.location.href = 'principal.html';
  } else {
    alert('Usuario o contraseña incorrectos');
  }
});
