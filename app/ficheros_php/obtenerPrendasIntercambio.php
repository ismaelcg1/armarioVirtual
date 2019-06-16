<?php
/**
*******************************************************
* @file obtenerPrendasIntercambio.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
$obtener = $conexion->obtenerPrendasIntercambio($_GET["uid"], $_GET["valor_pasado"]); 
$resultado = json_encode($obtener);
echo $resultado;
?>
