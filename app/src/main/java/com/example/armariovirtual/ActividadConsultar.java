package com.example.armariovirtual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ActividadConsultar extends AppCompatActivity implements View.OnClickListener, DialogPropiedadesPrenda.acabarDialog {

    private RecyclerView listaPrendas;
    private ArrayList<Prenda> prendas;
    private Toolbar appToolbar;
    private FirebaseUser user;
    private ServidorPHP objetoServidor;
    private final int TIEMPO_ESPERA_INICIAL = 850;
    private Context contexto;
    private Button bFiltrar;
    private Spinner spinnerFiltros;
    private String [] arrayFiltros;
    private LinearLayout layoutFiltroEscogido;
    private TextView tvBusquedaFiltro;
    private String opSeleccion;

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

    private void inicializarVariables() {
        contexto = this;
        prendas = new ArrayList<>();
        objetoServidor = new ServidorPHP();
        user = FirebaseAuth.getInstance().getCurrentUser();
        listaPrendas = findViewById(R.id.recyclerConsultar);
        // Toobar
        appToolbar = findViewById(R.id.appToolbar);
        appToolbar.setTitle(R.string.texto3MainActivityDrawer);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        bFiltrar = findViewById(R.id.btnFiltrar);
        spinnerFiltros = findViewById(R.id.spinnerConsultar);
        arrayFiltros = contexto.getResources().getStringArray(R.array.arrayFiltros);
        layoutFiltroEscogido = findViewById(R.id.layoutFiltroEscogido);
        tvBusquedaFiltro = findViewById(R.id.tvBusquedaFiltro);
        opSeleccion = "";
    }


    private void obtenerDatosServidor() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    prendas = objetoServidor.obtenerPrendas(user.getUid(), null, null);
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
            opSeleccion = " ";

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[1])) {
            opSeleccion = "Talla";
            new DialogPropiedadesPrenda(ActividadConsultar.this, ActividadConsultar.this, opSeleccion, null);

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[2])) {
            // Estilo

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[3])) {
            // Color

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[4])) {
            // Epoca

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[5])) {
            // Subcategoria

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[6])) {
            // Marca

        } else if (seleccion.equalsIgnoreCase(arrayFiltros[7])) {
            // Estado

        }

    }

    @Override
    public void cogerParametro(String seleccionado) {
        tvBusquedaFiltro.setText(getResources().getString(R.string.textoBusquedaActividadConsultar, opSeleccion, seleccionado));
        layoutFiltroEscogido.setVisibility(View.VISIBLE);

        switch (opSeleccion) {
            case "Talla":
                // TODO Hacer consulta a BBDD
                break;
                default:
                    break;
        }
    }
}
