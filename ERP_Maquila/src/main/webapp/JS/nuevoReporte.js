/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('formNuevoReporte');

  form.addEventListener('submit', (e) => {
    e.preventDefault();

    const tipoDefecto = document.getElementById('tipoDefecto').value;
    const lotesRechazados = document.getElementById('lotesRechazados').value;
    const costoTotal = document.getElementById('costoTotal').value;
    const detalles = document.getElementById('detalles').value;

    if (!tipoDefecto || !lotesRechazados || !costoTotal || !detalles) {
      alert('Por favor, completa todos los campos.');
      return;
    }

    // Aquí podrías guardar el reporte (por ejemplo en localStorage o enviarlo a un backend)
    alert('Reporte guardado correctamente.');
    window.location.href = 'reportes.html';
  });
});


