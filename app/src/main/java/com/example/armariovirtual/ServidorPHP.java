package com.example.armariovirtual;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServidorPHP {

    private String urlServidor = "http://192.168.0.106:80/armarioVirtual/";  // 192.168.2.65  // 192.168.0.106:80
    private String registrarUsuario = urlServidor + "registrarUsuario.php";
    private String actualizarUsuario = urlServidor + "actualizarUsuario.php";
    private String obtenerUsuario = urlServidor + "obtenerUsuario.php";
    private String obtenerTodosUsuarios = urlServidor + "obtenerTodosUsuarios.php";
    private String insertarPrendaPost = urlServidor + "insertarPrendaPost.php?";
    private String obtenerPrendas = urlServidor + "obtenerPrendas.php";
    private String contarPrendasUsuario = urlServidor + "obtenerCantidadPrendas.php";
    private String eliminarArmario = urlServidor + "eliminarArmarioUsuario.php";
    private String eliminarUsuario= urlServidor + "eliminarUsuario.php";
    private String eliminarPrenda= urlServidor + "eliminarPrendaTurno.php";
    private String obtenerPrendasIntercambio = urlServidor + "obtenerPrendasIntercambio.php";
    private String actualizarIntercambio = urlServidor + "actualizarIntercambio.php";
    private String contarUsuarios = urlServidor + "obtenerCantidadUsuarios.php";
    private String contarPrendasUsuariosTotales = urlServidor + "obtenerCantidadPrendasTotales.php";

    private String parametro_talla = "";
    private String parametro_estilo = "";
    private String parametro_color = "";
    private String parametro_epoca = "";
    private String parametro_categoria = "";
    private String parametro_subcategoria = "";
    private String parametro_marca = "";
    private String parametro_estado = "";

    private HashMap<String, String> parametros;


    public ServidorPHP() {
        // Constructor por defecto vacio
    }

    public boolean registrarUsuario(String uid, String nickusuario, int altura, int peso, String tallaPorDefecto,
                                    String fecha_nacimiento, boolean genero_masculino) throws ServidorPHPException {
        boolean registrado = false;

        JSONParser parser = new JSONParser();
        JSONObject datos;
        parametros = new HashMap<>();

        if (uid.isEmpty() && nickusuario.isEmpty() && altura == 0 && peso == 0 && fecha_nacimiento.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
            parametros.put("nickusuario", nickusuario);
            parametros.put("altura", ""+altura);
            parametros.put("peso", ""+peso);
            parametros.put("talla_por_defecto", tallaPorDefecto);
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

    public Usuario obtenerUsuario(String uid) throws ServidorPHPException {
        JSONParser parser = new JSONParser();
        JSONObject datos;

        Usuario miUsuario = null;
        parametros = new HashMap<>();

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

                String aliasUser = datosUsuario.getString("alias");
                int alturaUser = datosUsuario.getInt("altura");
                int pesoUser = datosUsuario.getInt("peso");
                String fecha_nacimientoUser = datosUsuario.getString("fecha_nacimiento");
                String tallaUser = datosUsuario.getString("talla_por_defecto");
                int genero_masculinoUser = datosUsuario.getInt("genero_masculino");
                Sexo sexo;

                if (genero_masculinoUser == 0) {
                    sexo = Sexo.Masculino;
                } else {
                    sexo = Sexo.Femenino;
                }
                // String nickName, Sexo sexo, String fechaNacimiento, int altura, int peso)
                miUsuario = new Usuario(aliasUser, sexo, tallaUser, fecha_nacimientoUser, alturaUser, pesoUser);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return miUsuario;
    }


    public void cargarWebService(final String uid, final Context contexto, final String talla, final String estilo, final String color,
                                 final String epoca, final String categoria, final String subcategoria, final int cantidad, final String marca, final String imagenConvertida) {
        final int ESTADO_LIMPIO_TRUE = 1;
        StringRequest stringRequest;

        stringRequest=new StringRequest(Request.Method.POST, insertarPrendaPost, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //progreso.hide();

                if (response.trim().equalsIgnoreCase("true")){
                    Toast.makeText(contexto,contexto.getResources().getString(R.string.prendaRegistroCorrectoActividadInsertar),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(contexto,contexto.getResources().getString(R.string.prendaRegistroIncorrectoActividadInsertar),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto,"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                //progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                parametros=new HashMap<>();

                parametros.put("talla", talla);
                parametros.put("estilo", estilo);
                parametros.put("color", color);
                parametros.put("epoca", epoca);
                parametros.put("categoria", categoria);
                parametros.put("subcategoria", subcategoria);
                parametros.put("cantidad", ""+cantidad);
                parametros.put("marca", marca);
                parametros.put("imagenString", imagenConvertida);
                parametros.put("estado_limpio", ""+ESTADO_LIMPIO_TRUE);
                parametros.put("uidUsuario", uid);

                return parametros;
            }
        };
        //request.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(contexto).addToRequestQueue(stringRequest);
    }


    public int obtenerCantidadPrendas (String uid) {
        JSONParser parser = new JSONParser();
        JSONObject datos;
        int contarPrendas = 0;
        parametros = new HashMap<>();

        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
        }

        try {
            datos = parser.getJSONObjectFromUrl(contarPrendasUsuario, parametros);
            String prendasTotales = datos.getString("cantidadPrendas");
            Boolean consultaCorrecta = datos.getBoolean("resultado");

            if (consultaCorrecta) {
                contarPrendas = Integer.parseInt(prendasTotales);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contarPrendas;
    }


    public boolean eliminarArmario (String uid) throws ServidorPHPException {
        JSONParser parser = new JSONParser();
        JSONObject datos;
        parametros = new HashMap<>();
        Boolean borradoRealizado = false;

        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
        }

        try {
            datos = parser.getJSONObjectFromUrl(eliminarArmario, parametros);
            Boolean borrado = datos.getBoolean("resultado");
            if (borrado) {
                borradoRealizado = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return borradoRealizado;
    }


    public ArrayList<Prenda> obtenerPrendas(String uid, String filtrado, String valorFiltrado) throws ServidorPHPException {
        JSONParser parser = new JSONParser();
        JSONObject datos;
        // Variables;
        int id, cantidad, es_intercambio;
        String talla, estilo, color, epoca, categoria, subcategoria, fotoString, marca, estadoLimpio;
        boolean limpio;
        ArrayList<Prenda> todasPrendas = new ArrayList<>();

        Prenda prenda = null;
        parametros = new HashMap<>();
        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
            parametros.put("filtrado", filtrado);
            parametros.put("valorFiltrado", valorFiltrado);
        }

        try {
            datos = parser.getJSONObjectFromUrl(obtenerPrendas, parametros);

            JSONArray arrayPrendas = datos.getJSONArray("datos");
            Boolean consultaCorrecta = datos.getBoolean("resultado");

            if (consultaCorrecta) {
                for (int i = 0; i < arrayPrendas.length(); i++) {
                    JSONObject obj = (JSONObject) arrayPrendas.get(i);
                    id = obj.getInt("id");
                    talla = obj.getString("talla");
                    estilo = obj.getString("estilo");
                    color = obj.getString("color");
                    epoca = obj.getString("epoca");
                    categoria = obj.getString("categoria");
                    subcategoria = obj.getString("subcategoria");
                    fotoString = obj.getString("foto");
                    cantidad = obj.getInt("cantidad");
                    marca = obj.getString("marca");
                    estadoLimpio = obj.getString("estado_limpio");
                    es_intercambio = obj.getInt("es_intercambio");

                    if (estadoLimpio.equalsIgnoreCase("1")) {
                        limpio = true;
                    } else {
                        limpio = false;
                    }

                    Bitmap imagenPrendaConvertida = Prenda.convertirStringABitmap(fotoString);

                    prenda = new Prenda(id, talla, estilo, color, epoca, categoria, subcategoria, imagenPrendaConvertida, cantidad, marca, limpio, es_intercambio);
                    todasPrendas.add(prenda);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todasPrendas;
    }


    public boolean actualizarUsuario(String uid, int altura, int peso, String tallaPorDefecto) throws ServidorPHPException {

        Boolean consultaCorrecta = false;
        JSONParser parser = new JSONParser();
        JSONObject datos;
        parametros = new HashMap<>();

        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
            parametros.put("alturaNueva", ""+altura);
            parametros.put("pesoNuevo", ""+peso);
            parametros.put("tallaNueva", tallaPorDefecto);
        }

        try {
            datos = parser.getJSONObjectFromUrl(actualizarUsuario, parametros);
            consultaCorrecta = datos.getBoolean("resultado");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return consultaCorrecta;
    }


    public boolean eliminarUsuario(String uid) throws ServidorPHPException {
        boolean eliminado = false;

        JSONParser parser = new JSONParser();
        JSONObject datos;

        HashMap<String, String> parametros = new HashMap<>();
        if (uid.isEmpty()) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
        }

        try {
            datos = parser.getJSONObjectFromUrl(eliminarUsuario, parametros);
            eliminado = datos.getBoolean("resultado");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return eliminado;
    }


    public boolean eliminarPrenda (String uid, int id) throws ServidorPHPException {
        boolean eliminado = false;

        JSONParser parser = new JSONParser();
        JSONObject datos;

        HashMap<String, String> parametros = new HashMap<>();
        if (uid.isEmpty()) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
            parametros.put("id", ""+id);
        }

        try {
            datos = parser.getJSONObjectFromUrl(eliminarPrenda, parametros);
            eliminado = datos.getBoolean("resultado");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return eliminado;
    }



    public ArrayList<Prenda> obtenerPrendasIntercambio(String uid, String valor) throws ServidorPHPException {
        JSONParser parser = new JSONParser();
        JSONObject datos;
        // Variables;
        int id, cantidad, es_intercambio;
        String talla, estilo, color, epoca, categoria, subcategoria, fotoString, marca, estadoLimpio;
        boolean limpio;
        ArrayList<Prenda> prendasIntercambio = new ArrayList<>();
        Prenda prenda = null;
        parametros = new HashMap<>();

        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
            parametros.put("valor_pasado", valor);
        }

        try {
            datos = parser.getJSONObjectFromUrl(obtenerPrendasIntercambio, parametros);

            JSONArray arrayPrendas = datos.getJSONArray("datos");
            Boolean consultaCorrecta = datos.getBoolean("resultado");

            if (consultaCorrecta) {
                for (int i = 0; i < arrayPrendas.length(); i++) {
                    JSONObject obj = (JSONObject) arrayPrendas.get(i);
                    id = obj.getInt("id");
                    talla = obj.getString("talla");
                    estilo = obj.getString("estilo");
                    color = obj.getString("color");
                    epoca = obj.getString("epoca");
                    categoria = obj.getString("categoria");
                    subcategoria = obj.getString("subcategoria");
                    fotoString = obj.getString("foto");
                    cantidad = obj.getInt("cantidad");
                    marca = obj.getString("marca");
                    estadoLimpio = obj.getString("estado_limpio");
                    es_intercambio = obj.getInt("es_intercambio");

                    if (estadoLimpio.equalsIgnoreCase("1")) {
                        limpio = true;
                    } else {
                        limpio = false;
                    }

                    Bitmap imagenPrendaConvertida = Prenda.convertirStringABitmap(fotoString);

                    prenda = new Prenda(id, talla, estilo, color, epoca, categoria, subcategoria, imagenPrendaConvertida, cantidad, marca, limpio, es_intercambio);
                    prendasIntercambio.add(prenda);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prendasIntercambio;
    }


    public boolean actualizarIntercambio (String uid, int idPrenda, int valorSeleccionado) throws ServidorPHPException {

        Boolean consultaCorrecta = false;
        JSONParser parser = new JSONParser();
        JSONObject datos;
        parametros = new HashMap<>();

        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
            parametros.put("id_prenda", ""+idPrenda);
            parametros.put("intercambio", ""+valorSeleccionado);
        }

        try {
            datos = parser.getJSONObjectFromUrl(actualizarIntercambio, parametros);
            consultaCorrecta = datos.getBoolean("resultado");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return consultaCorrecta;
    }


    public ArrayList<Usuario> obtenerTodosUsuarios(String uid) throws ServidorPHPException {
        JSONParser parser = new JSONParser();
        JSONObject datos;
        // Variables;
        String uidUsuario, nickName, fechaNacimiento, tallaPorDefecto;
        Sexo sexo;
        int altura, peso;
        int genero = 0;
        ArrayList<Usuario> todosUsuarios = new ArrayList<>();

        Usuario usuario = null;
        parametros = new HashMap<>();
        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
        }

        try {
            datos = parser.getJSONObjectFromUrl(obtenerTodosUsuarios, parametros);

            JSONArray arrayUsuarios = datos.getJSONArray("datos");
            Boolean consultaCorrecta = datos.getBoolean("resultado");

            if (consultaCorrecta) {
                for (int i = 0; i < arrayUsuarios.length(); i++) {
                    JSONObject obj = (JSONObject) arrayUsuarios.get(i);
                    uidUsuario = obj.getString("uid");
                    nickName = obj.getString("nick_usuario");
                    fechaNacimiento = obj.getString("fecha_nacimiento");
                    tallaPorDefecto = obj.getString("talla_por_defecto");
                    genero = obj.getInt("genero_masculino");
                    altura = obj.getInt("altura");
                    peso = obj.getInt("peso");

                    if (genero == 0) {
                        sexo = Sexo.Masculino;
                    } else {
                        sexo = Sexo.Femenino;
                    }

                    usuario = new Usuario(nickName, sexo, tallaPorDefecto, fechaNacimiento, altura, peso, uidUsuario);
                    todosUsuarios.add(usuario);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todosUsuarios;
    }

    public int obtenerCantidadUsuarios (String uid) {
        JSONParser parser = new JSONParser();
        JSONObject datos;
        int contadorUsuarios = 0;
        parametros = new HashMap<>();

        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
        }

        try {
            datos = parser.getJSONObjectFromUrl(contarUsuarios, parametros);
            String usuariosTotales = datos.getString("cantidadUsuarios");
            Boolean consultaCorrecta = datos.getBoolean("resultado");

            if (consultaCorrecta) {
                contadorUsuarios = Integer.parseInt(usuariosTotales);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contadorUsuarios;
    }


    public int obtenerCantidadPrendasTotales (String uid) {
        JSONParser parser = new JSONParser();
        JSONObject datos;
        int contarPrendas = 0;
        parametros = new HashMap<>();

        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);
        }

        try {
            datos = parser.getJSONObjectFromUrl(contarPrendasUsuariosTotales, parametros);
            String prendasTotales = datos.getString("cantidadPrendas");
            Boolean consultaCorrecta = datos.getBoolean("resultado");

            if (consultaCorrecta) {
                contarPrendas = Integer.parseInt(prendasTotales);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contarPrendas;
    }

}
