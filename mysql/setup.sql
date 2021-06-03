create table appgro.gen_categoria_producto (
  id_categoria int not null auto_increment,
  categoria varchar(150) null,
  reg_date timestamp null default current_timestamp(),
  imagen text null,
  constraint gen_categoria_producto_pkey
    primary key (id_categoria)
);
create table appgro.gen_etiqueta (
  id_etiqueta int not null auto_increment,
  etiqueta varchar(150) null,
  reg_date timestamp null default current_timestamp(),
  constraint gen_etiqueta_pkey
    primary key (id_etiqueta)
);
create table appgro.gen_pais (
  id_pais int not null auto_increment,
  nombre varchar(50) not null,
  codigo varchar(50) null,
  constraint gen_pais_pkey
    primary key (id_pais)
);
create table appgro.gen_parametro (
  id_parametro int not null auto_increment,
  nombre varchar(100) null,
  descripcion varchar(1000) null,
  valor varchar(4000) null,
  estado varchar(20) null,
  tipo_parametro varchar(100) null,
  reg_date timestamp null default current_timestamp(),
  constraint gen_parametro_pkey
    primary key (id_parametro)
);
create table appgro.gen_perfil (
  id_perfil int not null auto_increment,
  nombre varchar(50) not null,
  descripcion varchar(100) null,
  reg_date timestamp not null default current_timestamp(),
  constraint gen_perfil_pkey
    primary key (id_perfil)
);
create table appgro.gen_rol (
  id_rol int not null auto_increment,
  nombre varchar(50) not null,
  descripcion varchar(100) null,
  reg_date timestamp not null default current_timestamp(),
  constraint gen_rol_pkey
    primary key (id_rol)
);
create table appgro.gen_usuario (
  id_usuario bigint not null auto_increment,
  nombre_completo varchar(500) not null,
  primer_apellido varchar(250) not null,
  segundo_apellido varchar(250) not null,
  correo varchar(500) not null,
  celular varchar(150) not null,
  contrasena varchar(128) not null,
  reg_date timestamp null default current_timestamp(),
  estado varchar(20) null,
  constraint gen_usuario_correo_key
    unique (correo),
  constraint gen_usuario_pkey
    primary key (id_usuario)
);
create table appgro.gen_departamento (
  id_departamento int not null auto_increment,
  id_pais int not null,
  nombre varchar(50) not null,
  codigo varchar(50) null,
  constraint gen_departamento_pkey
    primary key (id_departamento),
  constraint gen_departamento_id_pais_fkey
    foreign key (id_pais)
    references gen_pais (id_pais)
);
create table appgro.gen_perfil_rol (
  id_perfil int not null,
  id_rol int not null,
  constraint gen_perfil_rol_pkey
    primary key (id_perfil, id_rol),
  constraint gen_perfil_rol_id_perfil_fkey
    foreign key (id_perfil)
    references gen_perfil (id_perfil),
  constraint gen_perfil_rol_id_rol_fkey
    foreign key (id_rol)
    references gen_rol (id_rol)
);
create table appgro.gen_recup_contrasena (
  id_usuario bigint not null,
  `token` varchar(40) not null,
  expiracion timestamp not null,
  contrasena_nueva varchar(100) null,
  estado_cambio varchar(15) null,
  reg_date timestamp null default current_timestamp(),
  constraint gen_recup_contrasena_pkey
    primary key (id_usuario, token),
  constraint gen_recup_contrasena_id_usuario_fkey
    foreign key (id_usuario)
    references gen_usuario (id_usuario)
);
create table appgro.gen_usuario_perfil (
  id_usuario bigint not null,
  id_perfil int not null,
  constraint gen_usuario_perfil_pkey
    primary key (id_usuario, id_perfil),
  constraint gen_usuario_perfil_id_perfil_fkey
    foreign key (id_perfil)
    references gen_perfil (id_perfil),
  constraint gen_usuario_perfil_id_usuario_fkey
    foreign key (id_usuario)
    references gen_usuario (id_usuario)
);
create table appgro.gen_usuario_tarifas (
  id_tarifa bigint not null auto_increment,
  id_usuario bigint not null,
  tarifa float8 not null,
  rango_inicial float8 not null,
  rango_final float8 null,
  constraint gen_usuario_tarifa_pkey
    primary key (id_tarifa),
  constraint gen_usuario_tarifa_id_usuario_fkey
    foreign key (id_usuario)
    references gen_usuario (id_usuario)
);
create table appgro.gen_ciudad (
  id_ciudad int not null auto_increment,
  id_departamento int not null,
  nombre varchar(50) not null,
  codigo varchar(50) null,
  constraint gen_ciudad_pkey
    primary key (id_ciudad),
  constraint gen_ciudad_id_departamento_fkey
    foreign key (id_departamento)
    references gen_departamento (id_departamento)
);
create table appgro.gen_tienda (
  id_tienda int not null auto_increment,
  id_usuario bigint not null,
  id_ciudad int not null,
  nombre varchar(150) null,
  descripcion varchar(500) null,
  direccion varchar(100) null,
  telefono1 varchar(20) null,
  telefono2 varchar(20) null,
  correo varchar(50) null,
  estado varchar(20) null,
  reg_date timestamp null default current_timestamp(),
  imagen text null,
  longitud float8 null,
  latitud float8 null,
  constraint gen_tienda_pkey
    primary key (id_tienda),
  constraint gen_tienda_id_ciudad_fkey
    foreign key (id_ciudad)
    references gen_ciudad (id_ciudad),
  constraint gen_tienda_id_usuario_fkey
    foreign key (id_usuario)
    references gen_usuario (id_usuario)
);
create table appgro.gen_usuario_ubicacion (
  id_ubicacion bigint not null auto_increment,
  id_usuario bigint not null,
  id_ciudad int not null,
  barrio varchar(50) null,
  via varchar(70) not null,
  numero_via varchar(20) not null,
  cuadrante varchar(20) not null,
  datos_adicionales varchar(150) null,
  telefono varchar(20) not null,
  predeterminado smallint not null default 0,
  longitud float8 null,
  latitud float8 null,
  constraint gen_usuario_ubicacion_pkey
    primary key (id_ubicacion),
  constraint gen_usuario_ubicacion_id_ciudad_fkey
    foreign key (id_ciudad)
    references gen_ciudad (id_ciudad),
  constraint gen_usuario_ubicacion_id_usuario_fkey
    foreign key (id_usuario)
    references gen_usuario (id_usuario)
);
create table appgro.gen_producto (
  id_producto int not null auto_increment,
  id_tienda int not null,
  id_categoria int not null,
  nombre varchar(150) null,
  descripcion varchar(500) null,
  precio float8 null,
  disponibilidad int null,
  reg_date timestamp null default current_timestamp(),
  imagen text null,
  unidad_medida varchar(100) null,
  constraint gen_producto_pkey
    primary key (id_producto),
  constraint gen_producto_id_categoria_fkey
    foreign key (id_categoria)
    references gen_categoria_producto (id_categoria),
  constraint gen_producto_id_tienda_fkey
    foreign key (id_tienda)
    references gen_tienda (id_tienda)
);
create table appgro.gen_producto_etiqueta (
  id_producto int not null,
  id_etiqueta int not null,
  constraint gen_producto_etiqueta_pkey
    primary key (id_producto, id_etiqueta),
  constraint gen_producto_etiqueta_id_etiqueta_fkey
    foreign key (id_etiqueta)
    references gen_etiqueta (id_etiqueta),
  constraint gen_producto_etiqueta_id_producto_fkey
    foreign key (id_producto)
    references gen_producto (id_producto)
);
create table appgro.app_carrito_compra (
  id_carrito_compra bigint not null auto_increment,
  id_comprador bigint not null,
  id_producto int not null,
  cantidad int not null,
  aclaracion varchar(500) null,
  estado varchar(50) not null,
  reg_date timestamp null default current_timestamp(),
  fecha_entrega timestamp null,
  id_ubicacion bigint not null default 1,
  precio float8 null,
  flete float8 null,
  constraint app_carrito_compra_pkey
    primary key (id_carrito_compra),
  constraint app_carrito_compra_id_comprador_fkey
    foreign key (id_comprador)
    references gen_usuario (id_usuario),
  constraint app_carrito_compra_id_producto_fkey
    foreign key (id_producto)
    references gen_producto (id_producto),
  constraint app_carrito_compra_id_ubicacion_fkey
    foreign key (id_ubicacion)
    references gen_usuario_ubicacion (id_ubicacion)
);
