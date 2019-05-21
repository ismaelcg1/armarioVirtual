package com.example.armariovirtual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ActividadConsultar extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView listaPrendas;
    private ImageView imagenPrenda;
    private ArrayList<Prenda> prendas;
    private Toolbar appToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_consultar);
        // Toobar
        appToolbar = findViewById(R.id.appToolbar);
        appToolbar.setTitle(R.string.texto3MainActivityDrawer);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaPrendas = findViewById(R.id.recyclerConsultar);
        imagenPrenda = findViewById(R.id.imagenPrenda);

        listaPrendas.setOnClickListener(this);

        // ----------------------------------------
        // TODO arraylist de prueba
        ArrayList<Prenda> prendas = new ArrayList<>();
        // Aquí introducimos los datos en el array, su foto y su texto
        prendas.add(new Prenda( "Pantalon", "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100));
        prendas.add(new Prenda( "Camiseta roja", "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100));
        prendas.add(new Prenda( "Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));
        prendas.add(new Prenda( "Pantalon", "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100));
        prendas.add(new Prenda( "Camiseta roja", "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100));
        prendas.add(new Prenda( "Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));
        prendas.add(new Prenda( "Pantalon", "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100));
        prendas.add(new Prenda( "Camiseta roja", "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100));
        prendas.add(new Prenda( "Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));
        prendas.add(new Prenda( "Pantalon", "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100));
        prendas.add(new Prenda( "Camiseta roja", "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100));
        prendas.add(new Prenda( "Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));
        prendas.add(new Prenda( "Pantalon", "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100));
        prendas.add(new Prenda( "Camiseta roja", "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100));
        prendas.add(new Prenda( "Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));
        prendas.add(new Prenda( "Pantalon", "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100));
        prendas.add(new Prenda( "Camiseta roja", "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100));
        prendas.add(new Prenda( "Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));
        // ----------------------------------------


        // Hay que crear en la carpeta values un fichero dimens.xml y crear ahí list_space
        // SpaceItemDecoration nos dará error, hay que crear una clase: Alt+Intro
        listaPrendas.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space_recycler_consultar, true, true));
        // Con esto el tamaño del recyclerwiew no cambiará
        listaPrendas.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listaPrendas.setLayoutManager(llm);

        // Creamos un adaptadorF para incluirlo en la listaOptimizada -> RecyclerView
        AdaptadorPrendas adaptadorPrendas = new AdaptadorPrendas(this, prendas);
        listaPrendas.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();
    }


    @Override
    public void onClick(View v) {

    }
}
