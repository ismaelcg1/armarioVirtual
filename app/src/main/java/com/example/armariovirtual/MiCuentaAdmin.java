package com.example.armariovirtual;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MiCuentaAdmin extends AppCompatActivity implements View.OnClickListener, DialogoReautentificacion.obtenerPasswordRe{

    private Toolbar appToolbar;
    private Button bCerrarSesion, bCambiarPassword, bEliminarBaseDatosArmario, bEliminarCuenta;
    private TextView emailUser;
    private String email;
    // FireBase
    private FirebaseAuth mAuth = null;
    private FirebaseUser user;
    private String passwordIntroducida;

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

    private void sendPasswordReset(final String email) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MiCuentaAdmin.this,
                                    getResources().getString(R.string.cambioPasswordActividadLogin)+" "+email, Toast.LENGTH_LONG).show();
                        } else {
                            // falloCambioPasswordActividadLogin
                            Toast.makeText(MiCuentaAdmin.this,
                                    getResources().getString(R.string.falloCambioPasswordActividadLogin), Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
                    new DialogoReautentificacion(MiCuentaAdmin.this, MiCuentaAdmin.this);
                } else if (opcion == 3) {
                    sendPasswordReset(email);
                } else if (opcion == 4){
                    resetearBaseDatosGlobal();
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

    private void resetearBaseDatosGlobal() {



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

    private void eliminarUsuario() {
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ServidorPHP objetoPHP = new ServidorPHP();
                            try {
                                objetoPHP.eliminarUsuario(user.getUid());
                            } catch (ServidorPHPException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(MiCuentaAdmin.this, getResources().getString(R.string.eliminarCuentaCorrectoMiCuenta),
                                    Toast.LENGTH_LONG).show();
                            finish();
                            Intent intentLoginActivity = new Intent(MiCuentaAdmin.this, LoginActivity.class);
                            startActivity(intentLoginActivity);
                        } else {
                            Toast.makeText(MiCuentaAdmin.this, getResources().getString(R.string.eliminarCuentaFalloMiCuenta),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void reAutentificacion() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), passwordIntroducida);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Usuario re-autentificado
                        eliminarUsuario();
                    }
                });
    }

    @Override
    public void cogerString(String seleccion) {
        passwordIntroducida = seleccion;
        reAutentificacion();
    }
}
