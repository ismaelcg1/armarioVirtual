package com.example.pruebaproyecto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button botonDrawer;
    private TextInputLayout email, password;
    private ProgressDialog progressDialog;
    private LinearLayout layout_login, layout_progress_bar;
    private TextView textoNoRegistrado;

    // Para autentificación con FireBase
    private FirebaseAuth mAuth = null;

    // Para ProgressBar
    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    // Para ver si el usuario está registrado:
    private boolean usuarioCorrecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Enlazamos nuestras variables a la vista
        botonDrawer = findViewById(R.id.buttonDrawer);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        layout_login = findViewById(R.id.layoutLogin);
        layout_progress_bar = findViewById(R.id.layoutProgressBar);
        textoNoRegistrado = findViewById(R.id.textoClickable);

        progressDialog = new ProgressDialog(this);

        // Aplicamos al botón que pueda ser 'pulsado'
        botonDrawer.setOnClickListener(this);

        // Para autentificación con FireBase
        mAuth = FirebaseAuth.getInstance();


        // Para progressBar
        mProgressBar = findViewById(R.id.progressbar);
        mLoadingText = findViewById(R.id.LoadingCompleteTextView);

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

    // Para autentificación con FireBase, vemos si el usuario se ha registrado:
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //TODO
        //updateUI(currentUser);
    }

    /*
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
*/

    public void comprobarUsuario() {

        final String emailUser, passwordUser;
        emailUser = email.getEditText().getText().toString().trim(); // Para quitar espacios en blanco --> trim()
        passwordUser = password.getEditText().getText().toString().trim();

        // PRUEBAS
        Log.i("Email: ", "" + emailUser);
        Log.i("Password: ", "" + passwordUser);

        usuarioCorrecto = false;

        // Vemos si se han insertado los datos o están los campos vacios:
        if (emailUser.isEmpty()) {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.textoEmailVacioActividadLogin), Toast.LENGTH_SHORT).show();
            // return; // Se detendría la ejecución ??
        } else if (passwordUser.isEmpty()) {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.textoPasswordVacioActividadLogin), Toast.LENGTH_SHORT).show();
            // return; // Se detendría la ejecución ??
        } else {
            // -------------

            // Este método es para cuando un usuario se halla registrado, que no se tenga que volver a registrar:
            mAuth.signInWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.toastLoginCorrectoActividadLogin), Toast.LENGTH_SHORT).show();
                                // PRUEBAS
                                Toast.makeText(LoginActivity.this, "usuarioCorrecto = "+usuarioCorrecto, Toast.LENGTH_SHORT).show();

                                FirebaseUser user = mAuth.getCurrentUser();
                                // -----
                                // Inicializamos la actividad principal si todo es correcto:
                                Intent intent = new Intent(getApplicationContext(), MainActivityDrawer.class);
                                startActivity(intent);
                                // -----
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.toastLoginIncorrectoActividadLogin), Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                            // ...
                        }
                    });

        }
    }


    @Override
    public void onClick(View v) {
        // FragmentManager es para los Dialog
        // FragmentManager fragmentManager = getSupportFragmentManager();

        switch (v.getId()) {
            case R.id.buttonDrawer:
                comprobarUsuario();
                break;

            case R.id.textoClickable:
                // Cambiamos color de texto al pulsar
                textoNoRegistrado.setTextColor(Color.parseColor("#EBB93509"));
                /*
                DialogoCompletarLogin dialogoRegistro = new DialogoCompletarLogin();
                dialogoRegistro.show(fragmentManager, "tagAlerta");
*/

                Intent intent2 = new Intent(this, RegistroInicial.class);
                startActivity(intent2);

                break;

            default:
                break;
        }

    }
}
