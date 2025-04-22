/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
  const listaReportes = document.getElementById('listaReportes');

  // Datos simulados de reportes
  const reportes = [
    { id: 1, nombre: 'Reporte de Rendimiento - Abril 2025' },
    { id: 2, nombre: 'Reporte de Producción - Semana 14' },
    { id: 3, nombre: 'Reporte de Empaque - Q1' },
    { id: 4, nombre: 'Reporte de Presentación - Línea B' },
    { id: 5, nombre: 'Reporte de Rendimiento - Planta Norte' },
    { id: 6, nombre: 'Reporte Especial - Auditoría Interna' },
  ];

  // Generar dinámicamente la lista
  reportes.forEach(reporte => {
    const item = document.createElement('div');
    item.className = 'reporte-item';

    const nombre = document.createElement('span');
    nombre.textContent = reporte.nombre;

    const botonVer = document.createElement('button');
    botonVer.className = 'ver-btn';
    botonVer.textContent = 'Ver';
    botonVer.addEventListener('click', () => {
      // Redirige a la pantalla de detalle (puedes pasar el id por querystring si lo deseas)
      window.location.href = 'detalleReporte.html';
    });

    item.appendChild(nombre);
    item.appendChild(botonVer);
    listaReportes.appendChild(item);
  });
});
