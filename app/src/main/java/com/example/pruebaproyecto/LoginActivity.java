package com.example.pruebaproyecto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button botonDrawer;
    private TextInputLayout email, password;
    private ProgressDialog progressDialog;
    private TextView textoNoRegistrado;

    // Para autentificación con FireBase
    private FirebaseAuth mAuth = null;

    // Para ver si el usuario está registrado:
    private boolean usuarioCorrecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Cambiamos el título de la actividad:
        this.setTitle(R.string.nombreActividadLogin);
        conectarVariablesConVista();

        progressDialog = new ProgressDialog(this);

        // Aplicamos al botón que pueda ser 'pulsado'
        botonDrawer.setOnClickListener(this);

        // Para autentificación con FireBase
        mAuth = FirebaseAuth.getInstance();


        /*

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
*/
    }

    private void conectarVariablesConVista() {
        botonDrawer = findViewById(R.id.buttonDrawer);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        textoNoRegistrado = findViewById(R.id.textoClickable);
    }

    // Para autentificación con FireBase, vemos si el usuario se ha registrado:
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email.getEditText().setText(""+currentUser.getEmail());
        }
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (user.isEmailVerified()) {
                // Inicializamos la actividad principal si todo es correcto:
                Intent intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
                startActivity(intent);
            } else {
                // Aunque el usuario se haya registrado, si no ha verificado el email debe hacerlo:
                snackBarVerificacion();
            }
        }
        // else ¿?
    }

    private void snackBarVerificacion() {
        // Cogemos la vista con getWindow().getDecorView().getRootView()
        Snackbar.make(getWindow().getDecorView().getRootView(), getResources().getString(R.string.snackBarEnviarEmail), Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .setAction(getResources().getString(R.string.snackBarBotonEnviarEmail), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendEmailVerification();
                    }
                })
                .show();
    }

    public void comprobarUsuario() {

        final String emailUser, passwordUser;
        emailUser = email.getEditText().getText().toString().trim(); // Para quitar espacios en blanco --> trim()
        passwordUser = password.getEditText().getText().toString().trim();

        usuarioCorrecto = false;

        // Vemos si se han insertado los datos o están los campos vacios:
        if (!validarDatos(emailUser, passwordUser)) {
            return;
        } else {

            // Este método es para cuando un usuario se halla registrado, que no se tenga que volver a registrar:
            mAuth.signInWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.toastLoginCorrectoActividadLogin), Toast.LENGTH_SHORT).show();

                                FirebaseUser user = mAuth.getCurrentUser();
                                // Actualizamos la vista:
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.toastLoginIncorrectoActividadLogin), Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });

        }
    }

    private boolean validarEmailPassword(String email, String password) {
        boolean correcto = false;
        Pattern pattern;
        Matcher mather;

        if (password == null) {
            // Patrón para validar el email (si es muy largo (+65 caracteres) no lo da por válido)
            pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            mather = pattern.matcher(email);

            if (mather.find() == true) {
                // Email válido
                correcto = true;
            } else {
                correcto = false;
            }
        } else if (email == null) {
            // Patrón para validar password, con 1 dígito, carácter especial, mayuscula, minúscula, no espacios en blanco, 8-15 caracteres.
            pattern = Pattern
                    .compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])([A-Za-z\\d$@$!%*?&]|[^ ]){8,15}$");
            mather = pattern.matcher(password);

            if (mather.find() == true) {
                // Email válido
                correcto = true;
            } else {
                correcto = false;
            }
        }
        return correcto;
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.toastEmailVerificacionEnviadoCorrectoRegistroIncial)
                                            + " " + user.getEmail(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.toastEmailVerificacionEnviadoIncorrectoRegistroIncial),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validarDatos(String emailUser, String passwordUser) {

        if (emailUser.isEmpty()) {
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.requerirEmailRegistroInicial), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!validarEmailPassword(emailUser, null)) { // Validamos también que el formato del email esté correcto.
            // Formato incorrecto
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.formatoEmailIncorrectoRegistroInicial), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (passwordUser.isEmpty()) {
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.requerirPasswordRegistroInicial), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!validarEmailPassword(null, passwordUser)){
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.formatoPasswordIncorrectoRegistroInicial), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonDrawer:
                comprobarUsuario();
                break;

            case R.id.textoClickable:
                // Cambiamos color de texto al pulsar
                textoNoRegistrado.setTextColor(Color.parseColor("#EBB93509"));

                Intent intent2 = new Intent(this, RegistroInicial.class);
                startActivity(intent2);

                break;

            default:
                break;
        }

    }
}
