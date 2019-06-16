<?php

/**
********************************************************
*  	@file Conexion.php
*  	@brief Clase para conectar PHP con MySQL
********************************************************
*/

define("DB_HOST", 		"localhost:3306");
define("DB_DATABASE", 	"armario_virtual");
define("DB_USER", 		"root");
define("DB_PASSWORD", 	"7Ch29OeuWwQCYzXg");

class Conexion
{ 
	private $conn = null;
	/**
	* Description: Constructor de la clase
	* Conecta automáticamente con la base de datos
	*/
	function __construct() 
	{
		$this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
		$this->conn->query("set names utf8");
	}

	/**
	* Description: Destructor de la clase
	* Cierra la conexión automáticamente con la base de datos
	*/
	function __destruct() 
	{
		$this->conn->close();
	}


	public function registrarUsuario($uid, $nickusuario, $altura, $peso, $talla_por_defecto, 
									 $fecha_nacimiento, $genero_masculino) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}
		// Convertimos las variables peso y altura a entero:
		$pesoInt = (int) $peso;
		$alturaInt = (int) $altura;

		// Vemos lo que nos llega de la variable $genero_masculino
		$genero_usuario = (int) $genero_masculino;

		$insertar = "INSERT INTO usuarios (uid, nick_usuario, altura, peso, talla_por_defecto, 
					 fecha_nacimiento, genero_masculino) VALUES ('$uid', '$nickusuario', '$alturaInt' , '$pesoInt' ,
					 '$talla_por_defecto' , '$fecha_nacimiento' , '$genero_usuario')";

		$result = $this->conn->query($insertar);
		
		return array("resultado" => $result);
	}  


	public function obtenerUsuario($uidActual) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}
		// 
		$obtener = "SELECT nick_usuario, altura, peso, fecha_nacimiento, talla_por_defecto, genero_masculino FROM usuarios WHERE uid ='$uidActual'";

		$result = array();
		foreach ($this->conn->query($obtener) as $indiceFila => $value) {
			$result["alias"] = $value["nick_usuario"];
			$result["altura"] = $value["altura"];
			$result["peso"] = $value["peso"];
			$result["talla_por_defecto"] = $value["talla_por_defecto"];
			$result["fecha_nacimiento"] = $value["fecha_nacimiento"];
			$result["genero_masculino"] = $value["genero_masculino"];
		}

		$resultSQL = true;
		if (empty($result)) $resultSQL = false;

		return array("datos" => $result, "resultado" => $resultSQL);
	}


	public function actualizarUsuario($uidUsuario, $alturaNueva, $pesoNuevo, $tallaNueva) {
		
		$actualizarUsuario;

		if (mysqli_connect_errno()) {
			printf("Fallo de la conexión: %s", mysqli_connect_errno());
			exit();
		}

		if (!empty($alturaNueva) && !empty($pesoNuevo) && !empty($tallaNueva)) {
			$actualizarUsuario = "UPDATE usuarios SET altura='$alturaNueva', peso='$pesoNuevo', 
							  talla_por_defecto='$tallaNueva' WHERE uid='$uidUsuario'";
		}
		$result = $this->conn->query($actualizarUsuario);
		
		return array("resultado" => $result);
	}


	public function insertarPrendaPost ($talla, $estilo, $color, $epoca, $categoria, $subcategoria, $cantidad, 
								  		$marca, $imagenString, $estado_limpio, $uidUsuario) {

		if (mysqli_connect_errno()) {
			printf("Fallo de la conexión: %s", mysqli_connect_errno());
			exit();

		}
		$path = "imagenesArmario/";

		if (!file_exists($path)) {
			mkdir($path);
		}
		$pathUser = $path."$uidUsuario/";

		if (!file_exists($pathUser)) {
			mkdir($pathUser);
		}
		//date_default_timezone_set("Europe/Madrid");
		$cadena_fecha_actual = date("d-m-y_H.i.s");
        $nombreFoto = $cadena_fecha_actual.".jpg"; // ".png";
		file_put_contents($pathUser.$nombreFoto,base64_decode($imagenString));
		
		$insertarPrenda = "INSERT INTO prendas (talla, estilo, color, epoca, categoria, subcategoria, cantidad, marca, 						ruta_foto, estado_limpio) VALUES ('$talla', '$estilo', '$color', '$epoca', '$categoria',
					 	   '$subcategoria', '$cantidad', '$marca', '".$pathUser.$nombreFoto."', '$estado_limpio')";

		$result = $this->conn->query($insertarPrenda);		
		$idPrenda = $this->conn->insert_id;

		$insertarPrendaRelacionUsuario = "INSERT INTO prendas_usuarios (id_prenda, id_usuario, es_regalo, es_intercambio) 
							VALUES ('".$idPrenda."', '$uidUsuario', '0' , '0')";		

		// Para insertar accesorios sería: 
		// "insert into accesorios (id_prenda, tipo) values ('".$idPrenda."', '$tipo')";	 	   
				
		$result2 = $this->conn->query($insertarPrendaRelacionUsuario);

		return (boolean)$result&&(boolean)$result2;		
	}


	public function obtenerPrendas($uidActual, $filtrado, $valorFiltrado) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$result = array();
		$resultSQL = true;
		
		if ($filtrado == 'sin_filtros') {
			$obtener = "SELECT id, talla, estilo, color, epoca, categoria, subcategoria, cantidad, marca, ruta_foto, 		estado_limpio, es_intercambio FROM prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario ='$uidActual'";
		} else {
			$obtener = "SELECT id, talla, estilo, color, epoca, categoria, subcategoria, cantidad, marca, ruta_foto, 		estado_limpio, es_intercambio FROM prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario ='$uidActual' AND $filtrado='$valorFiltrado'";
		} 

		foreach ($this->conn->query($obtener) as $indiceFila => $value) {
			$result[$indiceFila]["id"] = $value["id"];
			$result[$indiceFila]["talla"] = $value["talla"];
			$result[$indiceFila]["estilo"] = $value["estilo"];
			$result[$indiceFila]["color"] = $value["color"];
			$result[$indiceFila]["epoca"] = $value["epoca"];
			$result[$indiceFila]["categoria"] = $value["categoria"];
			$result[$indiceFila]["subcategoria"] = $value["subcategoria"];
			$result[$indiceFila]["cantidad"] = $value["cantidad"];
			$result[$indiceFila]["marca"] = $value["marca"];
			$result[$indiceFila]["estado_limpio"] = $value["estado_limpio"];
			$result[$indiceFila]["es_intercambio"] = $value["es_intercambio"];

			$im = file_get_contents($value["ruta_foto"]);
			$imdata = base64_encode($im);
			$result[$indiceFila]["foto"] = $imdata;
		}

		if (empty($result)) $resultSQL = false;

		return array("datos" => $result, "resultado" => $resultSQL);
	}


	public function contarPrendasUsuario($uid) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$resultSQL = true;
		// Se tiene que poner el GROUP BY de la tabla primera, sino no funciona
		$contarPrendas = "SELECT COUNT(*) FROM prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario ='$uid' GROUP BY prendas.id";
		$result = $this->conn->query($contarPrendas); 

		$numeroDePrendas = mysqli_num_rows($result);

		if ($numeroDePrendas == 0) $resultSQL = false;

		return array("cantidadPrendas" => $numeroDePrendas, "resultado" => $resultSQL);
	}


	public function eliminarArmarioUsuario ($uid) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$resultSQL = true;

		$quitarRevisionClavesForaneas= "SET FOREIGN_KEY_CHECKS=0";		
		$result1 = $this->conn->query($quitarRevisionClavesForaneas); 

		$consultaBorrarArmario = "DELETE prendas_usuarios, prendas FROM  prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario ='$uid'";

		$result = $this->conn->query($consultaBorrarArmario); 

		$habilitarRevisionClavesForaneas= "SET FOREIGN_KEY_CHECKS=1";
		$result2 = $this->conn->query($habilitarRevisionClavesForaneas); 

		if (empty($result)) {
			$resultSQL = false;
		} else {
			// Eliminamos todas las fotos contenidas en el directorio - fotos_usuario_actual - y seguidamente 
			// eliminamos la carpeta o directorio 

			$fotos_usuario_actual = "imagenesArmario/$uid";    // Carpeta que contine archivos y queremos eliminar  

			foreach(glob($fotos_usuario_actual."/*.*") as $archivos_carpeta)  
			{  
			 	unlink($archivos_carpeta);     // Eliminamos todos los archivos de la carpeta hasta dejarla vacia  
			}  
			rmdir($fotos_usuario_actual);         // Eliminamos la carpeta vacia  
		}

		return array("resultado" => $resultSQL);
	}



	public function eliminarPrendaSeleccionada ($uid, $id) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$resultSQL = true;

		$obtenerRutaImagen = "SELECT ruta_foto FROM prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario ='$uid' AND id_prenda = '$id'";

		$queryRutaImagen = $this->conn->query($obtenerRutaImagen);

		// Para obtener el String de la consulta, utilizamos la query anterior y la función fetch_assoc()
		$rutaImagenString = $queryRutaImagen->fetch_assoc();

