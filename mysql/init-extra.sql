-- Crear base de datos para el ERP e Inspección
CREATE DATABASE IF NOT EXISTS maquila;

-- Crear usuario appuser solo si no existe
CREATE USER IF NOT EXISTS 'appuser'@'%' IDENTIFIED BY 'apppass';

-- Asignar permisos completos a appuser sobre la base maquila
GRANT ALL PRIVILEGES ON maquila.* TO 'appuser'@'%';

-- Aplicar cambios
FLUSH PRIVILEGES;
