package com.example.armariovirtual;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityDrawerAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Intent intent;
    private Boolean es_administrador;
    private final String ADMINISTRADOR = "administrador";
    private int cantidad_usuarios;
    private int cantidad_prendas;
    private ServidorPHP objetoServidor;
    private FirebaseUser user;
    private TextView prendasTotales, usuariosTotales;
    private Button bAnadir, bEliminar, bConsultar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        inicializarVariables();
        obtenerDatosServidor();
        inicializarTextosIniciales();
        setSupportActionBar(toolbar);
        es_administrador = true;

        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void inicializarVariables() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        cantidad_usuarios = 0;
        cantidad_prendas = 0;
        usuariosTotales = findViewById(R.id.tvUsuariosTotales);
        prendasTotales = findViewById(R.id.tvPrendasTotales);
        bAnadir = findViewById(R.id.bAnadirAdmin);
        bEliminar = findViewById(R.id.bEliminarAdmin);
        bConsultar = findViewById(R.id.bConsultarAdmin);
        bAnadir.setOnClickListener(this);
        bEliminar.setOnClickListener(this);
        bConsultar.setOnClickListener(this);
    }

    private void inicializarTextosIniciales() {
        usuariosTotales.setText( getResources().getString(R.string.usuariosRegistradosMainActivityDrawerAdmin, cantidad_usuarios) );
        prendasTotales.setText( getResources().getString(R.string.totalPrendasMainActivityDrawerAdmin, cantidad_prendas) );

    }

    private void obtenerDatosServidor() {
        objetoServidor = new ServidorPHP();
        cantidad_usuarios = objetoServidor.obtenerCantidadUsuarios(user.getUid());
        cantidad_prendas = objetoServidor.obtenerCantidadPrendasTotales(user.getUid());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_elemento_admin) {
            intent = new Intent(this, RegistroInicial.class);
            intent.putExtra(ADMINISTRADOR,es_administrador);
            startActivity(intent);
        } else if (id == R.id.eliminar_elemento_admin) {
            intent = new Intent(this, ActividadEliminarAdmin.class);
            startActivity(intent);
        } else if (id == R.id.consultar_elemento_admin) {
            intent = new Intent(this, ActividadConsultarAdmin.class);
            startActivity(intent);
        } else if (id == R.id.configuracion_usuario_admin) {
            intent = new Intent(this, MiCuentaAdmin.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bAnadirAdmin:
                    intent = new Intent(this, RegistroInicial.class);
                    intent.putExtra(ADMINISTRADOR,es_administrador);
                    startActivity(intent);
                break;

            case R.id.bEliminarAdmin:
                intent = new Intent(this, ActividadEliminarAdmin.class);
                startActivity(intent);
                break;

            case R.id.bConsultarAdmin:
                intent = new Intent(this, ActividadConsultarAdmin.class);
                startActivity(intent);
                break;

            default:
                    // Por defecto
                break;
        }
    }
}
