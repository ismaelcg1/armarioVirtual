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

import static com.example.armariovirtual.MiArmario.ARRAY_LIST_ROPA_INFERIOR;
import static com.example.armariovirtual.MiArmario.UID_USUARIO;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiArmarioRopaInferior extends Fragment implements View.OnClickListener {

    private RecyclerView listaRopaInferior;
    private ArrayList<Prenda> ropaInferior;
    private String uidUsuario;
    private AdaptadorPrendas adaptadorPrendas ;
    private LinearLayoutManager llm;

    public MiArmarioRopaInferior() {
        // Constructor por defecto necesario
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ropaInferior = new ArrayList();

        if (getArguments() != null) {
            uidUsuario = getArguments().getString(UID_USUARIO);
            ropaInferior = getArguments().getParcelableArrayList(ARRAY_LIST_ROPA_INFERIOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_ropa_inferior, container, false);
        listaRopaInferior = vista.findViewById(R.id.recyclerViewRopaInferior);
        listaRopaInferior.setOnClickListener(this);

        iniciarRecyclerView();

        return vista;
    }

    private void iniciarRecyclerView() {
        // Con esto el tamaño del recyclerwiew no cambiará
        listaRopaInferior.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        llm = new LinearLayoutManager(getContext());
        listaRopaInferior.setLayoutManager(llm);

        adaptadorPrendas = new AdaptadorPrendas(getContext(), ropaInferior);
        listaRopaInferior.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();
    }


    @Override
    public void onClick(View v) {

    }
}
