<?php
/**
******************************************************
* @file actualizarIntercambio.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
$actualizar = $conexion->actualizarPrendaIntercambio($_GET["uid"], $_GET["id_prenda"], $_GET["intercambio"]); 
$resultado = json_encode($actualizar);
echo $resultado;
?>
