package com.example.armariovirtual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.armariovirtual.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import static com.example.armariovirtual.MainActivityDrawer.UID_USUARIO_KEY;

public class MainIntercambio extends AppCompatActivity {

    private Toolbar appToolbar;
    private String uidUsuario;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_intercambio_main);

        obtenerUidUsuario();
        inicializarVariables();
        inicializarToolbar();
        // Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inicializarVariables() {
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void obtenerUidUsuario() {
        Intent intentActividadActual = getIntent();
        Bundle b = intentActividadActual.getExtras();
        if(b!=null) {
            uidUsuario = (String) b.get(UID_USUARIO_KEY);
        }
    }


    private void inicializarToolbar() {
        appToolbar = findViewById(R.id.appToolbarIntercambio);
        // Asigno la flecha atras
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
    }

}
