package com.example.armariovirtual;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
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
        // Aquí introducimos los datos en el array, su foto y su texto
        prendas = new ArrayList<>();
        prendas.add(new Prenda( 0, "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 1, "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 2, "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 3,"L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 4,"S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 5,"42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 6,"L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 7,"S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 8,"42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 9,"L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 10,"S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 11,"42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 12,"L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 13,"S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 14,"42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 15,"L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 16, "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 17, "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
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
