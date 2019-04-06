package com.example.pruebaproyecto;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;


public class RegistroInicial extends AppCompatActivity implements View.OnClickListener, DialogPersonalizado.finalizarDialog {

    private TextView mDisplayDate, textViewSeleccionarPeso, textViewSeleccionarAltura;
    // 'Dialog' de fecha
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Toolbar appToolbar;
    // String comprobación
    private String seleccion, fecha;
    // Ponemos el contexto para el Dialog
    private Context contexto;
    // Botones para seleccionar en un Dialog un numberPicker
    private Button botonSeleccionarAltura, botonSeleccionarPeso, botonRegistrarme;
    // Para autentificación con FireBase
    private FirebaseAuth mAuth = null;
    //
    private EditText email, password, passwordRepetida;
    private RadioGroup radioGroup;
    private boolean generoMasculinoSeleccionado;
    private int peso, altura;
    private TextView textViewPeso, textViewAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primer_registro);
        // Inicializamos el contexto
        contexto = this;
        seleccion = "";
        fecha = "";
        generoMasculinoSeleccionado = true;
        peso = 0;
        altura = 0;

        // Conectamos nuestras variables con la vista:
        mDisplayDate = findViewById(R.id.tvDate);
        appToolbar = findViewById(R.id.appToolbar);
        textViewSeleccionarPeso = findViewById(R.id.textViewSeleccionaPeso);
        textViewSeleccionarAltura = findViewById(R.id.textViewSeleccionaAltura);
        botonSeleccionarAltura = findViewById(R.id.buttonSeleccionarAltura);
        botonSeleccionarPeso = findViewById(R.id.buttonSeleccionarPeso);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        passwordRepetida = findViewById(R.id.editTextPasswordRepetida);
        radioGroup = findViewById(R.id.radioGroupGenero);
        textViewAltura = findViewById(R.id.textViewSeleccionaAltura);
        textViewPeso = findViewById(R.id.textViewSeleccionaPeso);
        botonRegistrarme = findViewById(R.id.btnRegistrarme);

        // *************************************************
        // Pongo el titulo en la toolbar
        appToolbar.setTitle(R.string.nombreActividadRegistroInicialDialogCompletarLogin);
        // Asigno la flecha de atras a la toolbar
        appToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        // Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Hacemos el setOnClickListener para los Buttons
        botonSeleccionarAltura.setOnClickListener(this);
        botonSeleccionarPeso.setOnClickListener(this);
        botonRegistrarme.setOnClickListener(this);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistroInicial.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                // Guardamos la fecha de nacimiento seleccionada
                fecha = month + "/" + day + "/" + year;
                mDisplayDate.setText(fecha);
            }
        };
    }



    public void registrarNuevoUsuario() {

        // Cogemos los datos :
        String emailUser = email.getText().toString();
        String passwordUser = password.getText().toString();
        String passwordUserRepetida = passwordRepetida.getText().toString();

        // Ahora comprobamos si el usuario a introducido los datos:

        if (emailUser.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.toastEmailVacioRegistroIncial), Toast.LENGTH_LONG).show();
        } else if (passwordUser.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.toastPasswordVaciaRegistroIncial), Toast.LENGTH_LONG).show();
        } else if (passwordUserRepetida.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.toastPasswordRepetidaVaciaRegistroIncial), Toast.LENGTH_LONG).show();
        } else if (!passwordUser.equalsIgnoreCase(passwordUserRepetida)) {
            Toast.makeText(this, getResources().getString(R.string.toastPasswordNoCoincidenRegistroIncial), Toast.LENGTH_LONG).show();
        } else if (fecha.isEmpty()) { // Vemos si se ha introducido la fecha
            Toast.makeText(this, getResources().getString(R.string.toastFechaVaciaRegistroIncial), Toast.LENGTH_LONG).show();
        } else if (altura == 0) {
            Toast.makeText(this, getResources().getString(R.string.toastAlturaVaciaRegistroIncial), Toast.LENGTH_LONG).show();
        } else if (peso == 0) {
            Toast.makeText(this, getResources().getString(R.string.toastPesoVacioRegistroIncial), Toast.LENGTH_LONG).show();
        } else {
            // Todo correcto, procedemos a hacer el registro

            // --- Esto es para registrar desde 0, cambiar a Registro Inicial, OJO con los layout.
            mAuth.createUserWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegistroInicial.this, getResources().getString(R.string.toastRegistroCorrectoActividadLogin)+" "+email.getText().toString(), Toast.LENGTH_SHORT).show();

                                // Si todo ha salido bien mostramos layoutProgressBar
/*
                                layout_login.setVisibility(View.GONE);
                                layout_progress_bar.setVisibility(View.VISIBLE);

                                // Indicamos al usuario que la acción del registro se está llevando a cabo, con un progressBar

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (mProgressStatus < 100){
                                            mProgressStatus++;
                                            android.os.SystemClock.sleep(20);
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
                                }).start();
*/


                                //FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);

                            } else {
                                Toast.makeText(RegistroInicial.this, getResources().getString(R.string.toastRegistroIncorrectoActividadLogin), Toast.LENGTH_SHORT).show();

                                // Si ha fallado el registro
/*
                                // Hacemos invisible el layoutLogin
                                layout_login.setVisibility(View.VISIBLE);
                                layout_progress_bar.setVisibility(View.GONE);
*/
                                //updateUI(null);

                            }

                            // ...
                        }
                    });
        }

    }

    private void updateUI() {


    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.buttonSeleccionarAltura:
                seleccion = "Altura";
                new DialogPersonalizado(contexto, RegistroInicial.this, seleccion);
                break;
            case R.id.buttonSeleccionarPeso:
                seleccion = "Peso";
                new DialogPersonalizado(contexto, RegistroInicial.this, seleccion);
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
            textViewSeleccionarAltura.setText(""+num+" Cm");
        } else if (seleccion.equalsIgnoreCase("Peso")) {
            peso = num;
            textViewSeleccionarPeso.setText(""+num+" Kg");
        }

    }
}
