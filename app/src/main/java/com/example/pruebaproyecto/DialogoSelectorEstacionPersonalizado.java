package com.example.pruebaproyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogoSelectorEstacionPersonalizado extends DialogFragment implements View.OnClickListener {

    private LinearLayout layoutPrimavera, layoutVerano, layoutOtono, layoutInvierno;
    private Button btnPrivamera, btnVerano, btnOtono, btnInvierno;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View vista = inflater.inflate(R.layout.selector_estacion_year, null);

        definicionesVariables(vista);
        onClickListener();

        builder.setView(vista)
                .setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Diálogos", "Confirmación aceptada.");

                        //usuarioIntroducido = vista.findViewById(R.id.usuarioIntroducido);
                        // passwordIntroducida = vista.findViewById(R.id.passwordIntroducida);

                        // Acciones al darle al botón positivo

                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Diálogos", "Confirmación Cancelada.");
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    private void definicionesVariables(View vista) {

        btnPrivamera = vista.findViewById(R.id.bPrimavera);
        btnVerano = vista.findViewById(R.id.bVerano);
        btnOtono = vista.findViewById(R.id.bOtono);
        btnInvierno = vista.findViewById(R.id.bInvierno);

        layoutPrimavera = vista.findViewById(R.id.layoutPrimavera);
        layoutVerano = vista.findViewById(R.id.layoutVerano);
        layoutOtono = vista.findViewById(R.id.layoutOtono);
        layoutInvierno = vista.findViewById(R.id.layoutInvierno);
    }

    private void onClickListener() {
        btnInvierno.setOnClickListener(DialogoSelectorEstacionPersonalizado.this);
        btnOtono.setOnClickListener(DialogoSelectorEstacionPersonalizado.this);
        btnVerano.setOnClickListener(DialogoSelectorEstacionPersonalizado.this);
        btnPrivamera.setOnClickListener(DialogoSelectorEstacionPersonalizado.this);
    }

    private void quitarBordesLayout() {
        layoutPrimavera.setBackground(null);
        layoutVerano.setBackground(null);
        layoutOtono.setBackground(null);
        layoutInvierno.setBackground(null);
    }


    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bPrimavera:
                // Hacemos que el usuario vea visualmente cual es la opción seleccionada
                quitarBordesLayout();
                layoutPrimavera.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.bVerano:
                quitarBordesLayout();
                layoutVerano.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.bOtono:
                quitarBordesLayout();
                layoutOtono.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.bInvierno:
                quitarBordesLayout();
                layoutInvierno.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            default:
                // Por defecto
                break;

        }

    }
}
