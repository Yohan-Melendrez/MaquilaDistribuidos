document.querySelector('.login-form').addEventListener('submit', function (e) {
  e.preventDefault();

  const usuario = document.getElementById('usuario').value;
  const contrasena = document.getElementById('contrasena').value;

  fetch("http://localhost:9093/login?departamento=erp", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      nombre: usuario, contrasena: contrasena
    })
  })
    .then(res => {
      if (!res.ok) throw new Error("Credenciales Incorrectas");
      return res.json();
    })

    .then(data => {
      sessionStorage.setItem("token", data.token);
      sessionStorage.setItem("usuario", data.nombre); 
      sessionStorage.setItem("departamento", "erp");   
      window.location.href = "principal.html";
    })
    .catch(err => {
      alert(err.message);
    });


});

