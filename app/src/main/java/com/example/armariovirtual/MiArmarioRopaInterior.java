package com.example.armariovirtual;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import static com.example.armariovirtual.MiArmario.ARRAY_LIST_ROPA_INTERIOR;
import static com.example.armariovirtual.MiArmario.UID_USUARIO;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiArmarioRopaInterior extends Fragment implements View.OnClickListener {

    private RecyclerView listaRopaInterior;
    private ArrayList<Prenda> ropaInterior;
    private String uidUsuario;
    private AdaptadorPrendas adaptadorPrendas ;
    private LinearLayoutManager llm;

    public MiArmarioRopaInterior() {
        // Constructor por defecto necesario
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ropaInterior = new ArrayList();

        if (getArguments() != null) {
            uidUsuario = getArguments().getString(UID_USUARIO);
            ropaInterior = getArguments().getParcelableArrayList(ARRAY_LIST_ROPA_INTERIOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_ropa_interior, container, false);

        listaRopaInterior = vista.findViewById(R.id.recyclerViewRopaInterior);
        listaRopaInterior.setOnClickListener(this);

        iniciarRecyclerView();


        return vista;
    }

    private void iniciarRecyclerView() {
        // Con esto el tamaño del recyclerwiew no cambiará
        listaRopaInterior.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        llm = new LinearLayoutManager(getContext());
        listaRopaInterior.setLayoutManager(llm);

        adaptadorPrendas = new AdaptadorPrendas(getContext(), ropaInterior);
        listaRopaInterior.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();
    }

    @Override
    public void onClick(View v) {

    }
}
