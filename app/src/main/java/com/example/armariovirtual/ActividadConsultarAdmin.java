package com.example.armariovirtual;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ActividadConsultarAdmin extends AppCompatActivity {

    private RecyclerView listaUsuarios;
    private ArrayList<Usuario> usuarios;
    private Toolbar appToolbar;
    private String uidUsuario;
    private ServidorPHP objetoServidor;
    private Context contexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_consultar_admin);

        inicializarVariables();

        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        obtenerDatosServidor();
    }


    private void inicializarVariables() {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        uidUsuario = user.getUid();
        contexto = this;
        usuarios = new ArrayList<>();
        objetoServidor = new ServidorPHP();
        listaUsuarios = findViewById(R.id.recyclerViewConsultarAdmin);
        // Toobar
        appToolbar = findViewById(R.id.appToolbar);
        appToolbar.setTitle(R.string.texto3MainActivityDrawer);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        // Recycler
        // Con esto el tamaño del recyclerwiew no cambiará
        listaUsuarios.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listaUsuarios.setLayoutManager(llm);
    }

    private void obtenerDatosServidor() {
        try {
            usuarios = objetoServidor.obtenerTodosUsuarios(uidUsuario);
            // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
            AdaptadorUsuarios adaptadorUsuarios = new AdaptadorUsuarios(contexto, usuarios);
            listaUsuarios.setAdapter(adaptadorUsuarios);
            adaptadorUsuarios.refrescar();
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
    }
}
