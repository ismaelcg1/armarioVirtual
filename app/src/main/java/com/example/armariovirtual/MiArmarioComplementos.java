package com.example.armariovirtual;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiArmarioComplementos extends Fragment implements View.OnClickListener {


    private RecyclerView listaComplementos;
    private ImageView imagenComplemento;
    private ArrayList<Prenda> complementos;

    public MiArmarioComplementos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_complementos, container, false);

        listaComplementos = vista.findViewById(R.id.recyclerViewComplementos);
        imagenComplemento = vista.findViewById(R.id.imagenPrenda);
        complementos = new ArrayList();
        listaComplementos.setOnClickListener(this);

        // SpaceItemDecoration nos dará error, hay que crear una clase: Alt+Intro
        //listaCalzado.addItemDecoration(new SpaceItemDecoration( this, R.dimen.list_space_recycler_consultar, true, true));
        // Con esto el tamaño del recyclerwiew no cambiará
        listaComplementos.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listaComplementos.setLayoutManager(llm);

        // ----------------------------------------
        // TODO arraylist de prueba
        // Aquí introducimos los datos en el array, su foto y su texto
        complementos.add(new Prenda( 0,"M", "Diario",
                "Azul", "Otoño", "Accesorios",
                "Reloj", R.drawable.reloj_r100, 1));

        complementos.add(new Prenda( 1,"L", "Deportivo",
                "Azul", "Otoño", "Accesorios",
                "Pulsera", R.drawable.reloj_r100, 1));

        complementos.add(new Prenda( 2,"S", "Diario",
                "Azul", "Otoño", "Accesorios",
                "Reloj", R.drawable.reloj_r100, 1));

        // ----------------------------------------

        AdaptadorPrendas adaptadorPrendas = new AdaptadorPrendas(getContext(), complementos);
        listaComplementos.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();

        return vista;
    }

    @Override
    public void onClick(View v) {

    }
}
