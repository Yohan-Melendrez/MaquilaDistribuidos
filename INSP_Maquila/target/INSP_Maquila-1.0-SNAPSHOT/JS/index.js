document.getElementById('loginBtn').addEventListener('click', function() {
    const usuario = document.getElementById('usuario').value;
    const contrasena = document.getElementById('contrasena').value;

    if (usuario.trim() === "" || contrasena.trim() === "") {
        alert("Por favor, llene todos los campos.");
    } else {
        // Simulamos login exitoso
        window.location.href = "menuLotes.html"; // Redirige al menú de lotes
    }
});