-- Usar base de datos
USE maquila;

-- Insertar productos
INSERT INTO productos (nombre, descripcion) VALUES
('Cepillo Eléctrico', 'Cepillo con motor de limpieza'),
('Cortadora de Cabello', 'Cortadora con cuchillas de titanio'),
('Afeitadora Facial', 'Afeitadora recargable con cabezal flotante'),
('Shampoo Anticaspa', 'Envase de 250ml para control de caspa'),
('Jabón Neutro', 'Jabón dermatológico sin fragancia'),
('Gel Antibacterial', 'Botella de 100ml con 70% alcohol');

-- Insertar errores
INSERT INTO errores (nombre, descripcion, costo_usd) VALUES
('Motor Defectuoso', 'El motor no enciende o se apaga solo', 2.00),
('Cuchillas Dañadas', 'Cuchillas rotas o fuera de alineación', 1.50),
('Batería Deficiente', 'Batería no carga correctamente', 1.20),
('Interruptor Fallando', 'El botón no responde al tacto', 0.90),
('Envase Dañado', 'Botella rota o agrietada', 0.35),
('Etiqueta Mal Pegada', 'Etiqueta torcida o fuera de lugar', 0.15),
('Producto Mal Sellado', 'Sello de seguridad incompleto', 0.50);

-- Relación producto-error
INSERT INTO producto_errores (id_producto, id_error) VALUES
-- Cepillo Eléctrico (1)
(1, 1), (1, 3), (1, 4),
-- Cortadora de Cabello (2)
(2, 1), (2, 2), (2, 4),
-- Afeitadora Facial (3)
(3, 1), (3, 3),
-- Shampoo Anticaspa (4)
(4, 5), (4, 6),
-- Jabón Neutro (5)
(5, 6), (5, 7),
-- Gel Antibacterial (6)
(6, 5), (6, 7);

-- Insertar lotes
INSERT INTO lotes (id_lote, nombre_lote, estado) VALUES
(10, 'Lote 10 - Cepillos', 'En proceso'),
(11, 'Lote 11 - Cortadoras', 'En proceso'),
(12, 'Lote 12 - Afeitadoras', 'En proceso'),
(13, 'Lote 13 - Shampoos', 'En proceso'),
(14, 'Lote 14 - Jabones', 'En proceso'),
(15, 'Lote 15 - Antibacterial', 'En proceso');

-- Asignar productos a lotes
INSERT INTO lote_productos (id_lote, id_producto, cantidad) VALUES
(10, 1, 10),
(11, 2, 8),
(12, 3, 12),
(13, 4, 20),
(14, 5, 30),
(15, 6, 25);

-- Asignar lotes a inspectores
INSERT INTO lote_inspector (id_lote, inspector) VALUES
(10, 'juan'),
(11, 'juan'),
(12, 'juan'),
(13, 'juan'),
(14, 'juan'),
(15, 'juan');

-- Insertar datos de inspecciones 
-- (id_inspeccion, id_lote, id_producto, id_error, fecha, inspector, detalleDefecto)

-- Inspecciones para Lote 10 - Cepillos Eléctricos
INSERT INTO inspecciones (id_lote, id_producto, id_error, fecha, inspector, detalle_error) VALUES
(10, 1, 1, '2025-04-20 09:15:00', 'juan', 'Motor no enciende después de 3 intentos, posible problema en el circuito principal'),  -- Motor defectuoso
(10, 1, 3, '2025-04-20 09:30:00', 'juan', 'Batería pierde carga en menos de 5 minutos de uso continuo'),  -- Batería deficiente
(10, 1, 4, '2025-04-20 10:45:00', 'juan', 'Interruptor requiere múltiples presiones para activarse'),  -- Interruptor fallando
(10, 1, 1, '2025-04-21 08:30:00', 'juan', 'Motor emite ruido excesivo y se detiene durante uso'),  -- Otro motor defectuoso
(10, 1, 3, '2025-04-21 11:20:00', 'juan', 'Indicador de batería muestra error constante, no carga completamente'),  -- Otra batería deficiente
(10, 1, NULL, '2025-04-21 14:15:00', 'juan', NULL); -- Inspección sin errores detectados

-- Inspecciones para Lote 11 - Cortadoras de Cabello
INSERT INTO inspecciones (id_lote, id_producto, id_error, fecha, inspector, detalle_error) VALUES
(11, 2, 2, '2025-04-22 08:10:00', 'juan', 'Cuchilla superior desalineada, corta de manera irregular'),  -- Cuchillas dañadas
(11, 2, 2, '2025-04-22 09:35:00', 'juan', 'Dientes de la cuchilla rotos en el extremo derecho'),  -- Más cuchillas dañadas
(11, 2, 1, '2025-04-22 10:50:00', 'juan', 'Motor se sobrecalienta después de 2 minutos de uso'),  -- Motor defectuoso
(11, 2, 4, '2025-04-23 09:05:00', 'juan', 'Interruptor de velocidad se queda atascado en posición alta'),  -- Interruptor fallando
(11, 2, NULL, '2025-04-23 11:30:00', 'juan', NULL); -- Inspección sin errores detectados

