package com.example.armariovirtual;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

    private Button botonIniciarSesion, btnCuentaNueva;
    private TextInputLayout email, password;
    private ProgressDialog progressDialog;
    private TextView cambiarPassword;
    private CheckBox terminosCondiciones;
    // TODO crear clase para guardar datos usuario de BBDD
    //static Usuario miUsuario;
    //LoginActivity.miUsuario

    // FireBase
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
        botonIniciarSesion.setOnClickListener(this);
        btnCuentaNueva.setOnClickListener(this);
        cambiarPassword.setOnClickListener(this);

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
        botonIniciarSesion = findViewById(R.id.btnIniciarSesion);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        btnCuentaNueva = findViewById(R.id.btnCrearNuevaCuenta);
        cambiarPassword = findViewById(R.id.cambiaPassword);
        terminosCondiciones = findViewById(R.id.checkBoxTerminosCondiciones);
    }

    // Para autentificación con FireBase, vemos si el usuario se ha registrado:
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email.getEditText().setText(""+currentUser.getEmail());

            // TODO asignar cosas a la clase
            // hacer consulta a bd para rellenar clase usuario
            // nombre, edad, sexo......
            // miUsuario = new Usuario("nombre", edad, sexo, ...);

        }
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String emailUser;
            emailUser = email.getEditText().getText().toString().trim();
            Intent intent;

            if (user.isEmailVerified()) {
                // Inicializamos la actividad principal si tod@ es correcto:
                if (emailUser.equalsIgnoreCase("ismael.casado@itponiente.com")) {
                    intent = new Intent(getApplicationContext(), MainActivityDrawerAdmin.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
                    startActivity(intent);

                    finish();
                }

            } else {
                // Aunque el usuario se haya registrado, si no ha verificado el email debe hacerlo:
                snackBarVerificacion();
            }
        }
    }

    private void snackBarVerificacion() {
        // Cogemos la vista con getWindow().getDecorView().getRootView()
        Snackbar.make(getWindow().getDecorView().getRootView(), getResources().getString(R.string.snackBarEnviarEmail), Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
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
                                // Autentificación correcta

                                FirebaseUser user = mAuth.getCurrentUser();
                                // Actualizamos la vista:
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.toastLoginIncorrectoActividadLogin), Toast.LENGTH_SHORT).show();
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

    public void sendPasswordReset(final String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.cambioPasswordActividadLogin)+" "+email, Toast.LENGTH_LONG).show();
                        } else {
                            // falloCambioPasswordActividadLogin
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.falloCambioPasswordActividadLogin), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnIniciarSesion:
                // Vemos si el checkBox está pulsado:
                if (!terminosCondiciones.isChecked()) {
                    Toast.makeText(LoginActivity.this, getResources()
                            .getString(R.string.terminosCondicionesIncorrectoActividadLogin), Toast.LENGTH_LONG).show();
                } else {
                    comprobarUsuario();
                }
                break;

            case R.id.btnCrearNuevaCuenta:
                Intent intent2 = new Intent(this, RegistroInicial.class);
                startActivity(intent2);
                break;

            case R.id.cambiaPassword:
                final String emailUser = email.getEditText().getText().toString().trim();
                if (emailUser.isEmpty()) {
                    Toast.makeText(LoginActivity.this, getResources()
                            .getString(R.string.rellenaEmailCambioPasswordActividadLogin), Toast.LENGTH_LONG).show();
                } else if (!validarEmailPassword(emailUser, null)) {
                    Toast.makeText(LoginActivity.this, getResources()
                            .getString(R.string.formatoEmailIncorrectoRegistroInicial), Toast.LENGTH_LONG).show();
                } else {
                    sendPasswordReset(emailUser);
                }
                break;

            default:
                break;
        }

    }
}
