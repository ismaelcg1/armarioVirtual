package com.example.pruebaproyecto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Ciclo formativo: Desarrollo de Aplicaciones Multiplataforma
 * Proyecto final DAM
 * Alumno: Ismael Casado González
 * Curso académico: 2018-2019
 * Instituto Tecnológico Poniente
 */
public class MainActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private String nombreUsuarioString;
    private TextView nombreUser, prendasAdd, accesoriossAdd;
    // Para mostrar las prendas y accesorios añadidos
    private int prendas, accesorios;
    // Creamos el intent de la nueva actividad
    private Intent intent;

    private Button btnVerArmario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        conectarVariablesConVista();
        inicializarTextos();
        btnVerArmario.setOnClickListener(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO para obtener el sexo de la persona y poner una imagen u otra
        // LoginActivity.miUsuario.getSexo() == Sexo.Femenino

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @SuppressLint("StringFormatMatches")
    private void inicializarTextos () {
        prendas = 0;
        accesorios = 0;
        nombreUser.setText( getResources().getString(R.string.saludoMainActivityDrawer, nombreUsuarioString) );
        prendasAdd.setText( getResources().getString(R.string.prendasAnadidasMainActivityDrawer, prendas ) );
        accesoriossAdd.setText( getResources().getString(R.string.accesoriosAnadidosMainActivityDrawer, accesorios ) );

    }

    private void conectarVariablesConVista () {
        nombreUser = findViewById(R.id.nombreUsuario);
        prendasAdd = findViewById(R.id.prendasAdd);
        accesoriossAdd = findViewById(R.id.accesoriosAdd);
        btnVerArmario = findViewById(R.id.bVerArmario);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mi_armario) {
            intent = new Intent(this, MiArmario.class);
            startActivity(intent);
        } else if (id == R.id.add_elemento) {
            intent = new Intent(this, ActividadAddPrenda.class);
            startActivity(intent);
        } else if (id == R.id.eliminar_elemento) {

        } else if (id == R.id.consultar_elemento) {

        } else if (id == R.id.intercambio) {

        } else if (id == R.id.configuracion_usuario) {
            intent = new Intent(this, MiCuenta.class);
            startActivity(intent);
        } else if (id == R.id.informacion) {
            intent = new Intent(this, Informacion.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bVerArmario) {
            intent = new Intent(this, MiArmario.class);
            startActivity(intent);
        }
    }
}
