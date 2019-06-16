<?php
/**
******************************************************
* @file eliminarArmarioUsuario.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
$borrarArmario = $conexion->eliminarArmarioUsuario($_GET["uid"]);
$resultado=json_encode($borrarArmario);
echo $resultado;
?>