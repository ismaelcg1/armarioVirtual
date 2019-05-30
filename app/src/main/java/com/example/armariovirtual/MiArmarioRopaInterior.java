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
public class MiArmarioRopaInterior extends Fragment implements View.OnClickListener {


    private RecyclerView listaRopaInterior;
    private ImageView imagenRopaInterior;
    private ArrayList<Prenda> ropaInterior;

    public MiArmarioRopaInterior() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ropa_interior, container, false);

        listaRopaInterior = vista.findViewById(R.id.recyclerViewRopaInterior);
        imagenRopaInterior = vista.findViewById(R.id.imagenPrenda);
        ropaInterior = new ArrayList();
        listaRopaInterior.setOnClickListener(this);


        // SpaceItemDecoration nos dará error, hay que crear una clase: Alt+Intro
        //listaCalzado.addItemDecoration(new SpaceItemDecoration( this, R.dimen.list_space_recycler_consultar, true, true));
        // Con esto el tamaño del recyclerwiew no cambiará
        listaRopaInterior.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listaRopaInterior.setLayoutManager(llm);

        // ----------------------------------------
        // TODO arraylist de prueba
        // Aquí introducimos los datos en el array, su foto y su texto
        ropaInterior.add(new Prenda( 0,"M", "Diario",
                "Rojo", "Invierno", "Ropa interior",
                "Calzoncillos", R.drawable.calzoncillos_r100, 1));

        ropaInterior.add(new Prenda( 0,"L", "Deportivo",
                "Verdes", "Primavera", "Ropa interior",
                "Calzoncillos", R.drawable.calzoncillos_r100, 1));

        ropaInterior.add(new Prenda( 0,"M", "Diario",
                "Azul", "Otoño", "Ropa interior",
                "Calzoncillos", R.drawable.calzoncillos_r100, 1));

        ropaInterior.add(new Prenda( 0,"XL", "Diario",
                "Rojo", "Invierno", "Ropa interior",
                "Calzoncillos", R.drawable.calzoncillos_r100, 1));
        // ----------------------------------------


        // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
        AdaptadorPrendas adaptadorPrendas = new AdaptadorPrendas(getContext(), ropaInterior);
        listaRopaInterior.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();

        return vista;
    }

    @Override
    public void onClick(View v) {



    }
}
