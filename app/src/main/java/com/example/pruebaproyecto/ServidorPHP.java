package com.example.pruebaproyecto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServidorPHP {


    private String urlServidor = "http://192.168.0.106:80/armarioVirtual/";
    private String registrarUsuario = urlServidor + "registrarUsuario.php";
    private String eliminarUsuario = urlServidor + "eliminarUsuario.php";
    private String obtenerUsuario = urlServidor + "obtenerUsuarios.php";

    public ServidorPHP() {
        // Constructor por defecto vacio
    }

    public boolean registrarUsuario(String usuario, String contrasena, String token) throws ServidorPHPException {
        boolean registrado = false;






        /*
        JSONParser parser = new JSONParser();
        JSONObject datos;

        HashMap<String, String> parametros = new HashMap<>();
        if (usuario.isEmpty() && contrasena.isEmpty() && token.isEmpty()) {
            parametros = null;
        } else {
            parametros.put("usuario", usuario);
            parametros.put("password", contrasena);
            parametros.put("token", token);
        }

        try {
            datos = parser.getJSONObjectFromUrl(registrarUsuario, parametros);
            registrado = datos.getBoolean("resultado");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        return registrado;
    }


    public boolean eliminarUsuario(String usuario) throws ServidorPHPException {
        boolean eliminado = false;







        /*
        JSONParser parser = new JSONParser();
        JSONObject datos;

        HashMap<String, String> parametros = new HashMap<>();
        if (usuario.isEmpty()) {
            parametros = null;
        } else {
            parametros.put("usuario", usuario);
        }

        // Obtengo los datos del usuario del servidor

        try {
            datos = parser.getJSONObjectFromUrl(eliminarUsuario, parametros);
            eliminado = datos.getBoolean("resultado");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        return eliminado;
    }

    public ArrayList<String> obtenerTodosUsuarios(String usuario) throws ServidorPHPException {

        JSONParser parser = new JSONParser();
        JSONObject datos;
        ArrayList<String> usuarios = new ArrayList<>();






        /*
        HashMap<String, String> parametros = new HashMap<>();

        if (usuario.isEmpty()) {
            parametros = null;
        } else {
            parametros.put("usuarioActual", usuario);
        }

        // Obtengo los datos de los usuario del servidor

        try {
            datos = parser.getJSONObjectFromUrl(obtenerUsuario, parametros);
            JSONArray usuariosJson = datos.getJSONArray("resultado");
            for(int i = 0; i<usuariosJson.length(); i++) {
                usuarios.add(usuariosJson.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        */
        return usuarios;
    }

}
