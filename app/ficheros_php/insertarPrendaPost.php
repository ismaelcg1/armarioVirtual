<?php
/**
******************************************************
* @file insertarPrendaPost.php
* @brief Comprobamos que todo funciona OK
*******************************************************
*/

require_once 'Conexion.php';
$conexion = new Conexion();

$obtener = $conexion->insertarPrendaPost ($_POST["talla"], $_POST["estilo"], $_POST["color"], $_POST["epoca"], 
										$_POST["categoria"], $_POST["subcategoria"], $_POST["cantidad"], 
										$_POST["marca"], $_POST["imagenString"], $_POST["estado_limpio"],
										$_POST["uidUsuario"]); 
$resultado = json_encode($obtener);
echo $resultado;
?>

