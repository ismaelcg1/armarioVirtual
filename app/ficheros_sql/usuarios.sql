CREATE TABLE IF NOT EXISTS usuarios ( 
	uid VARCHAR(50) NOT NULL, 
	nick_usuario VARCHAR(20) NOT NULL,
	altura INT NOT NULL,
	peso INT NOT NULL,
	talla_por_defecto VARCHAR(3) NOT NULL,	
	fecha_nacimiento DATE NOT NULL,  
	genero_masculino BOOLEAN NOT NULL, 			
	PRIMARY KEY(uid) 
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
