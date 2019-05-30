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
public class MiArmarioRopaInferior extends Fragment implements View.OnClickListener {

    private RecyclerView listaRopaInferior;
    private ImageView imagenRopaInferior;
    private ArrayList<Prenda> ropaInferior;

    public MiArmarioRopaInferior() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ropa_inferior, container, false);

        listaRopaInferior = vista.findViewById(R.id.recyclerViewRopaInferior);
        imagenRopaInferior = vista.findViewById(R.id.imagenPrenda);
        ropaInferior = new ArrayList();
        listaRopaInferior.setOnClickListener(this);

        // SpaceItemDecoration nos dará error, hay que crear una clase: Alt+Intro
        //listaCalzado.addItemDecoration(new SpaceItemDecoration( this, R.dimen.list_space_recycler_consultar, true, true));
        // Con esto el tamaño del recyclerwiew no cambiará
        listaRopaInferior.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listaRopaInferior.setLayoutManager(llm);


        // ----------------------------------------
        // TODO arraylist de prueba
        // Aquí introducimos los datos en el array, su foto y su texto
        ropaInferior.add(new Prenda( 0,"L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));

        ropaInferior.add(new Prenda( 1,"M", "Deportivo",
                "Azul", "Primavera", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));

        ropaInferior.add(new Prenda( 2,"S", "Formal",
                "Azul", "Verano", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        // ----------------------------------------

        // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
        AdaptadorPrendas adaptadorPrendas = new AdaptadorPrendas(getContext(), ropaInferior);
        listaRopaInferior.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();


        return vista;
    }

    @Override
    public void onClick(View v) {



    }
}
