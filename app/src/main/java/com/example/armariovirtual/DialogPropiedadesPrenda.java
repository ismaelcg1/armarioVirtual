package com.example.armariovirtual;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DialogPropiedadesPrenda {
    private NumberPicker numberPickerString;
    private Button bAceptar, bCancelar;
    // Para mostrar Dialog y pasar parámetros:
    private Dialog dialogSeleccion;
    // Para cambiar el texto de 'StringPicker'
    private TextView cabeceraStringPicker;
    public interface acabarDialog {
        void cogerParametro(String seleccion);
    }

    private acabarDialog interfazDialogString;
    private String [] tallas;
    private String [] estilos;
    private String [] categorias;
    private String [] subcategorias;


    public DialogPropiedadesPrenda (Context contexto, acabarDialog actividadDialog, final String opcion, String categoriaSeleccionada) {

        interfazDialogString = actividadDialog;
        tallas = contexto.getResources().getStringArray(R.array.arrayTallas);
        estilos = contexto.getResources().getStringArray(R.array.arrayEstilos);
        categorias = contexto.getResources().getStringArray(R.array.arrayCategorias);
        // La subcategoría dependerá de la categoría seleccionada
        if (categoriaSeleccionada!=null) {
            Map<String, List<String>> subcategoriasList = crearArraysSubcategorias(contexto);
            subcategorias = (String []) subcategoriasList.get(categoriaSeleccionada).toArray();
        }

        conectarVariablesConVista(contexto);
        inicializarStringPicker(opcion);
        asignarOpcion(opcion);
    }

    private Map<String, List<String>> crearArraysSubcategorias(Context contexto) {

        String [] ropaParteSuperior = contexto.getResources().getStringArray(R.array.arrayRopaParteSuperior);

        String [] ropaParteInferior = contexto.getResources().getStringArray(R.array.arrayRopaParteInferior);
        String [] ropaParteInteriorHombre = contexto.getResources().getStringArray(R.array.arrayRopaParteInteriorHombre);
        String [] ropaParteInteriorMujer = contexto.getResources().getStringArray(R.array.arrayRopaParteInteriorMujer);
        String [] complementos = contexto.getResources().getStringArray(R.array.arrayComplementos);
        String [] calzadoArray = contexto.getResources().getStringArray(R.array.arrayCalzado);


        Map<String, List<String>> subcategorias = new HashMap<String, List<String>>();

        List<String> ropaSuperior = Arrays.asList(ropaParteSuperior);
        List<String> ropaInferior = Arrays.asList(ropaParteInferior);
        List<String> ropaInteriorHombre = Arrays.asList(ropaParteInteriorHombre);
        List<String> ropaInferiorMujer = Arrays.asList(ropaParteInteriorMujer);
        List<String> complementosRopa = Arrays.asList(complementos);
        List<String> calzado = Arrays.asList(calzadoArray);


        // Añadimos los elementos al Map:
        subcategorias.put("Ropa superior", (ropaSuperior));
        subcategorias.put("Ropa inferior", (ropaInferior));

        // TODO para saber sexo, para subcategorias de ropa interior
        /*
        if (LoginActivity.miUsuario.getSexo() == Sexo.Masculino) {
            subcategorias.put("Ropa interior", (ropaInteriorHombre));
        } else {
            subcategorias.put("Ropa interior", (ropaInferiorMujer));
        }
        */
        subcategorias.put("Complementos", (complementosRopa));
        subcategorias.put("Calzado", (calzado));

        return subcategorias;

    }

    private void conectarVariablesConVista(Context context) {
        dialogSeleccion = new Dialog(context);
        dialogSeleccion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSeleccion.setContentView(R.layout.string_picker);

        numberPickerString = dialogSeleccion.findViewById(R.id.npString);
        bAceptar = dialogSeleccion.findViewById(R.id.bAceptarStringPicker);
        bCancelar = dialogSeleccion.findViewById(R.id.bCancelarStringPicker);
        cabeceraStringPicker = dialogSeleccion.findViewById(R.id.textoStringPicker);
    }

    private void inicializarStringPicker(String op) {
        numberPickerString.setMinValue(0);
        numberPickerString.setWrapSelectorWheel(true);
        // Debemos ver la opción a coger y devolver el array:
        switch (op) {
            case "Talla":
                cabeceraStringPicker.setText(R.string.tallaNumberPickerString);
                numberPickerString.setMaxValue(tallas.length-1);
                numberPickerString.setDisplayedValues(tallas);
                break;
            case "Estilo":
                cabeceraStringPicker.setText(R.string.estiloNumberPickerString);
                numberPickerString.setMaxValue(estilos.length-1);
                numberPickerString.setDisplayedValues(estilos);
                break;
            case "Categoria":
                cabeceraStringPicker.setText(R.string.categoriaNumberPickerString);
                numberPickerString.setMaxValue(categorias.length-1);
                numberPickerString.setDisplayedValues(categorias);
                break;
            case "Subcategoria":
                cabeceraStringPicker.setText(R.string.subcategoriaNumberPickerString);
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

    private void asignarOpcion(String opcionSeleccionada) {

        final String op = opcionSeleccionada;
        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verOpcion(op);
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
