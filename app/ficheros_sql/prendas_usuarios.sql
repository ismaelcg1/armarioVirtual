CREATE TABLE prendas_usuarios (
	id_prenda INT NOT NULL,
	id_usuario VARCHAR(50)NOT NULL,
	es_regalo BOOLEAN NOT NULL DEFAULT 0, /*SI ES REGALO NO ESTARÁ EN NUESTRO ARMARIO, OTRA SECCIÓN DEDICADA*/	
	es_intercambio BOOLEAN NOT NULL DEFAULT 0, /* Prenda que está o no en la sección intercambio */
	PRIMARY KEY (id_prenda, id_usuario),
	FOREIGN KEY (id_prenda)	REFERENCES prendas (id),
	FOREIGN KEY (id_usuario) REFERENCES usuarios (uid)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

