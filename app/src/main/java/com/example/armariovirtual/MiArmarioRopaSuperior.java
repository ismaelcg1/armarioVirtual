package com.example.armariovirtual;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiArmarioRopaSuperior extends Fragment implements View.OnClickListener {

    private RecyclerView listaRopaSuperior;
    private ImageView imagenRopaSuperior;
    private ArrayList<Prenda> ropaSuperior;

    public MiArmarioRopaSuperior() {
        // Constructor por defecto necesario
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ropa_superior, container, false);

        listaRopaSuperior = vista.findViewById(R.id.recyclerViewRopaSuperior);
        imagenRopaSuperior = vista.findViewById(R.id.imagenPrenda);
        ropaSuperior = new ArrayList();
        listaRopaSuperior.setOnClickListener(this);

        //listaCalzado.addItemDecoration(new SpaceItemDecoration( this, R.dimen.list_space_recycler_consultar, true, true));
        // Con esto el tamaño del recyclerwiew no cambiará
        listaRopaSuperior.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listaRopaSuperior.setLayoutManager(llm);

        // ----------------------------------------
        // TODO arraylist de prueba
        // Aquí introducimos los datos en el array, su foto y su texto
        ropaSuperior.add(new Prenda( 0,"S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));

        ropaSuperior.add(new Prenda( 1,"M", "Deporte",
                "Verde", "Verano", "Parte superior",
                "Camiseta sin mangas", R.drawable.sueter_r100, 1));

        ropaSuperior.add(new Prenda( 2,"L", "Diario",
                "Azul", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));

        ropaSuperior.add(new Prenda( 3,"XXL", "Diario",
                "Amarilla", "Otoño", "Parte superior",
                "Camiseta manga larga", R.drawable.sueter_r100, 1));

        // ----------------------------------------


        // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
        AdaptadorPrendas adaptadorPrendas = new AdaptadorPrendas(getContext(), ropaSuperior);
        listaRopaSuperior.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();


        return vista;
    }

    @Override
    public void onClick(View v) {

    }
}
