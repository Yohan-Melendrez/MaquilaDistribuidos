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
    costo_usd DECIMAL(10,2) NOT NULL
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


CREATE TABLE reportes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo_defecto VARCHAR(255) NOT NULL,
    total_piezas_rechazadas BIGINT NOT NULL,
    costo_total_usd DOUBLE NOT NULL,
    costo_total_mxn DOUBLE NOT NULL,
    detalles_rechazo TEXT,
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

