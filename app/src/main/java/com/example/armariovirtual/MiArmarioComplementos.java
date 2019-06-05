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
import static com.example.armariovirtual.MiArmario.ARRAY_LIST_COMPLEMENTOS;
import static com.example.armariovirtual.MiArmario.UID_USUARIO;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiArmarioComplementos extends Fragment implements View.OnClickListener {


    private RecyclerView listaComplementos;
    private ArrayList<Prenda> complementos;
    private String uidUsuario;
    private AdaptadorPrendas adaptadorPrendas;
    private LinearLayoutManager llm;

    public MiArmarioComplementos() {
        // Constructor por defecto necesario
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        complementos = new ArrayList();

        if (getArguments() != null) {
            uidUsuario = getArguments().getString(UID_USUARIO);
            complementos = getArguments().getParcelableArrayList(ARRAY_LIST_COMPLEMENTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_complementos, container, false);
        listaComplementos = vista.findViewById(R.id.recyclerViewComplementos);
        listaComplementos.setOnClickListener(this);

        iniciarRecyclerView();

        return vista;
    }

    private void iniciarRecyclerView() {
        // Con esto el tamaño del recyclerwiew no cambiará
        listaComplementos.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        llm = new LinearLayoutManager(getContext());
        listaComplementos.setLayoutManager(llm);

        adaptadorPrendas = new AdaptadorPrendas(getContext(), complementos);
        listaComplementos.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();
    }

    @Override
    public void onClick(View v) {

    }
}
