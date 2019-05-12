package com.example.pruebaproyecto;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Ciclo formativo: Desarrollo de Aplicaciones Multiplataforma
 * Módulo profesional: Programación Multimedia y Dispositivos Móviles
 * Proyecto final 2º DAM
 * Alumno: Ismael Casado González
 * Curso académico: 2018-2019
 * Instituto Tecnológico Poniente
 */
public class MainActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String nombreUsuarioString;
    private TextView nombreUser, prendasAdd, accesoriossAdd;
    // Para mostrar las prendas y accesorios añadidos
    private int prendas, accesorios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        conectarVariablesConVista();

        inicializarTextos();

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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mi_armario) {
        } else if (id == R.id.add_elemento) {
            Intent intent = new Intent(this, ActividadFotos.class);
            startActivity(intent);
        } else if (id == R.id.eliminar_elemento) {

        } else if (id == R.id.consultar_elemento) {

        } else if (id == R.id.intercambio) {

        } else if (id == R.id.configuracion_usuario) {
            Intent intent = new Intent(this, MiCuenta.class);
            startActivity(intent);
        } else if (id == R.id.desarrollador) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
