package com.example.armariovirtual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ActividadInfoPrendaCompleta extends AppCompatActivity implements View.OnClickListener{

    private Toolbar appToolbar;
    private TextView tvNombre, tvTalla, tvEstilo, tvColor, tvEpoca, tvCategoria, tvSubcategoria, tvEstado;
    private ImageView imagenPrendaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_toda_informacion_prenda);

        inicializarVariablesConVista();

        // Cogemos los datos pasados al intent
        Intent intentActividadActual = getIntent();
        Bundle b = intentActividadActual.getExtras();

        if(b!=null)
        {
            String nombrePrendaSeleccionada =(String) b.get("nombrePrenda");
            String tallaPrendaSeleccionada =(String) b.get("tallaPrenda");
            String estiloPrendaSeleccionada =(String) b.get("estiloPrenda");
            String colorPrendaSeleccionada =(String) b.get("colorPrenda");
            String epocaPrendaSeleccionada =(String) b.get("epocaPrenda");
            String categoriaPrendaSeleccionada =(String) b.get("categoriaPrenda");
            String subcategoriaPrendaSeleccionada =(String) b.get("subcategoriaPrenda");
            boolean estadoPrendaSeleccionada =(boolean) b.get("estadoPrenda");
            int imagenPrendaSeleccionada =(int) b.get("imagenPrenda");

            tvNombre.setText(nombrePrendaSeleccionada);
            tvTalla.setText(tallaPrendaSeleccionada);
            tvEstilo.setText(estiloPrendaSeleccionada);
            tvColor.setText(colorPrendaSeleccionada);
            tvEpoca.setText(epocaPrendaSeleccionada);
            tvCategoria.setText(categoriaPrendaSeleccionada);
            tvSubcategoria.setText(subcategoriaPrendaSeleccionada);

            //imagenPrendaActual.set

            if (estadoPrendaSeleccionada) {
                tvEstado.setText("Limpio");
            } else {
                tvEstado.setText("Sucio");
            }
        }


        // Toobar
        appToolbar.setTitle(R.string.nombreActividadInfoPrendaCompleta);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void inicializarVariablesConVista() {
        appToolbar = findViewById(R.id.appToolbar);
        tvNombre = findViewById(R.id.nombrePrendaVisualizada);
        tvTalla = findViewById(R.id.tallaPrendaVisualizada);
        tvEstilo = findViewById(R.id.estiloPrendaVisualizada);
        tvColor = findViewById(R.id.colorPrendaVisualizada);
        tvEpoca = findViewById(R.id.epocaPrendaVisualizada);
        tvCategoria = findViewById(R.id.categoriaPrendaVisualizada);
        tvSubcategoria = findViewById(R.id.subcategoriaPrendaVisualizada);
        tvEstado = findViewById(R.id.estadoPrendaVisualizada);
    }


    @Override
    public void onClick(View v) {

    }
}
