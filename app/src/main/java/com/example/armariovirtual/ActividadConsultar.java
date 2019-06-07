package com.example.armariovirtual;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.armariovirtual.MainActivityDrawer.NUMERO_PRENDAS;
import static com.example.armariovirtual.MainActivityDrawer.SEXO_USUARIO;
import static com.example.armariovirtual.MainActivityDrawer.UID_USUARIO_KEY;

public class ActividadConsultar extends AppCompatActivity implements View.OnClickListener, DialogPropiedadesPrenda.acabarDialog,
        DialogoSeleccionarEstacionPersonalizado.finalizarDialog, DialogoSeleccionarColorPersonalizado.finalizarDialogColores {

    private RecyclerView listaPrendas;
    private ArrayList<Prenda> prendas;
    private Toolbar appToolbar;
    private String uidUsuario, categoriaSeleccionada, opSeleccion;
    private ServidorPHP objetoServidor;
    private final int TIEMPO_ESPERA_INICIAL = 500;
    private Context contexto;
    private Button bFiltrar;
    private Spinner spinnerFiltros;
    private String [] arrayFiltros;
    private LinearLayout layoutFiltroEscogido;
    private TextView tvBusquedaFiltro;
    private Boolean esMasculino;
    private String filtrado;
    private String valorFiltrado;
    private LinearLayout layoutSinElementos;
    private int cantidadPrendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_consultar);

        inicializarVariables();

        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (cantidadPrendas < 1) {
            layoutSinElementos.setVisibility(View.VISIBLE);
        } else {
            layoutSinElementos.setVisibility(View.GONE);

            listaPrendas.setOnClickListener(this);
            bFiltrar.setOnClickListener(this);

            // Para que el usuario no tenga que esperar a que se rellene el array con la peticion 'Sin filtros', le mostramos una animación:
            abrirSplashEsperarDatos();

            listaPrendas.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space_recycler_consultar, true, true));
            // Con esto el tamaño del recyclerwiew no cambiará
            listaPrendas.setHasFixedSize(true);
            // Creo un layoutmanager para el recyclerview
            LinearLayoutManager llm = new LinearLayoutManager(this);
            listaPrendas.setLayoutManager(llm);

            obtenerDatosServidor();
        }
    }

    private void inicializarVariables() {
        obtenerDatosUsuario();
        contexto = this;
        prendas = new ArrayList<>();
        objetoServidor = new ServidorPHP();
        listaPrendas = findViewById(R.id.recyclerConsultar);
        // Toobar
        appToolbar = findViewById(R.id.appToolbar);
        appToolbar.setTitle(R.string.texto3MainActivityDrawer);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        bFiltrar = findViewById(R.id.btnFiltrar);
        spinnerFiltros = findViewById(R.id.spinnerConsultar);
        arrayFiltros = contexto.getResources().getStringArray(R.array.arrayFiltros);
        layoutFiltroEscogido = findViewById(R.id.layoutFiltroEscogido);
        layoutSinElementos = findViewById(R.id.layoutSinElementosConsultar);
        tvBusquedaFiltro = findViewById(R.id.tvBusquedaFiltro);
        opSeleccion = "";
        categoriaSeleccionada = "";
        esMasculino = true;
        filtrado = "sin_filtros";
        valorFiltrado = "";
    }

    private void obtenerDatosUsuario() {
        Intent intentActividadActual = getIntent();
        Bundle b = intentActividadActual.getExtras();
        if(b!=null) {
            uidUsuario = (String) b.get(UID_USUARIO_KEY);
            cantidadPrendas =  b.getInt(NUMERO_PRENDAS);

            Sexo sexoUsuario = (Sexo) b.get(SEXO_USUARIO);
            if (sexoUsuario == Sexo.Femenino) {
                esMasculino = false;
            }
        }
    }


    private void obtenerDatosServidor() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    prendas = objetoServidor.obtenerPrendas(uidUsuario, filtrado, valorFiltrado);
                    // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
                    AdaptadorPrendas adaptadorPrendas = new AdaptadorPrendas(contexto, prendas);
                    listaPrendas.setAdapter(adaptadorPrendas);
                    adaptadorPrendas.refrescar();
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
            }
        }, TIEMPO_ESPERA_INICIAL);
    }


    private void abrirSplashEsperarDatos() {
        Intent intent = new Intent(this, SplashScreenObtenerDatos.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFiltrar) {
            String seleccion = spinnerFiltros.getSelectedItem().toString();

            verSeleccionSpinner(seleccion);
        }
    }

    private void verSeleccionSpinner(String seleccion) {

        if (seleccion.equalsIgnoreCase(arrayFiltros[0])) {
            opSeleccion = "";
            filtrado = "sin_filtros";
            valorFiltrado = "";
            layoutFiltroEscogido.setVisibility(View.GONE);
            obtenerDatosFiltrados();
        } else if (seleccion.equalsIgnoreCase(arrayFiltros[1])) {
            opSeleccion = "Talla";
            new DialogPropiedadesPrenda(ActividadConsultar.this, ActividadConsultar.this, opSeleccion, null);

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[2])) {
            opSeleccion = "Estilo";
            new DialogPropiedadesPrenda(ActividadConsultar.this, ActividadConsultar.this, opSeleccion, null);

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[3])) {
            opSeleccion = "Color";
            new DialogoSeleccionarColorPersonalizado(ActividadConsultar.this, ActividadConsultar.this);

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[4])) {
            opSeleccion = "Epoca";
            new DialogoSeleccionarEstacionPersonalizado(ActividadConsultar.this, ActividadConsultar.this);

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[5])) {
            opSeleccion = "Categoria";
            new DialogPropiedadesPrenda(ActividadConsultar.this, ActividadConsultar.this, opSeleccion, null);

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[6])) {
            opSeleccion = "Marca";
            new DialogPropiedadesPrenda(ActividadConsultar.this, ActividadConsultar.this, opSeleccion, null);

        }
    }


    @Override
    public void cogerParametro(String seleccionado) {
        tvBusquedaFiltro.setText(getResources().getString(R.string.textoBusquedaActividadConsultar, opSeleccion, seleccionado));
        layoutFiltroEscogido.setVisibility(View.VISIBLE);

        filtrado = opSeleccion;
        valorFiltrado = seleccionado;

        switch (opSeleccion) {
            case "Talla":
            case "Estilo":
            case "Subcategoria":
            case "Marca":
                    obtenerDatosFiltrados();
                break;
            case "Categoria":
                categoriaSeleccionada = seleccionado;
                dialogCorrespondienteSubcategoria();
                layoutFiltroEscogido.setVisibility(View.GONE);
                break;
            default:
                // Por defecto
                break;
        }
    }

    private void obtenerDatosFiltrados() {
        try {
            prendas = objetoServidor.obtenerPrendas(uidUsuario, filtrado, valorFiltrado);
            // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
            AdaptadorPrendas adaptadorPrendas = new AdaptadorPrendas(contexto, prendas);
            listaPrendas.setAdapter(adaptadorPrendas);
            adaptadorPrendas.refrescar();
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        filtrado = "";
        valorFiltrado = "";
    }

    private void dialogCorrespondienteSubcategoria() {
        opSeleccion = "Subcategoria";
        String genero;
        if (esMasculino) {
            genero = "Masculino";
        } else {
            genero = "Femenino";
        }
        new DialogPropiedadesPrenda(ActividadConsultar.this, ActividadConsultar.this, genero, categoriaSeleccionada);
    }

    @Override
    public void cogerColor(String seleccion) {
        tvBusquedaFiltro.setText(getResources().getString(R.string.textoBusquedaActividadConsultar, opSeleccion, seleccion));
        layoutFiltroEscogido.setVisibility(View.VISIBLE);
        filtrado = opSeleccion;
        valorFiltrado = seleccion;
        obtenerDatosFiltrados();
    }

    @Override
    public void cogerString(String seleccion) {
        tvBusquedaFiltro.setText(getResources().getString(R.string.textoBusquedaActividadConsultar, opSeleccion, seleccion));
        layoutFiltroEscogido.setVisibility(View.VISIBLE);
        filtrado = opSeleccion;
        valorFiltrado = seleccion;
        obtenerDatosFiltrados();
    }
}
