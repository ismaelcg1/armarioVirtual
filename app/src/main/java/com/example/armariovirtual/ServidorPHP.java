package com.example.armariovirtual;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServidorPHP {


    private String urlServidor = "http://192.168.0.106:80/armarioVirtual/";
    private String registrarUsuario = urlServidor + "registrarUsuario.php";
    private String eliminarUsuario = urlServidor + "eliminarUsuario.php";
    private String obtenerUsuario = urlServidor + "obtenerUsuario.php";

    public ServidorPHP() {
        // Constructor por defecto vacio
    }

    public boolean registrarUsuario(String uid, String nickusuario, int altura, int peso, String fecha_nacimiento,
                                    boolean genero_masculino) throws ServidorPHPException {
        boolean registrado = false;


        JSONParser parser = new JSONParser();
        JSONObject datos;

        HashMap<String, String> parametros = new HashMap<>();
        if (uid.isEmpty() && nickusuario.isEmpty() && altura == 0 && peso == 0 && fecha_nacimiento.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
            parametros.put("nickusuario", nickusuario);
            parametros.put("altura", ""+altura);
            parametros.put("peso", ""+peso);
            parametros.put("fecha_nacimiento", fecha_nacimiento);
            if (genero_masculino) {
                parametros.put("genero_masculino", "0");
            } else {
                parametros.put("genero_masculino", "1");
            }
        }

        try {
            datos = parser.getJSONObjectFromUrl(registrarUsuario, parametros);
            registrado = datos.getBoolean("resultado");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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


    public Usuario obtenerUsuario(String uid) throws ServidorPHPException {

        JSONParser parser = new JSONParser();
        JSONObject datos;

        Usuario miUsuario = null;


        HashMap<String, String> parametros = new HashMap<>();
        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
        }

        try {
            datos = parser.getJSONObjectFromUrl(obtenerUsuario, parametros);

            JSONObject datosUsuario = datos.getJSONObject("datos");
            Boolean consultaCorrecta = datos.getBoolean("resultado");


            if (consultaCorrecta) {

                // TODO si da fallo algún int, coger primero String y después realizar la conversión
                String aliasUser = datosUsuario.getString("alias");
                int alturaUser = datosUsuario.getInt("altura");
                int pesoUser = datosUsuario.getInt("peso");
                String fecha_nacimientoUser = datosUsuario.getString("fecha_nacimiento");
                int genero_masculinoUser = datosUsuario.getInt("genero_masculino");
                Sexo sexo;

                if (genero_masculinoUser == 0) {
                    sexo = Sexo.Masculino;
                } else {
                    sexo = Sexo.Femenino;
                }
                // String nickName, Sexo sexo, String fechaNacimiento, int altura, int peso)
                miUsuario = new Usuario(aliasUser, sexo, fecha_nacimientoUser, alturaUser, pesoUser);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO si la respuesta es null mostrar un toast en la clase que obtiene el resultado
        return miUsuario;
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
