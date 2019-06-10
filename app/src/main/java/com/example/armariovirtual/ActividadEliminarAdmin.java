package com.example.armariovirtual;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ActividadEliminarAdmin extends AppCompatActivity {

    private RecyclerView listaUsuarios;
    private ArrayList<Usuario> usuarios;
    private Toolbar appToolbar;
    private AdaptadorUsuarios adaptadorUsuarios;
    private String uidUsuario;
    private ServidorPHP objetoServidor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_eliminar_admin);

        inicializarVariables();
        inicializarToolbar();
        obtenerDatosServidor();

        deslizarBorrar();
    }

    private void inicializarVariables() {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        objetoServidor = new ServidorPHP();
        appToolbar = findViewById(R.id.appToolbar);
        listaUsuarios = findViewById(R.id.recyclerViewEliminarAdmin);
        usuarios = new ArrayList<>();
        uidUsuario = user.getUid();
        // Con esto el tamaño del recyclerwiew no cambiará
        listaUsuarios.setHasFixedSize(true);

        // Creo un layoutmanager para el recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listaUsuarios.setLayoutManager(llm);
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

        try {
            usuarios = objetoServidor.obtenerTodosUsuarios(uidUsuario);
            // Creamos un adaptador para incluirlo en la listaOptimizada -> RecyclerView
            adaptadorUsuarios = new AdaptadorUsuarios(ActividadEliminarAdmin.this, usuarios);
            listaUsuarios.setAdapter(adaptadorUsuarios);
            adaptadorUsuarios.refrescar();
        } catch (ServidorPHPException e) {
            e.printStackTrace();
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActividadEliminarAdmin.this); //alert for confirm to delete
                    builder.setMessage(getResources().getString(R.string.mensajeBuilderActividadEliminar)); //set message
                    builder.setPositiveButton(getResources().getString(R.string.builderBorrarActividadEliminar), new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adaptadorUsuarios.notifyItemRemoved(position); //item removed from recylcerview
                            try {
                                // TODO A falta de eliminar el usuario también de Firebase
                                Boolean usuarioEliminado = objetoServidor.eliminarUsuario(usuarios.get(position).getUid());
                                if (usuarioEliminado) {
                                    Toast.makeText(ActividadEliminarAdmin.this, getResources().getString(R.string.eliminarCuentaCorrectoMiCuenta),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ActividadEliminarAdmin.this, getResources().getString(R.string.eliminarCuentaFalloMiCuenta),
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (ServidorPHPException e) {
                                e.printStackTrace();
                            }
                            usuarios.remove(position);
                            return;
                        }
                    }).setNegativeButton(getResources().getString(R.string.builderCancelarActividadEliminar), new DialogInterface.OnClickListener() { //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adaptadorUsuarios.notifyItemRemoved(position + 1); //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                            adaptadorUsuarios.notifyItemRangeChanged(position, adaptadorUsuarios.getItemCount()); //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            return;
                        }
                    }).show(); //show alert dialog
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(listaUsuarios); //set swipe to recylcerview
    }

/*
    private void eliminarUsuario() {
        final Boolean actividadEliminar = true;
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ActividadEliminarAdmin.this, getResources().getString(R.string.eliminarCuentaCorrectoMiCuenta),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ActividadEliminarAdmin.this, getResources().getString(R.string.eliminarCuentaFalloMiCuenta),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    */

}
