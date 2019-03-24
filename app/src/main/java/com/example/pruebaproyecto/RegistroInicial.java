package com.example.pruebaproyecto;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Calendar;

public class RegistroInicial extends AppCompatActivity {

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Toolbar appToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primer_registro);
        mDisplayDate = findViewById(R.id.tvDate);
        appToolbar = findViewById(R.id.appToolbar);

        // Para el numberSpicker
        NumberPicker npAltura = findViewById(R.id.numberPickerAltura);
        NumberPicker npPeso = findViewById(R.id.numberPickerPeso);

        npAltura.setMinValue(2);
        npAltura.setMaxValue(20);

        npAltura.setOnValueChangedListener(onValueChangeListener);

        npPeso.setMinValue(30);
        npPeso.setMaxValue(130);

        npPeso.setOnValueChangedListener(onValueChangeListener);

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
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }


    NumberPicker.OnValueChangeListener onValueChangeListener =
            new 	NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Toast.makeText(RegistroInicial.this,
                            "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
                }
            };


    public void registrarNuevoUsuario () {
/*
        // --- Esto es para registrar desde 0, cambiar a Registro Inicial, OJO con los layout.
        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.toastRegistroCorrectoActividadLogin)+" "+emailUser, Toast.LENGTH_SHORT).show();

                            // Si todo ha salido bien mostramos layoutProgressBar
                            // y hacemos visible el layoutLogin
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



                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);



                        } else {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.toastRegistroIncorrectoActividadLogin), Toast.LENGTH_SHORT).show();

                            // Si ha fallado el registro
                            // Hacemos invisible el layoutLogin
                            layout_login.setVisibility(View.VISIBLE);
                            layout_progress_bar.setVisibility(View.GONE);

                            //updateUI(null);

                        }

                        // ...
                    }
                });*/


    }
}
