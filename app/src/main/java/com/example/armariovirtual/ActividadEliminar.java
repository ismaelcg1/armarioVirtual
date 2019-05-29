package com.example.armariovirtual;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ActividadEliminar extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView listaPrendas;
    private ImageView imagenPrenda;
    private ArrayList<Prenda> prendas;
    private Toolbar appToolbar;

    private AdaptadorPrendas adaptadorPrendas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_eliminar);
        // Toobar
        appToolbar = findViewById(R.id.appToolbar);
        appToolbar.setTitle(R.string.texto4MainActivityDrawer);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaPrendas = findViewById(R.id.recyclerEliminar);
        imagenPrenda = findViewById(R.id.imagenPrenda);

        listaPrendas.setOnClickListener(this);

        // ----------------------------------------
        // TODO arraylist de prueba
        prendas = new ArrayList<>();
        prendas.add(new Prenda( 0,"L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 1,"S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 2,"42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 3,"L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 4,"S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 5,"42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 6, "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 7,"S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 8,"42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 9, "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        prendas.add(new Prenda( 10, "S", "Deporte",
                "Rojo", "Primavera", "Parte superior",
                "Camiseta manga corta", R.drawable.sueter_r100, 1));
        prendas.add(new Prenda( 11, "42", "Diario",
                "Azul", "Otoño", "Calzado",
                "Zapatillas", R.drawable.zapatillas_r100, 1));
        prendas.add(new Prenda( 12, "L", "Diario",
                "Azul", "Otoño", "Parte inferior",
                "Pantalones", R.drawable.pantalones_r100, 1));
        // ----------------------------------------


        //listaPrendas.addItemDecoration(new SpaceItemDecoration(this, R.dimen.list_space_recycler_consultar, true, true));
        // Con esto el tamaño del recyclerwiew no cambiará
        listaPrendas.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listaPrendas.setLayoutManager(llm);

        // Creamos un adaptadorF para incluirlo en la listaOptimizada -> RecyclerView
        adaptadorPrendas = new AdaptadorPrendas(this, prendas);
        listaPrendas.setAdapter(adaptadorPrendas);
        adaptadorPrendas.refrescar();


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





    @Override
    public void onClick(View v) {

    }
}
