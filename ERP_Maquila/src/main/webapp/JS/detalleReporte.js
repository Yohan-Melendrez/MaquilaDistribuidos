/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
  // Obtener contenedor para los detalles
  const detalleInfo = document.getElementById('detalleInfo');
  
  // Simular los datos del reporte que vendrían de la página anterior o base de datos
  const reporteData = {
    tipoDefecto: "RENDIMIENTO",
    lotesRechazados: 3,
    costoTotal: "$ 10,000 PESOS",
    detalles: "3 LOTES DE BASURADORA NO FUNCIONAN CORRECTAMENTE, UNAS NO PRENDEN OTRAS NO TIENEN LA POTENCIA CORRECTA CUANDO ESTÁN ENCENDIDAS Y OTRAS NO CORTAN"
  };
  
  // Crear los elementos de visualización (no editables)
  detalleInfo.innerHTML = `
    <div class="campo">
      <label class="etiqueta">Tipo de defecto</label>
      <span class="valor">${reporteData.tipoDefecto}</span>
    </div>
    
    <div class="campo">
      <label class="etiqueta">Total de lotes rechazados según el tipo de defecto</label>
      <input type="text" class="valor" value="${reporteData.lotesRechazados}" readonly>
    </div>
    
    <div class="campo">
      <label class="etiqueta">Costo total de las piezas rechazadas</label>
      <input type="text" class="valor" value="${reporteData.costoTotal}" readonly>
    </div>
    
    <div class="campo">
      <label class="etiqueta">Detalles de las piezas rechazadas</label>
      <textarea class="valor" readonly>${reporteData.detalles}</textarea>
    </div>
  `;
});
