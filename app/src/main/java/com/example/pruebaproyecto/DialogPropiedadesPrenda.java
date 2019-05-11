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
    private String [] estilos;
    private String [] categorias;
    private String [] subcategorias;

    public DialogPropiedadesPrenda (Context contexto, acabarDialog actividadDialog, final String opcion) {

        interfazDialogString = actividadDialog;
        tallas = contexto.getResources().getStringArray(R.array.arrayTallas);
        estilos = contexto.getResources().getStringArray(R.array.arrayEstilos);
        categorias = contexto.getResources().getStringArray(R.array.arrayCategorias);
        // La subcategoría dependerá de la categoría seleccionada
        subcategorias = contexto.getResources().getStringArray(R.array.arrayTallas);
        conectarVariablesConVista(contexto);
        inicializarStringPicker(opcion);
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

    private void inicializarStringPicker(String op) {
        numberPickerString.setMinValue(0);
        numberPickerString.setWrapSelectorWheel(true);
        // Debemos ver la opción a coger y devolver el array:
        switch (op) {
            case "Talla":
                numberPickerString.setMaxValue(tallas.length-1);
                numberPickerString.setDisplayedValues(tallas);
                break;
            case "Estilo":
                numberPickerString.setMaxValue(estilos.length-1);
                numberPickerString.setDisplayedValues(estilos);
                break;
            case "Categoria":
                numberPickerString.setMaxValue(categorias.length-1);
                numberPickerString.setDisplayedValues(categorias);
                break;
            case "Subcategoria":
                numberPickerString.setMaxValue(subcategorias.length-1);
                numberPickerString.setDisplayedValues(subcategorias);
                break;
        }

    }

    private void verOpcion (String opcion) {
        // Cogemos el dato introducido en el 'stringPicker'
        int seleccionStringPicker = numberPickerString.getValue();

        switch (opcion) {
            case "Talla":
                interfazDialogString.cogerParametro(tallas[seleccionStringPicker]);
                break;
            case "Estilo":
                interfazDialogString.cogerParametro(estilos[seleccionStringPicker]);
                break;
            case "Categoria":
                interfazDialogString.cogerParametro(categorias[seleccionStringPicker]);
                break;
            case "Subcategoria":
                interfazDialogString.cogerParametro(subcategorias[seleccionStringPicker]);
                break;
        }

    }

    private void asignarOpcion(final String opcionSeleccionada) { // opcionSeleccionada

        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verOpcion(opcionSeleccionada); //
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
    }

}
