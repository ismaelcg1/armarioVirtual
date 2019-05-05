package com.example.pruebaproyecto;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistroInicial extends AppCompatActivity implements View.OnClickListener, DialogPersonalizadoPesoAltura.finalizarDialog {

    private TextView mMostrarFecha, textViewSeleccionarPeso, textViewSeleccionarAltura;
    // 'Dialog' de fecha
    private DatePickerDialog.OnDateSetListener mMostrarFechaSetListener;
    private Toolbar appToolbar;
    // String comprobación
    private String seleccion, fecha;
    // Ponemos el contexto para el Dialog
    private Context contexto;
    // Botones para seleccionar en un Dialog un numberPicker
    private Button botonSeleccionarAltura, botonSeleccionarPeso, botonRegistrarme;
    // Para autentificación con FireBase
    private FirebaseAuth mAuth = null;
    // Para los layout a mostrar:
    private LinearLayout layout_registro_inicial, layout_progress_bar;
    //
    private EditText nombreUsuario, email, password, passwordRepetida;
    private RadioGroup radioGroup;
    private boolean generoMasculinoSeleccionado;
    private int peso, altura;
    private TextView textViewPeso, textViewAltura;
    // Para ProgressBar
    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primer_registro);

        inicializarVariables();
        conectarVariablesConVista();

        // Pongo el titulo en la toolbar
        appToolbar.setTitle(R.string.nombreActividadRegistroInicial);
        // Asigno la flecha de atras a la toolbar
        appToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        // Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Hacemos el setOnClickListener para los botones
        botonSeleccionarAltura.setOnClickListener(this);
        botonSeleccionarPeso.setOnClickListener(this);
        botonRegistrarme.setOnClickListener(this);

        mMostrarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistroInicial.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mMostrarFechaSetListener,
                        anio, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mMostrarFechaSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                mes = mes + 1;
                // Guardamos la fecha de nacimiento seleccionada
                fecha = mes + "/" + dia + "/" + anio;
                mMostrarFecha.setText(fecha);
            }
        };
    }

    private void conectarVariablesConVista () {
        mMostrarFecha = findViewById(R.id.tvDate);
        appToolbar = findViewById(R.id.appToolbar);
        textViewSeleccionarPeso = findViewById(R.id.textViewSeleccionaPeso);
        textViewSeleccionarAltura = findViewById(R.id.textViewSeleccionaAltura);
        botonSeleccionarAltura = findViewById(R.id.buttonSeleccionarAltura);
        botonSeleccionarPeso = findViewById(R.id.buttonSeleccionarPeso);
        nombreUsuario = findViewById(R.id.editTextNombre);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        passwordRepetida = findViewById(R.id.editTextPasswordRepetida);
        radioGroup = findViewById(R.id.radioGroupGenero);
        textViewAltura = findViewById(R.id.textViewSeleccionaAltura);
        textViewPeso = findViewById(R.id.textViewSeleccionaPeso);
        botonRegistrarme = findViewById(R.id.btnRegistrarme);
        layout_registro_inicial = findViewById(R.id.linearLayoutRegistroInicial);
        layout_progress_bar = findViewById(R.id.linearLayoutProgessBar);
        // Para progressBar
        mProgressBar = findViewById(R.id.progressbar);
        mLoadingText = findViewById(R.id.loadingCompleteTextView);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, false);
    }

    private void inicializarVariables () {
        // Inicializamos el contexto
        contexto = this;
        seleccion = "";
        fecha = "";
        generoMasculinoSeleccionado = true;
        peso = 0;
        altura = 0;
        mAuth = FirebaseAuth.getInstance();
    }

    private boolean validarEmailPassword(String email, String password) {
        boolean correcto = false;
        Pattern pattern;
        Matcher mather;

        if (password == null) {
            // Patrón para validar el email
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


    private void registrarNuevoUsuario() {
        // Cogemos los datos :
        String nombreUser = nombreUsuario.getText().toString();
        String emailUser = email.getText().toString();
        String passwordUser = password.getText().toString();
        String passwordUserRepetida = passwordRepetida.getText().toString();

        if (!validateForm(nombreUser, emailUser, passwordUser, passwordUserRepetida)) {
            return; // Paramos la ejecución
        } else if (!passwordUser.equalsIgnoreCase(passwordUserRepetida)) {
            Toast.makeText(this, getResources().getString(R.string.toastPasswordNoCoincidenRegistroIncial), Toast.LENGTH_LONG).show();
        } else { // Si no hay ningún campo sin completar, registramos desde 0...




            Toast.makeText(this, "Pulsado botón Registrame", Toast.LENGTH_SHORT).show();


            /*
            * EL PROBLEMA ES QUE NO PUEDE ACCEDER AL PULSAR EL BOTON A TASK, QUIZÁS SEA POR ALGO DEL CONTEXTO DE LA ACTIVITY
            * */



            mAuth.createUserWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                Toast.makeText(RegistroInicial.this,
                                        getResources().getString(R.string.toastRegistroCorrectoActividadLogin)+" "+email.getText().toString(), Toast.LENGTH_LONG).show();

                                // Si todo ha salido bien mostramos layoutProgressBar
                                layout_registro_inicial.setVisibility(View.GONE);
                                layout_progress_bar.setVisibility(View.VISIBLE);

                                // Indicamos al usuario que la acción del registro se está llevando a cabo, con un progressBar
                                Runnable r = new Runnable() {
                                    @Override
                                    public void run() {
                                        while (mProgressStatus < 100){
                                            mProgressStatus++;
                                            android.os.SystemClock.sleep(500); // 100
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mProgressBar.setProgress(mProgressStatus);
                                                }
                                            });
                                        }
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mLoadingText.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    }
                                };
                                // Hacemos que dicho hilo se ejecute
                                new Thread(r).start();

                                boolean correcto = true;
                                updateUI(user, correcto);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistroInicial.this, getResources().getString(R.string.toastRegistroIncorrectoActividadLogin), Toast.LENGTH_SHORT).show();
                                // Si ha fallado el registro Hacemos invisible el progress bar
                                layout_registro_inicial.setVisibility(View.VISIBLE);
                                layout_progress_bar.setVisibility(View.GONE);
                                updateUI(null, false);
                            }
                            // ...
                        }
                    });
        }
    }


    private boolean validateForm(String nombreUser, String emailUser, String passwordUser, String passwordUserRepetida) {
        boolean valid = true;

        if (TextUtils.isEmpty(emailUser)) {
            email.setError(getResources().getString(R.string.requerirEmailRegistroInicial));
            valid = false;
        } else  if (validarEmailPassword(emailUser, null)) { // Validamos también que el formato del email esté correcto.
            email.setError(null);
        } else { // Formato incorrecto
            email.setError(getResources().getString(R.string.formatoEmailIncorrectoRegistroInicial));
        }

        if (TextUtils.isEmpty(nombreUser) ) {
            nombreUsuario.setError(getResources().getString(R.string.requerirNombreRegistroInicial));
            valid = false;
        } else if (nombreUser.length() > 20) {
            nombreUsuario.setError(getResources().getString(R.string.nombreNoCorrectoRegistroInicial));
        } else {
            nombreUsuario.setError(null);
            //interfaz.cogerNombre(nombreUsuario.getText().toString());
        }

        if (TextUtils.isEmpty(passwordUser)) {
            password.setError(getResources().getString(R.string.requerirPasswordRegistroInicial));
            valid = false;
        } else if (validarEmailPassword(null, passwordUser)){
            password.setError(null);
        } else {
            password.setError(getResources().getString(R.string.formatoPasswordIncorrectoRegistroInicial));
        }

        if (TextUtils.isEmpty(passwordUserRepetida)) {
            passwordRepetida.setError(getResources().getString(R.string.requerirPasswordRepetidaRegistroInicial));
            valid = false;
        } else {
            passwordRepetida.setError(null);
        }

        if (altura == 0) {
            textViewSeleccionarAltura.setError(getResources().getString(R.string.requerirEmailRegistroInicial));
            valid = false;
        } else {
            textViewSeleccionarAltura.setError(null);
        }

        if (peso == 0) {
            textViewSeleccionarPeso.setError(getResources().getString(R.string.requerirEmailRegistroInicial));
            valid = false;
        } else {
            textViewSeleccionarPeso.setError(null);
        }

        if (fecha.isEmpty()) {
            mMostrarFecha.setError(getResources().getString(R.string.requerirEmailRegistroInicial));
            valid = false;
        } else {
            mMostrarFecha.setError(null);
        }
        return valid;
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
    private void updateUI(FirebaseUser user, boolean correcto) {
        // hideProgressDialog();
        if (user != null && correcto) {
            // Abrimos la actividad inicial:
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

            /*
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);

            findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());
            */
        } else {
            /*
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
            */
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonSeleccionarAltura:
                seleccion = "Altura";
                new DialogPersonalizadoPesoAltura(contexto, RegistroInicial.this, seleccion);
                break;
            case R.id.buttonSeleccionarPeso:
                seleccion = "Peso";
                new DialogPersonalizadoPesoAltura(contexto, RegistroInicial.this, seleccion);
                break;
            case R.id.radioGroupGenero:
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioButtonMasculino) {
                    // RadioButton masculino seleccionado
                    generoMasculinoSeleccionado = true;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioButtonFemenino) {
                    // RadioButton femenino seleccionado
                    generoMasculinoSeleccionado = false;
                }
                break;

            case R.id.btnRegistrarme:
                registrarNuevoUsuario();
                break;
            default:
                Toast.makeText(this, "Error switch - onClick - RegistroInicial", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void resultado(int num) {
        // Dependiendo de la operación que hayamos seleccionado:
        if (seleccion.equalsIgnoreCase("Altura")) {
            altura = num;
            textViewAltura.setText(""+num+" Cm");
        } else if (seleccion.equalsIgnoreCase("Peso")) {
            peso = num;
            textViewPeso.setText(""+num+" Kg");
        }
    }
}
