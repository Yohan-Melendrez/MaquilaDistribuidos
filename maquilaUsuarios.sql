CREATE DATABASE maquila_usuarios;
USE maquila_usuarios;

CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    departamento VARCHAR(100),
    contrasena VARCHAR(255)
);

INSERT INTO usuarios (nombre, departamento, contrasena) VALUES
('qa', 'qa', '$2a$10$NNOG3LecMiIRyoY/SF.sGOIBIMbPia4a1udrpy2uRj.JuSs.bBOXW');