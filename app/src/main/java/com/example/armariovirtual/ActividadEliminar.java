package com.example.armariovirtual;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static com.example.armariovirtual.MainActivityDrawer.NUMERO_PRENDAS;
import static com.example.armariovirtual.MainActivityDrawer.UID_USUARIO_KEY;

public class ActividadEliminar extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView listaPrendas;
    private ArrayList<Prenda> prendas;
    private Toolbar appToolbar;

    private AdaptadorPrendas adaptadorPrendas;
    private final int TIEMPO_ESPERA_INICIAL = 50;
    private String uidUsuario;
    private int cantidadPrendas;
    private ServidorPHP objetoServidor;
    private LinearLayout layoutSinElementos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_eliminar);

        inicializarVariables();
        inicializarToolbar();

        listaPrendas.setOnClickListener(this);

        if (cantidadPrendas < 1) {
            layoutSinElementos.setVisibility(View.VISIBLE);
        } else {
            layoutSinElementos.setVisibility(View.GONE);
            obtenerDatosServidor();

            deslizarBorrar();

        }

    }

    private void deslizarBorrar() {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (direction == ItemTouchHelper.LEFT | direction == ItemTouchHelper.RIGHT) { //if swipe left or right

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActividadEliminar.this); //alert for confirm to delete
                    builder.setMessage(getResources().getString(R.string.mensajeBuilderActividadEliminar)); //set message
                    builder.setPositiveButton(getResources().getString(R.string.builderBorrarActividadEliminar), new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adaptadorPrendas.notifyItemRemoved(position); //item removed from recylcerview
                            try {
                                objetoServidor.eliminarPrenda(uidUsuario, prendas.get(position).getId());
                            } catch (ServidorPHPException e) {
                                e.printStackTrace();
                            }
                            prendas.remove(position);
                            return;
                        }
                    }).setNegativeButton(getResources().getString(R.string.builderCancelarActividadEliminar), new DialogInterface.OnClickListener() { //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adaptadorPrendas.notifyItemRemoved(position + 1); //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                            adaptadorPrendas.notifyItemRangeChanged(position, adaptadorPrendas.getItemCount()); //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            return;
                        }
                    }).show(); //show alert dialog
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(listaPrendas); //set swipe to recylcerview
    }

    private void inicializarVariables() {
        obtenerDatosUsuario();
        layoutSinElementos = findViewById(R.id.layoutSinElementosEliminar);
        objetoServidor = new ServidorPHP();
        appToolbar = findViewById(R.id.appToolbar);
        listaPrendas = findViewById(R.id.recyclerEliminar);
        prendas = new ArrayList<>();
        // Con esto el tamaño del recyclerwiew no cambiará
        listaPrendas.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listaPrendas.setLayoutManager(llm);
    }

    private void obtenerDatosUsuario() {
        Intent intentActividadActual = getIntent();
        Bundle b = intentActividadActual.getExtras();
        if(b!=null) {
            uidUsuario = (String) b.get(UID_USUARIO_KEY);
            cantidadPrendas = b.getInt(NUMERO_PRENDAS);
        }
    }

    private void inicializarToolbar() {
        appToolbar.setTitle(R.string.texto4MainActivityDrawer);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void obtenerDatosServidor() {
        Handler handler = new Handler();
        final String filtrado = "sin_filtros";
        final String valorFiltrado = "";

        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    prendas = objetoServidor.obtenerPrendas(uidUsuario, filtrado, valorFiltrado);
                    // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
                    adaptadorPrendas = new AdaptadorPrendas(ActividadEliminar.this, prendas);
                    listaPrendas.setAdapter(adaptadorPrendas);
                    adaptadorPrendas.refrescar();
                } catch (ServidorPHPException e) {
                    e.printStackTrace();
                }
            }
        }, TIEMPO_ESPERA_INICIAL);
    }


    @Override
    public void onClick(View v) {

    }
}
