package com.example.armariovirtual;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class Informacion extends AppCompatActivity {

    private Toolbar appToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion);

        inicializarToolbar();
        // Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void inicializarToolbar() {
        appToolbar = findViewById(R.id.appToolbar);
        appToolbar.setTitle(R.string.nombreInicialActividadInformacion);
        // Asigno la flecha atras
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
    }

}