//		print_r($rutaImagenString);

		// -----
		$quitarRevisionClavesForaneas= "SET FOREIGN_KEY_CHECKS=0";		
		$result1 = $this->conn->query($quitarRevisionClavesForaneas); 

		$consultaBorrarPrenda = "DELETE prendas_usuarios, prendas FROM prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario ='$uid' AND id_prenda = '$id'";

		$result = $this->conn->query($consultaBorrarPrenda); 

		$habilitarRevisionClavesForaneas= "SET FOREIGN_KEY_CHECKS=1";
		$result2 = $this->conn->query($habilitarRevisionClavesForaneas); 
		// -----

		if (empty($result)) {
			$resultSQL = false;
		} else {
			// Eliminamos la foto del directorio: $rutaImagenString
			unlink($rutaImagenString['ruta_foto']);
		}

		return array("resultado" => $resultSQL);
	}


	public function obtenerPrendasIntercambio($uid, $valorPasado) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}
		if ($valorPasado == "mis_prendas") {
			$obtener = "SELECT id, talla, estilo, color, epoca, categoria, subcategoria, cantidad, marca, ruta_foto, 		estado_limpio, es_intercambio FROM prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario ='$uid' AND es_intercambio='1'";
		} else {
			$obtener = "SELECT id, talla, estilo, color, epoca, categoria, subcategoria, cantidad, marca, ruta_foto, 		estado_limpio, es_intercambio FROM prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario !='$uid' AND es_intercambio='1'";		
		}

		$resultSQL = true;

		foreach ($this->conn->query($obtener) as $indiceFila => $value) {
			$result[$indiceFila]["id"] = $value["id"];
			$result[$indiceFila]["talla"] = $value["talla"];
			$result[$indiceFila]["estilo"] = $value["estilo"];
			$result[$indiceFila]["color"] = $value["color"];
			$result[$indiceFila]["epoca"] = $value["epoca"];
			$result[$indiceFila]["categoria"] = $value["categoria"];
			$result[$indiceFila]["subcategoria"] = $value["subcategoria"];
			$result[$indiceFila]["cantidad"] = $value["cantidad"];
			$result[$indiceFila]["marca"] = $value["marca"];
			$result[$indiceFila]["estado_limpio"] = $value["estado_limpio"];
			$result[$indiceFila]["es_intercambio"] = $value["es_intercambio"];

			$im = file_get_contents($value["ruta_foto"]);
			$imdata = base64_encode($im);
			$result[$indiceFila]["foto"] = $imdata;
		}

		if (empty($result)) $resultSQL = false;

		return array("datos" => $result, "resultado" => $resultSQL);

	}
	

	public function eliminarUsuario($uid) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$borrarUsuario = "DELETE FROM usuarios WHERE uid = '$uid'";

		$result = $this->conn->query($borrarUsuario);		
		
		return array("resultado" => $result);
	}


	public function actualizarPrendaIntercambio($uid, $idPrenda, $numIntercambio) { // $numIntercambio -> 0 ó 1 (depende de como esté el switch)
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$resultSQL = true;

		$actualizarIntercambio = "UPDATE prendas_usuarios SET es_intercambio = '$numIntercambio' WHERE id_usuario = '$uid' AND id_prenda='$idPrenda'";

		$result = $this->conn->query($actualizarIntercambio);

		if (empty($result)) $resultSQL = false;		
		
		return array("resultado" => $resultSQL);
	}


