<?php
/**
******************************************************
* @file obtenersitios.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
$borrar = $conexion->eliminarUsuario($_GET["uid"]); 
$resultado=json_encode($borrar);
echo $resultado;
?>