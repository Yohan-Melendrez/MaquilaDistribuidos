CREATE DATABASE IF NOT EXISTS maquilaQA;
USE maquilaQA;

CREATE TABLE productos (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE errores (
    id_error INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    costo_usd DECIMAL(10,2) NOT NULL,
    nivel_atencion VARCHAR(20) -- BAJO, MEDIO, ALTO, CRITICO
);

CREATE TABLE producto_errores (
    id_producto INT,
    id_error INT,
    PRIMARY KEY (id_producto, id_error),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_error) REFERENCES errores(id_error)
);

CREATE TABLE inspectores (
    id_inspector INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE lotes (
    id_lote INT PRIMARY KEY AUTO_INCREMENT,
    nombre_lote VARCHAR(100) NOT NULL,
    estado VARCHAR(50) DEFAULT 'En proceso',
    id_inspector INT,
    nivel_atencion VARCHAR(20), -- BAJO, MEDIO, ALTO, CRITICO
    FOREIGN KEY (id_inspector) REFERENCES inspectores(id_inspector)
);


CREATE TABLE lote_productos (
    id_lote INT,
    id_producto INT,
    cantidad INT NOT NULL,
    PRIMARY KEY (id_lote, id_producto),
    FOREIGN KEY (id_lote) REFERENCES lotes(id_lote),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

CREATE TABLE inspecciones (
    id_inspeccion INT AUTO_INCREMENT PRIMARY KEY,
    id_lote INT,
    id_producto INT,
    id_error INT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    detalle_error VARCHAR(100),
    inspector VARCHAR(100),
    FOREIGN KEY (id_lote) REFERENCES lotes(id_lote),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_error) REFERENCES errores(id_error)
);

CREATE TABLE lote_inspector (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_lote INT NOT NULL,
    inspector VARCHAR(100) NOT NULL,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_lote) REFERENCES lotes(id_lote)
);

CREATE TABLE notificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    mensaje TEXT,
    leido BOOLEAN DEFAULT FALSE,
    tipo VARCHAR(50),
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_inspector INT,
    FOREIGN KEY (id_inspector) REFERENCES inspectores(id_inspector)
);
-- Insertar inspectores
INSERT INTO inspectores (nombre, activo) VALUES
('Juan', true),
('María', true),
('Luis', true),
('Ana', true),
('Carlos', true),
('Valeria', true),
('Pedro', true),
('Laura', true),
('Sofía', true),
('Miguel', true);