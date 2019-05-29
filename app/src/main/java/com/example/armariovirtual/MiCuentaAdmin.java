package com.example.armariovirtual;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MiCuentaAdmin extends AppCompatActivity implements View.OnClickListener{

    private Toolbar appToolbar;
    private Button bCerrarSesion, bCambiarPassword, bEliminarBaseDatosArmario, bEliminarCuenta;
    private TextView emailUser;
    private String email;
    // FireBase
    private FirebaseAuth mAuth = null;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion_admin);
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

    private void onClickListener() {
        bCerrarSesion.setOnClickListener(this);
        bCambiarPassword.setOnClickListener(this);
        bEliminarBaseDatosArmario.setOnClickListener(this);
        bEliminarCuenta.setOnClickListener(this);
    }

    private void ponerEmailUsuario() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            emailUser.setText(email);
        }
    }

    private void inicializarToolbar() {
        appToolbar.setTitle(R.string.nombreActividadConfiguracion);
        // Asigno la flecha atras
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
    }

    private void conectarVariablesConVista() {
        appToolbar = findViewById(R.id.appToolbar);
        bCerrarSesion = findViewById(R.id.btnCerrarSesionAdmin);
        bCambiarPassword = findViewById(R.id.btnCambiarPasswordAdmin);
        bEliminarBaseDatosArmario = findViewById(R.id.btnEliminarArmarioAdmin);
        bEliminarCuenta = findViewById(R.id.btnEliminarCuentaAdmin);
        emailUser = findViewById(R.id.tvEmailAdmin);
    }

    @Override
    public void onClick(View v) {
        int opcion;

        switch (v.getId()) {
            case R.id.btnCerrarSesionAdmin:
                opcion = 1;
                crearDialog(getResources().getString(R.string.cerrarSesionTituloDialogMiCuenta),
                        getResources().getString(R.string.cerrarSesionDialogMiCuenta), opcion);
                break;

            case R.id.btnEliminarCuentaAdmin:
                opcion = 2;
                crearDialog(getResources().getString(R.string.eliminarCuentaTituloDialogMiCuenta),
                        getResources().getString(R.string.eliminarCuentaDialogMiCuenta, email), opcion);
                break;

            case R.id.btnCambiarPasswordAdmin:
                opcion = 3;
                crearDialog(getResources().getString(R.string.cambiarPasswordTituloDialogMiCuenta),
                        getResources().getString(R.string.cambiarPasswordDialogMiCuenta, email), opcion);
                break;

            case R.id.btnEliminarArmarioAdmin:
                opcion = 4;
                crearDialog(getResources().getString(R.string.eliminarArmarioTituloDialogMiCuentaAdmin),
                        getResources().getString(R.string.resetearArmarioDialogMiCuentaAdmin), opcion);
                break;
        }

    }

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

    private void signOut(int id) {
        // FireBase
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        updateUI();
    }

    private void updateUI() {

        finish();
        // TODO cerrar UI de MainActivityDrawerAdmin, al dar atras vuelve a la pantalla aún estando la sesión cerrada
        Intent intentLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intentLoginActivity);

    }
}
