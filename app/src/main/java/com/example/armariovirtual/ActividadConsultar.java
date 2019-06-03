package com.example.armariovirtual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ActividadConsultar extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView listaPrendas;
    private ImageView imagenPrenda;
    private ArrayList<Prenda> prendas;
    private Toolbar appToolbar;
    // FireBase
    private FirebaseUser user;
    private ServidorPHP objetoServidor;
    private final int TIEMPO_ESPERA_INICIAL = 800;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_consultar);
        contexto = this;
        // Toobar
        appToolbar = findViewById(R.id.appToolbar);
        listaPrendas = findViewById(R.id.recyclerConsultar);
        imagenPrenda = findViewById(R.id.imagenPrenda);
        appToolbar.setTitle(R.string.texto3MainActivityDrawer);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listaPrendas.setOnClickListener(this);

        // Para que el usuario 'no tenga que esperar a que se rellene el array con la peticion', le mostramos una animación:
        abrirSplashEsperarDatos();

        prendas = new ArrayList<>();
        objetoServidor = new ServidorPHP();
        user = FirebaseAuth.getInstance().getCurrentUser();
        listaPrendas.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space_recycler_consultar, true, true));
        // Con esto el tamaño del recyclerwiew no cambiará
        listaPrendas.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listaPrendas.setLayoutManager(llm);

        obtenerDatosServidor();

    }

    private void obtenerDatosServidor() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    prendas = objetoServidor.obtenerPrendas(user.getUid());
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

    }
}
