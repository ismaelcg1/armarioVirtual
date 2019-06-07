package com.example.armariovirtual;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DialogoReautentificacion {

    private Button bAceptar, bCancelar;
    // Para mostrar Dialog y pasar parámetros:
    private Dialog dialogSeleccion;
    private Context contextoActual;
    private EditText passwordIntroducida;

    public interface obtenerPasswordRe {
        void cogerString(String seleccion);
    }

    private obtenerPasswordRe interfazDialogString;

    public DialogoReautentificacion(Context contexto, obtenerPasswordRe actividadDialog) {
        interfazDialogString = actividadDialog;
        contextoActual = contexto;

        definicionesVariables(contexto);
        realizarAcciones();
    }

    private void definicionesVariables(Context context) {
        dialogSeleccion = new Dialog(context);
        dialogSeleccion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSeleccion.setContentView(R.layout.dialog_reautentificacion);

        passwordIntroducida = dialogSeleccion.findViewById(R.id.etPasswordReautentificacion);

        bAceptar = dialogSeleccion.findViewById(R.id.bAceptarPassword);
        bCancelar = dialogSeleccion.findViewById(R.id.bCancelarPassword);
    }

    private void realizarAcciones() {
        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfazDialogString.cogerString(passwordIntroducida.getText().toString());
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
