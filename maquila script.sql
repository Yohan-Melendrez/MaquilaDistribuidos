CREATE DATABASE maquila;
USE maquila;

CREATE TABLE productos (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

-- Crear tabla de errores
CREATE TABLE errores (
    id_error INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    id_producto INT,
    costo_usd DECIMAL(10,2) NOT NULL,
	FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

-- Tabla intermedia producto-error (relación N a N)
CREATE TABLE producto_errores (
    id_producto INT,
    id_error INT,
    PRIMARY KEY (id_producto, id_error),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_error) REFERENCES errores(id_error)
);
CREATE TABLE lotes (
    id_lote INT PRIMARY KEY AUTO_INCREMENT,
    nombre_lote VARCHAR(100) NOT NULL,
    estado VARCHAR(50) DEFAULT 'En proceso' -- o 'Enviado a QA', 'Rechazado', etc.
);

CREATE TABLE lote_productos (
    id_lote INT,
    id_producto INT,
    cantidad INT NOT NULL,
    PRIMARY KEY (id_lote, id_producto),
    FOREIGN KEY (id_lote) REFERENCES lotes(id_lote),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);
-- Insertar productos
INSERT INTO productos (nombre, descripcion) VALUES
('Shampoo Anticaspa', 'Envase de 250ml para control de caspa'),
('Jabón Neutro', 'Jabón dermatológico sin fragancia'),
('Gel Antibacterial', 'Botella de 100ml con 70% alcohol'),
('Crema Corporal', 'Crema hidratante para piel seca'),
('Toallitas Húmedas', 'Paquete de 40 unidades, sin alcohol');

-- Insertar errores
INSERT INTO errores (nombre, descripcion, costo_usd,id_producto) VALUES
('Envase Dañado', 'Botella rota o agrietada', 0.35,1),
('Etiqueta Mal Pegada', 'Etiqueta torcida o fuera de lugar', 0.15,1),
('Producto Mal Sellado', 'Sello de seguridad incompleto', 0.50,1),
('Contenido Incorrecto', 'Producto dentro no corresponde al envase', 1.25,2),
('Cantidad Incorrecta', 'Volumen menor al especificado', 0.40,2);

-- Asociar productos con errores (puedes ajustar según lógica real)
INSERT INTO producto_errores (id_producto, id_error) VALUES
(1, 1), (1, 2), (1, 3),
(2, 2), (2, 4),
(3, 1), (3, 3), (3, 5),
(4, 2), (4, 3), (4, 4),
(5, 1), (5, 5);