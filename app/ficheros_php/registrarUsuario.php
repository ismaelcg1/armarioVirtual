<?php
/**
******************************************************
* @file registrarUsuario.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
/* IGUAL QUE EN ANDROID LOS PARAMETROS QUE SE LE PASA A GET, O POST*/
$insertar = $conexion->registrarUsuario($_GET["uid"], $_GET["nickusuario"], $_GET["altura"],
									    $_GET["peso"], $_GET["talla_por_defecto"],
									    $_GET["fecha_nacimiento"], $_GET["genero_masculino"]);
$resultado=json_encode($insertar);
echo $resultado;
?>