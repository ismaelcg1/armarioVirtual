package com.example.armariovirtual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FragmentMisPrendas extends Fragment {

    private RecyclerView recyclerViewPrendas;
    private static ArrayList<Prenda> misPrendasIntercambio;
    private ServidorPHP objetoServidor;
    private AdaptadorPrendas adaptadorPrendas;
    private LinearLayoutManager llm;
    private FirebaseUser user;
    private final String MIS_PRENDAS_VALOR = "mis_prendas";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objetoServidor = new ServidorPHP();
        user = FirebaseAuth.getInstance().getCurrentUser();
        misPrendasIntercambio = new ArrayList<>();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mis_prendas, container, false);

        recyclerViewPrendas = root.findViewById(R.id.recyclerViewMisPrendas);

        // Con esto el tamaño del recyclerwiew no cambiará
        recyclerViewPrendas.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        llm = new LinearLayoutManager(getContext());
        recyclerViewPrendas.setLayoutManager(llm);

        obtenerMisPrendas();


        return root;
    }

    private void iniciarRecyclerView() {
        // Con esto el tamaño del recyclerwiew no cambiará
        recyclerViewPrendas.setHasFixedSize(true);
        // Creo un layoutmanager para el recyclerview
        llm = new LinearLayoutManager(getContext());
        recyclerViewPrendas.setLayoutManager(llm);

        adaptadorPrendas = new AdaptadorPrendas(getContext(), misPrendasIntercambio);
        recyclerViewPrendas.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();
    }

    private void obtenerMisPrendas () {
        try {
            misPrendasIntercambio = objetoServidor.obtenerPrendasIntercambio(user.getUid(), MIS_PRENDAS_VALOR);
            adaptadorPrendas = new AdaptadorPrendas(getContext(), misPrendasIntercambio);
            recyclerViewPrendas.setAdapter(adaptadorPrendas);
            adaptadorPrendas.refrescar();
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
    }

}