// Para hacer el intercambio, ponemos el uid de la otra persona, actualizando la tabla prendas_usuarios y es intercambio a 0
	public function realizarIntercambio($uid1, $uid2, $idPrenda1, $idPrenda2) { 
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}
/*
		$actualizarIntercambio = "UPDATE prendas_usuarios SET es_intercambio = '$numIntercambio' WHERE id_usuario = '$uid' AND id_prenda='$idPrenda'";

		$result = $this->conn->query($actualizarIntercambio);		
*/		
		return array("resultado" => $result);
	}

	public function contarUsuarios($uid) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$resultSQL = true;
		$contarPrendas = "SELECT COUNT(*) FROM usuarios WHERE uid !='$uid' GROUP BY uid";
		$result = $this->conn->query($contarPrendas); 

		$numeroDePrendas = mysqli_num_rows($result);

		if ($numeroDePrendas == 0) $resultSQL = false;

		return array("cantidadUsuarios" => $numeroDePrendas, "resultado" => $resultSQL);
	}

// Esta función se podría ahorrar poniendo un poco más compleja la de contarPrendasUsuario
	public function contarPrendasTotalesUsuarios($uid) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$resultSQL = true;
		// Se tiene que poner el GROUP BY de la tabla primera, sino no funciona
		$contarPrendas = "SELECT COUNT(*) FROM prendas INNER JOIN prendas_usuarios ON prendas.id = prendas_usuarios.id_prenda WHERE id_usuario !='$uid' GROUP BY prendas.id";
		$result = $this->conn->query($contarPrendas); 

		$numeroDePrendas = mysqli_num_rows($result);

		if ($numeroDePrendas == 0) $resultSQL = false;

		return array("cantidadPrendas" => $numeroDePrendas, "resultado" => $resultSQL);
	}


	public function obtenerTodosUsuarios($uidActual) {
		// Verificamos la conexión
		if (mysqli_connect_errno())
		{
			printf("Falló la conexión: %s\n", mysqli_connect_error());
			exit();
		}

		$result = array();
		$resultSQL = true;

		$obtener = "SELECT * FROM usuarios WHERE uid != '$uidActual'";

		foreach ($this->conn->query($obtener) as $indiceFila => $value) {
			$result[$indiceFila]["uid"] = $value["uid"];
			$result[$indiceFila]["nick_usuario"] = $value["nick_usuario"];
			$result[$indiceFila]["fecha_nacimiento"] = $value["fecha_nacimiento"];
			$result[$indiceFila]["talla_por_defecto"] = $value["talla_por_defecto"];
			$result[$indiceFila]["genero_masculino"] = $value["genero_masculino"];
			$result[$indiceFila]["altura"] = $value["altura"];
			$result[$indiceFila]["peso"] = $value["peso"];
		}

		if (empty($result)) $resultSQL = false;

		return array("datos" => $result, "resultado" => $resultSQL);
	}
}
?>