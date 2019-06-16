<?php
/**
******************************************************
* @file actualizarUsuario.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
$obtener = $conexion->actualizarUsuario($_GET["uid"], $_GET["alturaNueva"], $_GET["pesoNuevo"], $_GET["tallaNueva"]); 
$resultado = json_encode($obtener);
echo $resultado;
?>
