package com.example.pruebaproyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MiCuenta extends AppCompatActivity implements View.OnClickListener {

    private Toolbar appToolbar;
    private Button cerrarSesion;
    // FireBase
    private FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_cuenta);

        conectarVariablesConVista();
        onClickListener();
        inicializarToolbar();
        // Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityDrawer = new Intent(getApplicationContext(), MainActivityDrawer.class);
                startActivity(intentActivityDrawer);
                finish();
            }
        });


    }

    private void inicializarToolbar() {
        appToolbar.setTitle(R.string.nombreActividadMiCuenta);
        // Asigno la flecha atras
        appToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
    }

    private void updateUI(int id) {
        switch (id) {
            case 1:
                // Borramos todas las actividades y abrimos la primera
                Intent intentLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                intentLoginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivityDrawer pantallaDrawer = new MainActivityDrawer();
                pantallaDrawer.finish();
                finish();
                startActivity(intentLoginActivity);


                break;

            default:

                break;
        }
    }


    protected void conectarVariablesConVista() {
        cerrarSesion = findViewById(R.id.btnCerrarSesion);
        appToolbar = findViewById(R.id.appToolbar);
    }

    protected void onClickListener() {
        cerrarSesion.setOnClickListener(this);
    }

    // Salir de la sesión
    private void signOut(int id) {
        // FireBase
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        updateUI(id);
    }
/*
    private void snackBarVerificacion() {
        // Cogemos la vista con getWindow().getDecorView().getRootView()
        Snackbar.make(getWindow().getDecorView().getRootView(), getResources().getString(R.string.snackBarEnviarEmail), Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                .setAction(getResources().getString(R.string.snackBarBotonEnviarEmail), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int id = 1;
                        signOut(id);
                    }
                })
                .show();
    }
*/

    public void crearDialog(String mensaje, String titulo, final int opcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Ponemos el mensaje y el título:
        builder.setMessage(mensaje)
                .setTitle(titulo);


        builder.setPositiveButton(R.string.dialogTextoSiMiCuenta, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (opcion == 1) {
                    signOut(opcion);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.dialogTextoCancelarMiCuenta, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        builder.show();
    }

    @Override
    public void onClick(View v) {
        int opcion;

        switch (v.getId()) {
            case R.id.btnCerrarSesion:
                opcion = 1;
                crearDialog(getResources().getString(R.string.cerrarSesionDialogMiCuenta),
                        getResources().getString(R.string.cerrarSesionTituloDialogMiCuenta),opcion);
                break;
                
        }

    }
}
