<?php
/**
******************************************************
* @file eliminarPrendaTurno.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();
$borrar = $conexion->eliminarPrendaSeleccionada($_GET["uid"], $_GET["id"]); 
$resultado=json_encode($borrar);
echo $resultado;
?>