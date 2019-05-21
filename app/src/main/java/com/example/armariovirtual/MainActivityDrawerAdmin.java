package com.example.armariovirtual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivityDrawerAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        /* TODO arreglar a la clase que dirije el intent
            poner otras clases nuevas, sino al hacer los findviewbyid por ejemplo petaría


         */
        if (id == R.id.add_elemento_admin) {
            intent = new Intent(this, ActividadAddPrenda.class);
            startActivity(intent);
        } else if (id == R.id.eliminar_elemento_admin) {
            intent = new Intent(this, ActividadAddPrenda.class);
            startActivity(intent);
        } else if (id == R.id.consultar_elemento_admin) {
            intent = new Intent(this, ActividadAddPrenda.class);
            startActivity(intent);
        } else if (id == R.id.configuracion_usuario_admin) {
            intent = new Intent(this, MiCuenta.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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


}