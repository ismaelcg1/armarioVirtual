<?php
/**
*******************************************************
* @file obtenerPrendas.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
$uidActual = $_GET["uid"];
$filtrado = $_GET["filtrado"]; 
$valorFiltrado = $_GET["valorFiltrado"]; 

$obtener = $conexion->obtenerPrendas($uidActual, $filtrado, $valorFiltrado); 

$resultado = json_encode($obtener);
echo $resultado;
?>
