package com.example.armariovirtual;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ActividadEliminar extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView listaPrendas;
    private ArrayList<Prenda> prendas;
    private Toolbar appToolbar;

    private AdaptadorPrendas adaptadorPrendas;
    private final int TIEMPO_ESPERA_INICIAL = 50;
    private FirebaseUser user;
    private ServidorPHP objetoServidor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_eliminar);

        inicializarVariables();
        inicializarToolbar();

        listaPrendas.setOnClickListener(this);

        obtenerDatosServidor();

        // ------------------------------
        // Para poder deslizar RecyclerView

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
                            // TODO En este caso borrar el elemento del Arraylist y de la BBDD
                            //sqldatabase.execSQL("delete from " + TABLE_NAME + " where _id='" + (position + 1) + "'"); //query for delete
                            prendas.remove(position); //then remove item
                            // TODO Eliminar al elemento pulsado aquí, pasar el id de la prenda y el del usuario --> en prendas_usuarios

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

        // ------------------------------

    }

    private void inicializarVariables() {
        user = FirebaseAuth.getInstance().getCurrentUser();
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

        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    prendas = objetoServidor.obtenerPrendas(user.getUid(), null, null);
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
