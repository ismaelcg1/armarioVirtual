package com.example.armariovirtual;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogoSeleccionarColorPersonalizado implements View.OnClickListener {

    // Botones 'coloreados'
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20;
    // Para los colores:
    private List<Integer> colores;
    private List <Button> botonesArray;
    private String [] textosColores;
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
    // Para mostrar Dialog y pasar parámetros:
    private Dialog dialogSeleccion;
    private Context contextoActual;
    private int colorSeleccionado;
    private Button bAceptar, bCancelar;

    public interface finalizarDialogColores {
        void cogerColor(String seleccion);
    }

    private finalizarDialogColores interfazDialogString;

    public DialogoSeleccionarColorPersonalizado (Context contexto, finalizarDialogColores actividadDialog) {
        interfazDialogString = actividadDialog;
        contextoActual = contexto;

        definicionesVariables();
        insertarBotonesArray();
        insertarColoresArrayList();
        cambiarBackgroundColorBotones();
        onClickListener();
        realizarAcciones();
    }

    private void definicionesVariables() {
        dialogSeleccion = new Dialog(contextoActual);
        dialogSeleccion.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSeleccion.setContentView(R.layout.selector_colores);

        colores = new ArrayList<>();
        botonesArray = new ArrayList<>();

        btn1 = dialogSeleccion.findViewById(R.id.b1);
        btn2 = dialogSeleccion.findViewById(R.id.b2);
        btn3 = dialogSeleccion.findViewById(R.id.b3);
        btn4 = dialogSeleccion.findViewById(R.id.b4);
        btn5 = dialogSeleccion.findViewById(R.id.b5);
        btn6 = dialogSeleccion.findViewById(R.id.b6);
        btn7 = dialogSeleccion.findViewById(R.id.b7);
        btn8 = dialogSeleccion.findViewById(R.id.b8);
        btn9 = dialogSeleccion.findViewById(R.id.b9);
        btn10 = dialogSeleccion.findViewById(R.id.b10);
        btn11 = dialogSeleccion.findViewById(R.id.b11);
        btn12 = dialogSeleccion.findViewById(R.id.b12);
        btn13 = dialogSeleccion.findViewById(R.id.b13);
        btn14 = dialogSeleccion.findViewById(R.id.b14);
        btn15 = dialogSeleccion.findViewById(R.id.b15);
        btn16 = dialogSeleccion.findViewById(R.id.b16);
        btn17 = dialogSeleccion.findViewById(R.id.b17);
        btn18 = dialogSeleccion.findViewById(R.id.b18);
        btn19 = dialogSeleccion.findViewById(R.id.b19);
        btn20 = dialogSeleccion.findViewById(R.id.b20);

        layoutB1 = dialogSeleccion.findViewById(R.id.layoutB1);
        layoutB2 = dialogSeleccion.findViewById(R.id.layoutB2);
        layoutB3 = dialogSeleccion.findViewById(R.id.layoutB3);
        layoutB4 = dialogSeleccion.findViewById(R.id.layoutB4);
        layoutB5 = dialogSeleccion.findViewById(R.id.layoutB5);
        layoutB6 = dialogSeleccion.findViewById(R.id.layoutB6);
        layoutB7 = dialogSeleccion.findViewById(R.id.layoutB7);
        layoutB8 = dialogSeleccion.findViewById(R.id.layoutB8);
        layoutB9 = dialogSeleccion.findViewById(R.id.layoutB9);
        layoutB10 = dialogSeleccion.findViewById(R.id.layoutB10);
        layoutB11 = dialogSeleccion.findViewById(R.id.layoutB11);
        layoutB12 = dialogSeleccion.findViewById(R.id.layoutB12);
        layoutB13 = dialogSeleccion.findViewById(R.id.layoutB13);
        layoutB14 = dialogSeleccion.findViewById(R.id.layoutB14);
        layoutB15 = dialogSeleccion.findViewById(R.id.layoutB15);
        layoutB16 = dialogSeleccion.findViewById(R.id.layoutB16);
        layoutB17 = dialogSeleccion.findViewById(R.id.layoutB17);
        layoutB18 = dialogSeleccion.findViewById(R.id.layoutB18);
        layoutB19 = dialogSeleccion.findViewById(R.id.layoutB19);
        layoutB20 = dialogSeleccion.findViewById(R.id.layoutB20);

        bAceptar = dialogSeleccion.findViewById(R.id.bAceptarSelectorColor);
        bCancelar = dialogSeleccion.findViewById(R.id.bCancelarSelectorColor);
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

        textosColores = contextoActual.getResources().getStringArray(R.array.arrayColores);
    }

    private void onClickListener() {

         btn1.setOnClickListener(this);
         btn2.setOnClickListener(this);
         btn3.setOnClickListener(this);
         btn4.setOnClickListener(this);
         btn5.setOnClickListener(this);
         btn6.setOnClickListener(this);
         btn7.setOnClickListener(this);
         btn8.setOnClickListener(this);
         btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);
        btn19.setOnClickListener(this);
        btn20.setOnClickListener(this);
    }

    private void cambiarBackgroundColorBotones() {
        for (int i=0; i<botonesArray.size(); i++) {
            botonesArray.get(i).getBackground().setColorFilter( colores.get(i), PorterDuff.Mode.SRC);
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

    private void realizarAcciones() {
        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfazDialogString.cogerColor(textosColores[colorSeleccionado]);
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
            // Cambiamos el color al layout, al pulsar el botón para que el usuario, vea que lo ha pulsado:
            case R.id.b1:
                colorSeleccionado = 0;
                quitarBordesLayout();
                layoutB1.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b2:
                colorSeleccionado = 1;
                quitarBordesLayout();
                layoutB2.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b3:
                colorSeleccionado = 2;
                quitarBordesLayout();
                layoutB3.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b4:
                colorSeleccionado = 3;
                quitarBordesLayout();
                layoutB4.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b5:
                colorSeleccionado = 4;
                quitarBordesLayout();
                layoutB5.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b6:
                colorSeleccionado = 5;
                quitarBordesLayout();
                layoutB6.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b7:
                colorSeleccionado = 6;
                quitarBordesLayout();
                layoutB7.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b8:
                colorSeleccionado = 7;
                quitarBordesLayout();
                layoutB8.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b9:
                colorSeleccionado = 8;
                quitarBordesLayout();
                layoutB9.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b10:
                colorSeleccionado = 9;
                quitarBordesLayout();
                layoutB10.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b11:
                colorSeleccionado = 10;
                quitarBordesLayout();
                layoutB11.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b12:
                colorSeleccionado = 11;
                quitarBordesLayout();
                layoutB12.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b13:
                colorSeleccionado = 12;
                quitarBordesLayout();
                layoutB13.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b14:
                colorSeleccionado = 13;
                quitarBordesLayout();
                layoutB14.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b15:
                colorSeleccionado = 14;
                quitarBordesLayout();
                layoutB15.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b16:
                colorSeleccionado = 15;
                quitarBordesLayout();
                layoutB16.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b17:
                colorSeleccionado = 16;
                quitarBordesLayout();
                layoutB17.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b18:
                colorSeleccionado = 17;
                quitarBordesLayout();
                layoutB18.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b19:
                colorSeleccionado = 18;
                quitarBordesLayout();
                layoutB19.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            case R.id.b20:
                colorSeleccionado = 19;
                quitarBordesLayout();
                layoutB20.setBackground(contextoActual.getResources().getDrawable(R.drawable.layout_border));
                break;
            default:
                // Por defecto
                break;
        }
    }


}
