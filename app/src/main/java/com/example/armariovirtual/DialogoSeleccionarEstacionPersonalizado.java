package com.example.armariovirtual;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class DialogoSeleccionarEstacionPersonalizado implements View.OnClickListener {

    private LinearLayout layoutPrimavera, layoutVerano, layoutOtono, layoutInvierno;
    private Button btnPrivamera, btnVerano, btnOtono, btnInvierno;
    private Button bAceptar, bCancelar;
    // Para mostrar Dialog y pasar parámetros:
    private Dialog dialogSeleccion;
    private Context contextoActual;
    private String estacionSeleccionada;

    public interface finalizarDialog {
        void cogerString(String seleccion);
    }

    private finalizarDialog interfazDialogString;

    public DialogoSeleccionarEstacionPersonalizado (Context contexto, finalizarDialog actividadDialog) {
        interfazDialogString = actividadDialog;
        contextoActual = contexto;

        definicionesVariables(contexto);
        onClickListener();
        realizarAcciones();
    }

    private void definicionesVariables(Context context) {
        dialogSeleccion = new Dialog(context);
        dialogSeleccion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSeleccion.setContentView(R.layout.selector_estacion_year);

        estacionSeleccionada = "";

        btnPrivamera = dialogSeleccion.findViewById(R.id.bPrimavera);
        btnVerano = dialogSeleccion.findViewById(R.id.bVerano);
        btnOtono = dialogSeleccion.findViewById(R.id.bOtono);
        btnInvierno = dialogSeleccion.findViewById(R.id.bInvierno);

        layoutPrimavera = dialogSeleccion.findViewById(R.id.layoutPrimavera);
        layoutVerano = dialogSeleccion.findViewById(R.id.layoutVerano);
        layoutOtono = dialogSeleccion.findViewById(R.id.layoutOtono);
        layoutInvierno = dialogSeleccion.findViewById(R.id.layoutInvierno);

        bAceptar = dialogSeleccion.findViewById(R.id.bAceptarSelectorEstacion);
        bCancelar = dialogSeleccion.findViewById(R.id.bCancelarSelectorEstacion);
    }

    private void onClickListener() {
        btnInvierno.setOnClickListener(this);
        btnOtono.setOnClickListener(this);
        btnVerano.setOnClickListener(this);
        btnPrivamera.setOnClickListener(this);

        bAceptar.setOnClickListener(this);
        bCancelar.setOnClickListener(this);
    }

    private void quitarBordesLayout() {
        layoutPrimavera.setBackground(null);
        layoutVerano.setBackground(null);
        layoutOtono.setBackground(null);
        layoutInvierno.setBackground(null);
    }

    private void realizarAcciones() {
        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfazDialogString.cogerString(estacionSeleccionada);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bPrimavera:
                // Hacemos que el usuario vea visualmente cual es la opción seleccionada
                estacionSeleccionada = "Primavera";
                quitarBordesLayout();
                layoutPrimavera.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.bVerano:
                estacionSeleccionada = "Verano";
                quitarBordesLayout();
                layoutVerano.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.bOtono:
                estacionSeleccionada = "Otoño";
                quitarBordesLayout();
                layoutOtono.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.bInvierno:
                estacionSeleccionada = "Invierno";
                quitarBordesLayout();
                layoutInvierno.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            default:
                // Por defecto
                break;
        }
    }


}