-- Inspecciones para Lote 12 - Afeitadoras Faciales
INSERT INTO inspecciones (id_lote, id_producto, id_error, fecha, inspector, detalle_error) VALUES
(12, 3, 1, '2025-04-24 08:20:00', 'juan', 'Motor pierde potencia durante uso, velocidad irregular'),  -- Motor defectuoso
(12, 3, 3, '2025-04-24 09:45:00', 'juan', 'Batería no mantiene carga, se agota después de un uso'),  -- Batería deficiente
(12, 3, 3, '2025-04-24 11:10:00', 'juan', 'Conector de carga no hace contacto adecuado con base'),  -- Otra batería deficiente
(12, 3, 1, '2025-04-25 08:15:00', 'juan', 'Motor se detiene cuando se presiona contra la piel'),  -- Otro motor defectuoso
(12, 3, NULL, '2025-04-25 09:30:00', 'juan', NULL),  -- Inspección sin errores detectados
(12, 3, NULL, '2025-04-25 10:45:00', 'juan', NULL); -- Otra inspección sin errores detectados

-- Inspecciones para Lote 13 - Shampoos Anticaspa
INSERT INTO inspecciones (id_lote, id_producto, id_error, fecha, inspector, detalle_error) VALUES
(13, 4, 5, '2025-04-26 08:30:00', 'juan', 'Fisura en la base del envase, gotea al presionar'),  -- Envase dañado
(13, 4, 6, '2025-04-26 09:15:00', 'juan', 'Etiqueta frontal desplazada 5mm hacia la derecha'),  -- Etiqueta mal pegada
(13, 4, 5, '2025-04-26 10:20:00', 'juan', 'Tapa no cierra completamente, permite entrada de aire'),  -- Otro envase dañado
(13, 4, 6, '2025-04-26 11:05:00', 'juan', 'Etiqueta con burbujas de aire en la parte inferior'),  -- Otra etiqueta mal pegada
(13, 4, 5, '2025-04-27 08:40:00', 'juan', 'Envase con deformidad en la parte superior'),  -- Más envases dañados
(13, 4, NULL, '2025-04-27 09:55:00', 'juan', NULL); -- Inspección sin errores detectados

-- Inspecciones para Lote 14 - Jabones Neutro
INSERT INTO inspecciones (id_lote, id_producto, id_error, fecha, inspector, detalle_error) VALUES
(14, 5, 6, '2025-04-28 08:10:00', 'juan', 'Etiqueta con pliegues en la esquina superior izquierda'),  -- Etiqueta mal pegada
(14, 5, 7, '2025-04-28 09:20:00', 'juan', 'Película protectora despegada en un 25% del perímetro'),  -- Producto mal sellado
(14, 5, 6, '2025-04-28 10:30:00', 'juan', 'Etiqueta colocada al revés'),  -- Otra etiqueta mal pegada
(14, 5, 7, '2025-04-28 11:40:00', 'juan', 'Sello incompleto en la parte superior del empaque'),  -- Otro producto mal sellado
(14, 5, NULL, '2025-04-29 08:25:00', 'juan', NULL),  -- Inspección sin errores detectados
(14, 5, NULL, '2025-04-29 09:40:00', 'juan', NULL); -- Otra inspección sin errores detectados

-- Inspecciones para Lote 15 - Gel Antibacterial
INSERT INTO inspecciones (id_lote, id_producto, id_error, fecha, inspector, detalle_error) VALUES
(15, 6, 5, '2025-04-30 08:15:00', 'juan', 'Grieta en la parte lateral del envase de 3mm'),  -- Envase dañado
(15, 6, 7, '2025-04-30 09:25:00', 'juan', 'Tapón dosificador mal enroscado, permite fuga de producto'),  -- Producto mal sellado
(15, 6, 5, '2025-04-30 10:35:00', 'juan', 'Deformación en la base que impide que se mantenga vertical'),  -- Otro envase dañado
(15, 6, 7, '2025-04-30 11:45:00', 'juan', 'Sello de seguridad roto antes de apertura'),  -- Otro producto mal sellado
(15, 6, 5, '2025-05-01 08:30:00', 'juan', 'Envase con manchas de color extraño en el plástico'),  -- Más envases dañados
(15, 6, NULL, '2025-05-01 09:45:00', 'juan', NULL); -- Inspección sin errores detectados

INSERT INTO reportes (tipo_defecto, total_piezas_rechazadas, costo_total_usd, detalles_rechazo
) VALUES ('Soldadura mala',5,120.50,'Lote: L001, Producto: Motor A, Inspector: Juan, Fecha: 2025-05-01 10:45; Lote: L002, 
Producto: Motor B, Inspector: Ana, Fecha: 2025-05-02 11:30'
);

