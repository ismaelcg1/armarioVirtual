package com.example.pruebaproyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogoSelectorColorPersonalizado extends DialogFragment implements View.OnClickListener {
    // Botones 'coloreados'
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20;
    // Para los colores:
    private List<Integer> colores;
    private List <Button> botonesArray;
    // Constantes de colores
    private final int red =        0xffF44336;
    private final int pink =       0xffE91E63;
    private final int Purple =     0xff9C27B0;
    private final int DeepPurple = 0xff673AB7;
    private final int Indigo =     0xff3F51B5;
    private final int Blue =       0xff2196F3;
    private final int LightBlue =  0xff03A9F4;
    private final int Cyan =       0xff00BCD4;
    private final int Teal =       0xff009688;
    private final int Green =      0xff4CAF50;
    private final int Lime =       0xffCDDC39;
    private final int Yellow =     0xffFFEB3B;
    private final int Amber =      0xffFFC107;
    private final int Orange =     0xffFF9800;
    private final int DeepOrange = 0xffFF5722;
    private final int Brown =      0xff795548;
    private final int Grey =       0xff9E9E9E;
    private final int BlueGray =   0xff607D8B;
    private final int Black =      0xff000000;
    private final int White =      0xffffffff;

    // Para que el usuario vea el color que ha pulsado, debemos crearnos los diferentes layout de los botones:
    private LinearLayout layoutB1,layoutB2,layoutB3,layoutB4, layoutB5,layoutB6,layoutB7,layoutB8,layoutB9,layoutB10;
    private LinearLayout layoutB11,layoutB12,layoutB13,layoutB14, layoutB15,layoutB16,layoutB17,layoutB18,layoutB19,layoutB20;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View vista = inflater.inflate(R.layout.selector_colores, null);

        // Para colores:
        definicionesVariables(vista);
        insertarColoresArrayList();
        insertarBotonesArray();
        cambiarBackgroundColorBotones();
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

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    private void definicionesVariables(View vista) {

        colores = new ArrayList<>();
        botonesArray = new ArrayList<>();

        btn1 = vista.findViewById(R.id.b1);
        btn2 = vista.findViewById(R.id.b2);
        btn3 = vista.findViewById(R.id.b3);
        btn4 = vista.findViewById(R.id.b4);
        btn5 = vista.findViewById(R.id.b5);
        btn6 = vista.findViewById(R.id.b6);
        btn7 = vista.findViewById(R.id.b7);
        btn8 = vista.findViewById(R.id.b8);
        btn9 = vista.findViewById(R.id.b9);
        btn10 = vista.findViewById(R.id.b10);
        btn11 = vista.findViewById(R.id.b11);
        btn12 = vista.findViewById(R.id.b12);
        btn13 = vista.findViewById(R.id.b13);
        btn14 = vista.findViewById(R.id.b14);
        btn15 = vista.findViewById(R.id.b15);
        btn16 = vista.findViewById(R.id.b16);
        btn17 = vista.findViewById(R.id.b17);
        btn18 = vista.findViewById(R.id.b18);
        btn19 = vista.findViewById(R.id.b19);
        btn20 = vista.findViewById(R.id.b20);

        layoutB1 = vista.findViewById(R.id.layoutB1);
        layoutB2 = vista.findViewById(R.id.layoutB2);
        layoutB3 = vista.findViewById(R.id.layoutB3);
        layoutB4 = vista.findViewById(R.id.layoutB4);
        layoutB5 = vista.findViewById(R.id.layoutB5);
        layoutB6 = vista.findViewById(R.id.layoutB6);
        layoutB7 = vista.findViewById(R.id.layoutB7);
        layoutB8 = vista.findViewById(R.id.layoutB8);
        layoutB9 = vista.findViewById(R.id.layoutB9);
        layoutB10 = vista.findViewById(R.id.layoutB10);
        layoutB11 = vista.findViewById(R.id.layoutB11);
        layoutB12 = vista.findViewById(R.id.layoutB12);
        layoutB13 = vista.findViewById(R.id.layoutB13);
        layoutB14 = vista.findViewById(R.id.layoutB14);
        layoutB15 = vista.findViewById(R.id.layoutB15);
        layoutB16 = vista.findViewById(R.id.layoutB16);
        layoutB17 = vista.findViewById(R.id.layoutB17);
        layoutB18 = vista.findViewById(R.id.layoutB18);
        layoutB19 = vista.findViewById(R.id.layoutB19);
        layoutB20 = vista.findViewById(R.id.layoutB20);
    }

    private void insertarBotonesArray() {
        botonesArray.add(btn1);
        botonesArray.add(btn2);
        botonesArray.add(btn3);
        botonesArray.add(btn4);
        botonesArray.add(btn5);
        botonesArray.add(btn6);
        botonesArray.add(btn7);
        botonesArray.add(btn8);
        botonesArray.add(btn9);
        botonesArray.add(btn10);
        botonesArray.add(btn11);
        botonesArray.add(btn12);
        botonesArray.add(btn13);
        botonesArray.add(btn14);
        botonesArray.add(btn15);
        botonesArray.add(btn16);
        botonesArray.add(btn17);
        botonesArray.add(btn18);
        botonesArray.add(btn19);
        botonesArray.add(btn20);
    }

    private void insertarColoresArrayList() {
        colores.add(red);
        colores.add(pink);
        colores.add(Purple);
        colores.add(DeepPurple);
        colores.add(Indigo);
        colores.add(Blue);
        colores.add(LightBlue);
        colores.add(Cyan);
        colores.add(Teal);
        colores.add(Green);
        colores.add(Lime);
        colores.add(Yellow);
        colores.add(Amber);
        colores.add(Orange);
        colores.add(DeepOrange);
        colores.add(Brown);
        colores.add(Grey);
        colores.add(BlueGray);
        colores.add(White);
        colores.add(Black);
    }

    private void onClickListener() {

         btn1.setOnClickListener(DialogoSelectorColorPersonalizado.this);
         btn2.setOnClickListener(DialogoSelectorColorPersonalizado.this);
         btn3.setOnClickListener(DialogoSelectorColorPersonalizado.this);
         btn4.setOnClickListener(DialogoSelectorColorPersonalizado.this);
         btn5.setOnClickListener(DialogoSelectorColorPersonalizado.this);
         btn6.setOnClickListener(DialogoSelectorColorPersonalizado.this);
         btn7.setOnClickListener(DialogoSelectorColorPersonalizado.this);
         btn8.setOnClickListener(DialogoSelectorColorPersonalizado.this);
         btn9.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn10.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn11.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn12.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn13.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn14.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn15.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn16.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn17.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn18.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn19.setOnClickListener(DialogoSelectorColorPersonalizado.this);
        btn20.setOnClickListener(DialogoSelectorColorPersonalizado.this);
    }

    private void cambiarBackgroundColorBotones() {
        for (int i=0; i<botonesArray.size(); i++) {
            botonesArray.get(i).getBackground().setColorFilter( colores.get(i), PorterDuff.Mode.SRC);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Cambiamos el color al layout, al pulsar el botón para que el usuario, vea que lo ha pulsado:
            case R.id.b1:
                quitarBordesLayout();
                layoutB1.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b2:
                quitarBordesLayout();
                layoutB2.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b3:
                quitarBordesLayout();
                layoutB3.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b4:
                quitarBordesLayout();
                layoutB4.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b5:
                quitarBordesLayout();
                layoutB5.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b6:
                quitarBordesLayout();
                layoutB6.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b7:
                quitarBordesLayout();
                layoutB7.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b8:
                quitarBordesLayout();
                layoutB8.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b9:
                quitarBordesLayout();
                layoutB9.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b10:
                quitarBordesLayout();
                layoutB10.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b11:
                quitarBordesLayout();
                layoutB11.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b12:
                quitarBordesLayout();
                layoutB12.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b13:
                quitarBordesLayout();
                layoutB13.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b14:
                quitarBordesLayout();
                layoutB14.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b15:
                quitarBordesLayout();
                layoutB15.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b16:
                quitarBordesLayout();
                layoutB16.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b17:
                quitarBordesLayout();
                layoutB17.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b18:
                quitarBordesLayout();
                layoutB18.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b19:
                quitarBordesLayout();
                layoutB19.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b20:
                quitarBordesLayout();
                layoutB20.setBackground(getResources().getDrawable(R.drawable.layout_border));
                break;
            default:
                // Por defecto
                break;
        }
    }

    private void quitarBordesLayout() {
        layoutB1.setBackground(null);
        layoutB2.setBackground(null);
        layoutB3.setBackground(null);
        layoutB4.setBackground(null);
        layoutB5.setBackground(null);
        layoutB6.setBackground(null);
        layoutB7.setBackground(null);
        layoutB8.setBackground(null);
        layoutB9.setBackground(null);
        layoutB10.setBackground(null);
        layoutB11.setBackground(null);
        layoutB12.setBackground(null);
        layoutB13.setBackground(null);
        layoutB14.setBackground(null);
        layoutB15.setBackground(null);
        layoutB16.setBackground(null);
        layoutB17.setBackground(null);
        layoutB18.setBackground(null);
        layoutB19.setBackground(null);
        layoutB20.setBackground(null);
    }
}
