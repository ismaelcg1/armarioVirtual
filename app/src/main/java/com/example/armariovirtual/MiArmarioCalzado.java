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
import static com.example.armariovirtual.MiArmario.ARRAY_LIST_CALZADO;
import static com.example.armariovirtual.MiArmario.UID_USUARIO;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiArmarioCalzado extends Fragment implements View.OnClickListener {

    private RecyclerView listaCalzado;
    private ArrayList<Prenda> calzado;
    private String uidUsuario;
    private AdaptadorPrendas adaptadorPrendas;
    private LinearLayoutManager llm;

    public MiArmarioCalzado() {
        // Constructor por defecto necesario
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calzado = new ArrayList();

        if (getArguments() != null) {
            uidUsuario = getArguments().getString(UID_USUARIO);
            calzado = getArguments().getParcelableArrayList(ARRAY_LIST_CALZADO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_calzado, container, false);
        listaCalzado = vista.findViewById(R.id.recyclerViewCalzado);
        listaCalzado.setOnClickListener(this);

        iniciarRecyclerView();

        // Inflate the layout for this fragment
        return vista;
    }

    private void iniciarRecyclerView() {
        // Con esto el tamaño del recyclerwiew no cambiará
        listaCalzado.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        llm = new LinearLayoutManager(getContext());
        listaCalzado.setLayoutManager(llm);

        adaptadorPrendas = new AdaptadorPrendas(getContext(), calzado);
        listaCalzado.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();
    }


    @Override
    public void onClick(View v) {

    }
}
