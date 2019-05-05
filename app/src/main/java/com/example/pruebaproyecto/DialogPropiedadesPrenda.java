package com.example.pruebaproyecto;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;


public class DialogPropiedadesPrenda {
    private NumberPicker numberPickerString;
    private Button bAceptar, bCancelar;
    // Para mostrar Dialog y pasar parámetros:
    private Dialog dialogSeleccion;
    public interface acabarDialog {
        void cogerParametro(String seleccion);
    }

    private acabarDialog interfazDialogString;
    private String [] tallas;

    public DialogPropiedadesPrenda (Context contexto, acabarDialog actividadDialog, final String opcion) {

        interfazDialogString = actividadDialog;
        tallas = contexto.getResources().getStringArray(R.array.arrayTallas);
        conectarVariablesConVista(contexto);
        inicializarStringPicker();
        asignarOpcion(opcion);

    }

    private void conectarVariablesConVista(Context context) {
        dialogSeleccion = new Dialog(context);
        dialogSeleccion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSeleccion.setContentView(R.layout.string_picker);

        numberPickerString = dialogSeleccion.findViewById(R.id.npString);
        bAceptar = dialogSeleccion.findViewById(R.id.bAceptarStringPicker);
        bCancelar = dialogSeleccion.findViewById(R.id.bCancelarStringPicker);
    }

    private void inicializarStringPicker() {
        numberPickerString.setMinValue(0);
        numberPickerString.setMaxValue(tallas.length-1);
        numberPickerString.setWrapSelectorWheel(true);
        numberPickerString.setDisplayedValues(tallas);

    }

    private void asignarOpcion(String opcionSeleccionada) {
        switch (opcionSeleccionada) {
            case "Talla":
                bAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cogemos el dato introducido en el 'stringPicker' si le damos a aceptar
                        int seleccionStringPicker = numberPickerString.getValue();
                        interfazDialogString.cogerParametro(tallas[seleccionStringPicker]);
                        dialogSeleccion.dismiss();
                    }
                });
                bCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSeleccion.dismiss();
                    }
                });
                dialogSeleccion.show();
                break;

            default:

                break;

        }

    }



}
