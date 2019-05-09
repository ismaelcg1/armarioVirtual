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
    private Button bCerrarSesion, bCambiarPassword, bEliminarArmario, bEliminarCuenta;
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
        bCerrarSesion = findViewById(R.id.btnCerrarSesion);
        appToolbar = findViewById(R.id.appToolbar);
        bCambiarPassword = findViewById(R.id.btnCambiarPassword);
        bEliminarArmario = findViewById(R.id.btnEliminarArmario);
        bEliminarCuenta = findViewById(R.id.btnEliminarCuenta);
    }

    protected void onClickListener() {
        bCerrarSesion.setOnClickListener(this);
        bCambiarPassword.setOnClickListener(this);
        bEliminarArmario.setOnClickListener(this);
        bEliminarCuenta.setOnClickListener(this);
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

    public void crearDialog(String titulo, String mensaje, final int opcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Ponemos el mensaje y el título:
        builder.setMessage(mensaje)
                .setTitle(titulo);


        builder.setPositiveButton(R.string.dialogTextoSiMiCuenta, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (opcion == 1) {
                    signOut(opcion);
                } else if (opcion == 2) {

                } else if (opcion == 3) {

                } else if (opcion == 4){

                } else {

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
                crearDialog(getResources().getString(R.string.cerrarSesionTituloDialogMiCuenta),
                        getResources().getString(R.string.cerrarSesionDialogMiCuenta),opcion);
                break;

            case R.id.btnCambiarPassword:
                opcion = 2;
                crearDialog(getResources().getString(R.string.cambiarPasswordTituloDialogMiCuenta),
                        getResources().getString(R.string.cambiarPasswordDialogMiCuenta),opcion);
                break;

            case R.id.btnEliminarArmario:
                opcion = 3;
                crearDialog(getResources().getString(R.string.eliminarArmarioTituloDialogMiCuenta),
                        getResources().getString(R.string.eliminarArmarioDialogMiCuenta),opcion);
                break;

            case R.id.btnEliminarCuenta:
                opcion = 4;
                crearDialog(getResources().getString(R.string.eliminarCuentaTituloDialogMiCuenta),
                        getResources().getString(R.string.eliminarCuentaDialogMiCuenta),opcion);
                break;
        }

    }
}
