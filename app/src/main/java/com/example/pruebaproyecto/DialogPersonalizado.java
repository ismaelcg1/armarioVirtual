package com.example.pruebaproyecto;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


public class DialogPersonalizado {

    private NumberPicker numberPickerPeso, numberPickerAltura;
    private LinearLayout linearLayoutNumberPickerAltura, linearLayoutNumberPickerPeso;
    private Button aceptarAltura, aceptarPeso, cancelarAltura, cancelarPeso;

    // Para mostrar un dialog y pasar parámetros
    public interface finalizarDialog {
        void resultado(int num);
    }

    private finalizarDialog interfaz;

    public DialogPersonalizado (Context contexto, finalizarDialog actividad, final String seleccion) {

        interfaz = actividad;

        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.number_picker_altura);

        aceptarAltura = dialogo.findViewById(R.id.buttonAceptarAltura);
        cancelarAltura = dialogo.findViewById(R.id.buttonCancelarAltura);
        aceptarPeso = dialogo.findViewById(R.id.buttonAceptarPeso);
        cancelarPeso = dialogo.findViewById(R.id.buttonCancelarPeso);
        linearLayoutNumberPickerAltura = dialogo.findViewById(R.id.linearLayoutNumberPickerAltura);
        linearLayoutNumberPickerPeso = dialogo.findViewById(R.id.linearLayoutNumberPickerPeso);

        // NumberPicker's:
        numberPickerPeso = dialogo.findViewById(R.id.numberPickerPeso);
        numberPickerAltura = dialogo.findViewById(R.id.numberPickerAltura);
        numberPickerAltura.setMinValue(130);
        numberPickerAltura.setMaxValue(210);
        // Ponemos por defecto que salga una altura                            ¿?????????
        numberPickerAltura.setValue(170);
        numberPickerAltura.setOnValueChangedListener(onValueChangeListener);
        numberPickerPeso.setMinValue(30);
        numberPickerPeso.setMaxValue(130);
        // Ponemos por defecto que salga un peso                            ¿?????????
        numberPickerPeso.setValue(70);
        numberPickerPeso.setOnValueChangedListener(onValueChangeListener);


        if (seleccion.equalsIgnoreCase("Altura")) {
            // Mostramos el numberPicker para el Altura
            linearLayoutNumberPickerAltura.setVisibility(View.VISIBLE);
            linearLayoutNumberPickerPeso.setVisibility(View.GONE);

            aceptarAltura.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Cogemos el dato introducido en el numberPickerAltura (int) si le damos a aceptar
                    interfaz.resultado(numberPickerAltura.getValue());
                    dialogo.dismiss();
                }
            });

            cancelarAltura.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialogo.dismiss();
                }
            });

        } else if (seleccion.equalsIgnoreCase("Peso")) {
            // Mostramos el numberPicker para el Peso
            linearLayoutNumberPickerAltura.setVisibility(View.GONE);
            linearLayoutNumberPickerPeso.setVisibility(View.VISIBLE);

            aceptarPeso.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Cogemos el dato introducido en el numberPickerPeso (int) si le damos a aceptar
                    interfaz.resultado(numberPickerPeso.getValue());
                    dialogo.dismiss();
                }
            });

            cancelarPeso.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialogo.dismiss();
                }
            });

        }

        dialogo.show();
    }

    // Number picker
    NumberPicker.OnValueChangeListener onValueChangeListener =
            new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    /*Toast.makeText(getContext(),
                            "selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT);*/
                }
            };

    /*
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.textViewSeleccionaPeso:
                seleccion = "Peso";
                linearLayoutNumberPickerAltura.setVisibility(View.GONE);
                linearLayoutNumberPickerPeso.setVisibility(View.VISIBLE);
                break;

            case R.id.textViewSeleccionaAltura:
                seleccion = "Altura";
                linearLayoutNumberPickerAltura.setVisibility(View.VISIBLE);
                linearLayoutNumberPickerPeso.setVisibility(View.GONE);
                break;
            default:
                seleccion = "";
                break;

        }
    }*/


}
