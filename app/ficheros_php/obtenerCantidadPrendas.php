<?php
/**
*******************************************************
* @file obtenerCantidadPrendas.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
$uidActual = $_GET["uid"];

$obtener = $conexion->contarPrendasUsuario($uidActual); 

$resultado = json_encode($obtener);
echo $resultado;
?>
