package com.example.armariovirtual;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
    private EditText aliasUsuario, email, password, passwordRepetida;
    private RadioGroup radioGroup;
    private boolean generoMasculinoSeleccionado;
    private int peso, altura;
    private TextView textViewPeso, textViewAltura;
    private String fechaServidor;

    private RadioButton botonGeneroPulsado;
    private Boolean es_administrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primer_registro);

        inicializarVariables();
        conectarVariablesConVista();
        obtenerDatos();

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
                fecha = dia + "/" + mes + "/" + anio;
                mMostrarFecha.setText(fecha);
                // Guardamos el formato de la fecha para el servidor:
                if (mes < 10) {
                    fechaServidor = anio + "-0" + mes + "-" + dia;
                } else {
                    fechaServidor = anio + "-" + mes + "-" + dia;
                }
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
        aliasUsuario = findViewById(R.id.editTextAlias);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        passwordRepetida = findViewById(R.id.editTextPasswordRepetida);
        radioGroup = findViewById(R.id.radioGroupGenero);
        textViewAltura = findViewById(R.id.textViewSeleccionaAltura);
        textViewPeso = findViewById(R.id.textViewSeleccionaPeso);
        botonRegistrarme = findViewById(R.id.btnRegistrarme);
        layout_registro_inicial = findViewById(R.id.linearLayoutRegistroInicial);
    }

    private void obtenerDatos() {
        final String ADMINISTRADOR = "administrador";
        Intent intentActividadActual = getIntent();
        Bundle b = intentActividadActual.getExtras();
        if(b!=null) {
            es_administrador = b.getBoolean(ADMINISTRADOR);
        }
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
        fechaServidor = "";
        mAuth = FirebaseAuth.getInstance();
        es_administrador = false;
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
        String nombreUser = aliasUsuario.getText().toString();
        String emailUser = email.getText().toString();
        String passwordUser = password.getText().toString();
        String passwordUserRepetida = passwordRepetida.getText().toString();
        final String tallaPorDefecto = obtenerTalla();

         if (!passwordUser.equalsIgnoreCase(passwordUserRepetida)) {
            Toast.makeText(this, getResources().getString(R.string.toastPasswordNoCoincidenRegistroIncial), Toast.LENGTH_LONG).show();
        } else if (validateForm(nombreUser, emailUser, passwordUser, passwordUserRepetida)) { // Si no hay ningún campo sin completar, registramos desde 0...

            mAuth.createUserWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                Toast.makeText(RegistroInicial.this,
                                        getResources().getString(R.string.toastRegistroCorrectoActividadLogin)+" "+email.getText().toString(), Toast.LENGTH_LONG).show();
                                boolean correcto = true;

                                ServidorPHP objetoServidor = new ServidorPHP();
                                Boolean todoCorrecto = false;
                                try {
                                    todoCorrecto = objetoServidor.registrarUsuario(user.getUid(),aliasUsuario.getText().toString(),
                                                                    altura, peso, tallaPorDefecto, fechaServidor, generoMasculinoSeleccionado );
                                } catch (ServidorPHPException e) {
                                    e.printStackTrace();
                                }

                                if (todoCorrecto) {
                                    Toast.makeText(RegistroInicial.this, "Todo correcto", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegistroInicial.this, "Fallo registroInicial BBDD", Toast.LENGTH_SHORT).show();
                                }


                                updateUI(user, correcto);
                            }
                        }
                    });
        }
    }

    private String obtenerTalla() {
        String tallaObtenida;
        Double resultadoIMC;
        Double alturaDoubleMetros = ( Double.valueOf(altura) ) /100;
        // 'Formula' aproximada para calcular la talla, calculando el IMC:
        //  peso[kg] / (altura[m] * altura[m])
        resultadoIMC = Double.valueOf ( peso / ( alturaDoubleMetros * alturaDoubleMetros) );

        if (resultadoIMC < 18.0) {
            tallaObtenida = "XS";
        } else if (resultadoIMC < 21.0) {
            tallaObtenida = "S";
        } else if (resultadoIMC < 25.0) {
            tallaObtenida = "M";
        } else if (resultadoIMC < 29.0) {
            tallaObtenida = "L";
        } else if (resultadoIMC < 34.0) {
            tallaObtenida = "XL";
        } else {
            tallaObtenida = "XXL";
        }

        return tallaObtenida;
    }


    private boolean validateForm(String aliasUser, String emailUser, String passwordUser, String passwordUserRepetida) {
        boolean valid = true;

        if (TextUtils.isEmpty(emailUser)) {
            email.setError(getResources().getString(R.string.requerirEmailRegistroInicial));
            valid = false;
        } else  if (validarEmailPassword(emailUser, null)) { // Validamos también que el formato del email esté correcto.
            email.setError(null);
        } else { // Formato incorrecto
            email.setError(getResources().getString(R.string.formatoEmailIncorrectoRegistroInicial));
            valid = false;
        }

        if (TextUtils.isEmpty(aliasUser) ) {
            aliasUsuario.setError(getResources().getString(R.string.requerirAliasRegistroInicial));
            valid = false;
        } else if (aliasUser.length() > 20) {
            aliasUsuario.setError(getResources().getString(R.string.aliasNoCorrectoRegistroInicial));
            valid = false;
        } else {
            aliasUsuario.setError(null);
        }

        if (TextUtils.isEmpty(passwordUser)) {
            password.setError(getResources().getString(R.string.requerirPasswordRegistroInicial));
            valid = false;
        } else if (validarEmailPassword(null, passwordUser)){
            password.setError(null);
        } else {
            password.setError(getResources().getString(R.string.formatoPasswordIncorrectoRegistroInicial));
            valid = false;
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


    private void updateUI(FirebaseUser user, boolean correcto) {
        // hideProgressDialog();
        if (user != null && correcto) {
            if (es_administrador) {
                inicializarVariables();
                resetearCampos();
            } else { // Volvemos al login
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void resetearCampos() {

        aliasUsuario.setText("");
        email.setText("");
        password.setText("");
        passwordRepetida.setText("");
        mMostrarFecha.setText(R.string.seleccionarFechaRegistroInicial);
        textViewSeleccionarAltura.setText(R.string.textViewAlturaRegistroInicial);
        textViewSeleccionarPeso.setText(R.string.textViewPesoRegistroInicial);
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

            case R.id.btnRegistrarme:
                verGeneroSeleccionado();
                registrarNuevoUsuario();
                break;

            default:
                Toast.makeText(this, "Error switch - onClick - RegistroInicial", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void verGeneroSeleccionado() {
        generoMasculinoSeleccionado = true;
        int selectedId = radioGroup.getCheckedRadioButtonId();
        botonGeneroPulsado = findViewById(selectedId);

        if (botonGeneroPulsado.getText().toString().equalsIgnoreCase("Femenino")) {
            generoMasculinoSeleccionado = false;
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
