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
import static com.example.armariovirtual.MiArmario.ARRAY_LIST_ROPA_SUPERIOR;
import static com.example.armariovirtual.MiArmario.UID_USUARIO;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiArmarioRopaSuperior extends Fragment implements View.OnClickListener {

    private RecyclerView listaRopaSuperior;
    private ArrayList<Prenda> ropaSuperior;
    private String uidUsuario;
    private AdaptadorPrendas adaptadorPrendas;
    private LinearLayoutManager llm;


    public MiArmarioRopaSuperior() {
        // Constructor por defecto necesario
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ropaSuperior = new ArrayList();

        if (getArguments() != null) {
            uidUsuario = getArguments().getString(UID_USUARIO);
            ropaSuperior = getArguments().getParcelableArrayList(ARRAY_LIST_ROPA_SUPERIOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_ropa_superior, container, false);
        listaRopaSuperior = vista.findViewById(R.id.recyclerViewRopaSuperior);
        listaRopaSuperior.setOnClickListener(this);

        iniciarRecyclerView();

        return vista;
    }

    private void iniciarRecyclerView() {
        // Con esto el tamaño del recyclerwiew no cambiará
        listaRopaSuperior.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        llm = new LinearLayoutManager(getContext());
        listaRopaSuperior.setLayoutManager(llm);

        adaptadorPrendas = new AdaptadorPrendas(getContext(), ropaSuperior);
        listaRopaSuperior.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();
    }


    @Override
    public void onClick(View v) {

    }
}
