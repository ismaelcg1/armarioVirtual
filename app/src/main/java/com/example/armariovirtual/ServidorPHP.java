package com.example.armariovirtual;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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
    private String eliminarUsuario = urlServidor + "eliminarUsuario.php";
    private String obtenerUsuario = urlServidor + "obtenerUsuario.php";
    private String insertarPrenda = urlServidor + "insertarPrenda.php";
    private String insertarPrendaPost = urlServidor + "insertarPrendaPost.php?";
    private String obtenerPrendas = urlServidor + "obtenerPrendas.php";

    private String PARAMETRO_TALLA = "";
    private String PARAMETRO_ESTILO = "";
    private String PARAMETRO_COLOR = "";
    private String PARAMETRO_EPOCA = "";
    private String PARAMETRO_CATEGORIA = "";
    private String PARAMETRO_SUBCATEGORIA = "";
    private String PARAMETRO_MARCA = "";
    private String PARAMETRO_ESTADO = "";

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
        // TODO si la respuesta es null mostrar un toast en la clase que obtiene el resultado
        return miUsuario;
    }


    public void cargarWebService(final String uid, final Context contexto, final String talla, final String estilo, final String color,
                                 final String epoca, final String categoria, final String subcategoria, final int cantidad, final String marca, final String imagenConvertida) {
        final int ESTADO_LIMPIO_TRUE = 1;
        StringRequest stringRequest;
        // Para mostrar al usuario que se está subiendo la prenda
        /*
        final ProgressDialog progreso;

        progreso=new ProgressDialog(contexto);
        progreso.setMessage(contexto.getResources().getString(R.string.subiendoFotoActividadInsertar));
        progreso.show();
*/
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



    public ArrayList<Prenda> obtenerPrendas(String uid, String filtrado, String valorFiltrado) throws ServidorPHPException {
        JSONParser parser = new JSONParser();
        JSONObject datos;
        // Variables;
        int id, cantidad;
        String talla, estilo, color, epoca, categoria, subcategoria, fotoString, marca, estadoLimpio;
        boolean limpio;
        ArrayList<Prenda> todasPrendas = new ArrayList<>();

        Prenda prenda = null;

        parametros = new HashMap<>();
        if (uid.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("uid", uid);

            if (filtrado != null) {
                verFiltrado(filtrado, valorFiltrado);
            }
            asignarParametrosHashMap();
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

                    if (estadoLimpio.equalsIgnoreCase("1")) {
                        limpio = true;
                    } else {
                        limpio = false;
                    }

                    Bitmap imagenPrendaConvertida = Prenda.convertirStringABitmap(fotoString);

                    prenda = new Prenda(id, talla, estilo, color, epoca, categoria, subcategoria, imagenPrendaConvertida, cantidad, marca, limpio);
                    todasPrendas.add(prenda);
                }
                Log.d("LONGITUD ARRAY", ""+todasPrendas.size());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todasPrendas;
    }

    private void asignarParametrosHashMap() {
        parametros.put("talla", PARAMETRO_TALLA);
        parametros.put("estilo", PARAMETRO_ESTILO);
        parametros.put("color", PARAMETRO_COLOR);
        parametros.put("epoca", PARAMETRO_EPOCA);
        parametros.put("categoria", PARAMETRO_CATEGORIA);
        parametros.put("subcategoria", PARAMETRO_SUBCATEGORIA);
        parametros.put("marca", PARAMETRO_MARCA);
        parametros.put("estado_limpio", PARAMETRO_ESTADO);
    }

    private void verFiltrado(String filtrado, String valorFiltrado) {

        if (filtrado.equalsIgnoreCase("talla")) {
            PARAMETRO_TALLA = valorFiltrado;
        } else if (filtrado.equalsIgnoreCase("estilo")) {
            PARAMETRO_ESTILO = valorFiltrado;
        } else if (filtrado.equalsIgnoreCase("color")) {
            PARAMETRO_COLOR = valorFiltrado;
        } else if (filtrado.equalsIgnoreCase("epoca")) {
            PARAMETRO_EPOCA = valorFiltrado;
        } else if (filtrado.equalsIgnoreCase("categoria")) {
            PARAMETRO_CATEGORIA = valorFiltrado;
        } else if (filtrado.equalsIgnoreCase("subcategoria")) {
            PARAMETRO_SUBCATEGORIA = valorFiltrado;
        } else if (filtrado.equalsIgnoreCase("marca")) {
            PARAMETRO_MARCA = valorFiltrado;
        } else if (filtrado.equalsIgnoreCase("estado_limpio")) {
            PARAMETRO_ESTADO = valorFiltrado;
        }
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


    public boolean insertarPrenda(String talla, String estilo, String color, String epoca, String categoria, String subcategoria,
                                  int cantidad, String marca, String imagen, boolean estado_limpio, String uid ) throws ServidorPHPException {
        boolean insertada = false;
        JSONParser parser = new JSONParser();
        JSONObject datos;
        parametros = new HashMap<>();

        if (uid.isEmpty() && talla.isEmpty() && estilo.isEmpty() && color.isEmpty() && categoria.isEmpty() && subcategoria.isEmpty()
                && cantidad == 0 && marca.isEmpty() && imagen.isEmpty() ) {
            parametros = null;
        } else {
            parametros.put("talla", talla);
            parametros.put("estilo", estilo);
            parametros.put("color", color);
            parametros.put("epoca", epoca);
            parametros.put("categoria", categoria);
            parametros.put("subcategoria", subcategoria);
            parametros.put("cantidad", ""+cantidad);
            parametros.put("marca", marca);
            parametros.put("imagenString", imagen);
            parametros.put("estado_limpio", ""+estado_limpio);
            parametros.put("uidUsuario", uid);
        }

        try {
            datos = parser.getJSONObjectFromUrl(insertarPrenda, parametros);
            // Pruebas:
            Log.d("Parametros: ", ""+parametros);
            insertada = datos.getBoolean("resultado");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return insertada;
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
