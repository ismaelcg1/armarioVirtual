package com.example.armariovirtual;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MiCuenta extends AppCompatActivity implements View.OnClickListener, DialogPersonalizadoPesoAltura.finalizarDialog {

    private Toolbar appToolbar;
    private Button bCerrarSesion, bCambiarPassword, bEliminarArmario, bEliminarCuenta, bAltura, bPeso, bTalla;
    private TextView emailUser;
    private String email;
    // FireBase
    private FirebaseAuth mAuth = null;
    private FirebaseUser user;
    // Ponemos el contexto para el Dialog
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_cuenta);
        contexto = this;
        conectarVariablesConVista();
        onClickListener();
        ponerEmailUsuario();
        inicializarToolbar();
        // Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void inicializarToolbar() {
        appToolbar.setTitle(R.string.nombreActividadMiCuenta);
        // Asigno la flecha atras
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
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
        bAltura = findViewById(R.id.bAlturaMiCuenta);
        bPeso = findViewById(R.id.bPesoMiCuenta);
        bTalla = findViewById(R.id.bTallaMiCuenta);

        bCerrarSesion = findViewById(R.id.btnCerrarSesion);
        appToolbar = findViewById(R.id.appToolbar);
        bCambiarPassword = findViewById(R.id.btnCambiarPassword);
        bEliminarArmario = findViewById(R.id.btnEliminarArmario);
        bEliminarCuenta = findViewById(R.id.btnEliminarCuenta);
        emailUser = findViewById(R.id.tvEmailUsuario);
    }

    protected void onClickListener() {
        bAltura.setOnClickListener(this);
        bPeso.setOnClickListener(this);
        bTalla.setOnClickListener(this);

        bCerrarSesion.setOnClickListener(this);
        bCambiarPassword.setOnClickListener(this);
        bEliminarArmario.setOnClickListener(this);
        bEliminarCuenta.setOnClickListener(this);
    }

    private void ponerEmailUsuario() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            // Mostramos el email al usuario:
            emailUser.setText(email);
        }
    }

    // Salir de la sesión
    private void signOut(int id) {
        // FireBase
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        updateUI(id);

        // TODO Eliminar intent de MainDrawerActivity
        MainActivityDrawer ob = new MainActivityDrawer();
        ob.finish();
    }

    private void sendEmailVerification() {
        user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MiCuenta.this,
                                    getResources().getString(R.string.toastEmailVerificacionEnviadoCorrectamenteRegistroIncial),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MiCuenta.this,
                                    getResources().getString(R.string.toastEmailVerificacionEnviadoIncorrectoRegistroIncial),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                    sendEmailVerification();
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
                        getResources().getString(R.string.cambiarPasswordDialogMiCuenta, email),opcion);
                break;

            case R.id.btnEliminarArmario:
                opcion = 3;
                crearDialog(getResources().getString(R.string.eliminarArmarioTituloDialogMiCuenta),
                        getResources().getString(R.string.eliminarArmarioDialogMiCuenta),opcion);
                break;

            case R.id.btnEliminarCuenta:
                opcion = 4;
                crearDialog(getResources().getString(R.string.eliminarCuentaTituloDialogMiCuenta),
                        getResources().getString(R.string.eliminarCuentaDialogMiCuenta, email),opcion);
                break;

            case R.id.bAlturaMiCuenta:
                new DialogPersonalizadoPesoAltura(contexto, MiCuenta.this, "Altura");
                // TODO hacer que se cambie la altura, por la seleccionada, abajo igual con el peso
                break;

            case R.id.bPesoMiCuenta:
                new DialogPersonalizadoPesoAltura(contexto, MiCuenta.this, "Peso");
                break;

            case R.id.bTallaMiCuenta:
                // TODO hacer el de talla
                break;
        }

    }

    @Override
    public void resultado(int num) {
        // BLa bla bla
    }
}
