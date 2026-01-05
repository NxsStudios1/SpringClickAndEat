# Click And Eat

# Base de Datos
# Equipo 9	
#DROP DATABASE CLICKANDEAT;
CREATE DATABASE clickandeat;
USE clickandeat;
SHOW TABLES		;

# ----------------------------
# CREACIÓN DE TABLAS
# ----------------------------

-- -----------------------------------------------------
-- Table `tbl_rol`
-- -----------------------------------------------------
CREATE TABLE tbl_rol (
	id INT PRIMARY KEY AUTO_INCREMENT,
	tipo ENUM('ADMINISTRADOR','CLIENTE') NOT NULL UNIQUE
);

-- -----------------------------------------------------
-- Table `tbl_usuario`
-- -----------------------------------------------------

CREATE TABLE tbl_usuario(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES tbl_rol(id)
);

-- -----------------------------------------------------
-- Table `tbl_comentario`
-- -----------------------------------------------------

CREATE TABLE tbl_comentario(
	id INT PRIMARY KEY AUTO_INCREMENT,
    asunto VARCHAR(100) NOT NULL,
    contenido VARCHAR (2000) NOT NULL,
    calificacion INT CHECK (calificacion >= 1 AND calificacion <= 5) NOT NULL,
    categoria ENUM('COMIDA', 'SERVICIO', 'AMBIENTE', 'TIEMPO_ESPERA', 'GENERAL'),
    fecha_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES tbl_usuario(id)
);

-- -----------------------------------------------------
-- Table `tbl_respuestaComentario`
-- -----------------------------------------------------

CREATE TABLE tbl_respuesta_comentario (
	id INT PRIMARY KEY AUTO_INCREMENT,
    contenido VARCHAR (2000) NOT NULL,
    fecha_respuesta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_comentario INT NOT NULL,
    id_administrador INT NOT NULL,
    FOREIGN KEY (id_comentario) REFERENCES tbl_comentario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_administrador) REFERENCES tbl_usuario(id)
);

-- -----------------------------------------------------
-- Table `tbl_ingrediente`
-- -----------------------------------------------------

CREATE TABLE tbl_ingrediente (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200) NOT NULL, 
    cantidad_porcion DOUBLE NOT NULL,
    unidad_medida ENUM('GRAMOS', 'LITROS', 'MILILITROS', 'UNIDADES', 'KILOGRAMOS') NOT NULL,
    stock_actual DOUBLE NOT NULL,
    precio_unitario DOUBLE NOT NULL
);


-- -----------------------------------------------------
-- Table `tbl_categoriaProducto`
-- -----------------------------------------------------

CREATE TABLE tbl_categoria_producto(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
);

-- -----------------------------------------------------
-- Table `tbl_producto`
-- -----------------------------------------------------

CREATE TABLE tbl_producto( 
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    precio DOUBLE NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES tbl_categoria_producto(id)
);

-- -----------------------------------------------------
-- Table `tbl_producto_ingrediente`
-- -----------------------------------------------------
CREATE TABLE tbl_producto_ingrediente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_producto INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad_ingrediente DOUBLE NOT NULL,
	UNIQUE KEY producto_ingrediente (id_producto, id_ingrediente),
    FOREIGN KEY (id_producto) REFERENCES tbl_producto(id),
    FOREIGN KEY (id_ingrediente) REFERENCES tbl_ingrediente(id)
);

-- -----------------------------------------------------
-- Table `tbl_pedido`
-- -----------------------------------------------------

CREATE TABLE tbl_pedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero_ticket VARCHAR(20) UNIQUE NOT NULL,
    estado ENUM('PENDIENTE', 'EN_PROCESO', 'TERMINADO', 'PAGADO', 'CANCELADO') DEFAULT 'PENDIENTE',
    total DOUBLE NOT NULL,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones VARCHAR(500),
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES tbl_usuario(id)
);

-- -----------------------------------------------------
-- Table `tbl_promocion`
-- -----------------------------------------------------
CREATE TABLE tbl_promocion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),
	fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    precio_total_con_descuento DOUBLE NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- -----------------------------------------------------
-- Table `tbl_productoPromocion`
-- -----------------------------------------------------

CREATE TABLE tbl_promocion_producto(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_producto INT NOT NULL,
    id_promocion INT NOT NULL,
    cantidad_producto DOUBLE NOT NULL,
    UNIQUE KEY producto_promocion (id_producto, id_promocion),
    FOREIGN KEY (id_promocion) REFERENCES tbl_promocion(id),
    FOREIGN KEY (id_producto) REFERENCES tbl_producto(id)
);

-----------------------------------------------------
-- Table tbl_detallePedido`
-- --------------------------------------------------

CREATE TABLE tbl_detalle_pedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo_item ENUM('PRODUCTO', 'PROMOCION') NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    precio_unitario DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    id_producto INT NULL,
    id_promocion INT NULL,
    id_pedido INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES tbl_pedido(id),
    FOREIGN KEY (id_producto) REFERENCES tbl_producto(id),
    FOREIGN KEY (id_promocion) REFERENCES tbl_promocion(id)
);

