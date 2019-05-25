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
public class MiArmarioCalzado extends Fragment implements View.OnClickListener {

    private RecyclerView listaCalzado;
    private ImageView imagenCalzado;
    private ArrayList<Prenda> calzado;

    public MiArmarioCalzado() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Toast.makeText(getActivity(), "Has pulsado calzado!", Toast.LENGTH_SHORT).show();
        View vista = inflater.inflate(R.layout.fragment_calzado, container, false);

        listaCalzado = vista.findViewById(R.id.recyclerViewCalzado);
        imagenCalzado = vista.findViewById(R.id.imagenPrenda);
        calzado = new ArrayList();
        listaCalzado.setOnClickListener(this);


        // SpaceItemDecoration nos dará error, hay que crear una clase: Alt+Intro
        //listaCalzado.addItemDecoration(new SpaceItemDecoration( this, R.dimen.list_space_recycler_consultar, true, true));
        // Con esto el tamaño del recyclerwiew no cambiará
        listaCalzado.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listaCalzado.setLayoutManager(llm);


        // ----------------------------------------
        // TODO arraylist de prueba
        // Aquí introducimos los datos en el array, su foto y su texto
        calzado.add(new Prenda( 0,"Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));

        calzado.add(new Prenda( 1,"Zapatillas puma", "44", "Diario",
                "Azul", "Verano", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));

        calzado.add(new Prenda( 2,"Zapatillas nike", "38", "Deportivo",
                "Azul", "Primavera", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));

        calzado.add(new Prenda( 3,"Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));

        calzado.add(new Prenda( 4,"Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));

        calzado.add(new Prenda( 5,"Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));

        calzado.add(new Prenda( 6,"Zapatillas nike", "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100));
        // ----------------------------------------


        // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
        AdaptadorPrendas adaptadorPrendas = new AdaptadorPrendas(getContext(), calzado);
        listaCalzado.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();


        // Inflate the layout for this fragment
        return vista;
    }



    @Override
    public void onClick(View v) {

    }
}
