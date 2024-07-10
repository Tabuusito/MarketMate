CREATE DATABASE TFG;

USE TFG;

CREATE TABLE Producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    referencia VARCHAR(50) UNIQUE NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    familia ENUM('papeleria', 'libros', 'regalos') NOT NUL,
    imagen VARCHAR(255)
);

INSERT INTO Producto (nombre, referencia, cantidad, precio, familia)
VALUES ('Producto 1', '12345', 100, 9.99, 'papeleria');

INSERT INTO Producto (nombre, referencia, cantidad, precio, familia)
VALUES ('Producto 2', '67890', 50, 14.99, 'libros');

INSERT INTO Producto (nombre, referencia, cantidad, precio, familia)
VALUES ('Producto 3', '00001', 50, 14.99, 'regalos');

INSERT INTO Producto (nombre, referencia, cantidad, precio, familia)
VALUES ('Producto 4', '00111', 50, 14.99, 'libros');

CREATE TABLE Venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    total DECIMAL(10, 2) NOT NULL
);

CREATE TABLE DetalleVenta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES Venta(id),
    FOREIGN KEY (producto_id) REFERENCES Producto(id)
);

CREATE TABLE Caja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    primer_ticket_id INT NOT NULL,
    ultimo_ticket_id INT NOT NULL,
    monto_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (primer_ticket_id) REFERENCES Venta(id),
    FOREIGN KEY (ultimo_ticket_id) REFERENCES Venta(id)
);