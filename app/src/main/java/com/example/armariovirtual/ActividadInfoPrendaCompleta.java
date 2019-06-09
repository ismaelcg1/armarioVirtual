package com.example.armariovirtual;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ActividadInfoPrendaCompleta extends AppCompatActivity implements View.OnClickListener{

    private Toolbar appToolbar;
    private TextView tvCantidad, tvTalla, tvEstilo, tvColor, tvEpoca, tvCategoria, tvSubcategoria, tvEstado, tvMarca;
    private ImageView imagenPrendaActual;
    private Switch switchIntercambio;
    private int idPrendaSeleccionada, estadoIntercambio;
    private FirebaseUser user;
    private String uidUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_toda_informacion_prenda);

        inicializarVariablesConVista();
        switchIntercambio.setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uidUsuario = user.getUid();

        // Cogemos los datos pasados al intent
        Intent intentActividadActual = getIntent();
        Bundle b = intentActividadActual.getExtras();

        if(b!=null)
        {
            idPrendaSeleccionada =(int) b.get("idPrenda");
            String tallaPrendaSeleccionada =(String) b.get("tallaPrenda");
            String estiloPrendaSeleccionada =(String) b.get("estiloPrenda");
            String colorPrendaSeleccionada =(String) b.get("colorPrenda");
            String epocaPrendaSeleccionada =(String) b.get("epocaPrenda");
            String categoriaPrendaSeleccionada =(String) b.get("categoriaPrenda");
            String subcategoriaPrendaSeleccionada =(String) b.get("subcategoriaPrenda");
            boolean estadoPrendaSeleccionada =(boolean) b.get("estadoPrenda");
            Bitmap imagenPrendaSeleccionada =(Bitmap) b.get("imagenPrenda");
            int cantidadPrendaSeleccionada =(int) b.get("cantidadPrenda");
            String marcaPrendaSeleccionada = (String) b.get("marcaPrenda");
            estadoIntercambio =(int) b.get("estado_intercambio");

            tvTalla.setText(tallaPrendaSeleccionada);
            tvEstilo.setText(estiloPrendaSeleccionada);
            tvColor.setText(colorPrendaSeleccionada);
            tvEpoca.setText(epocaPrendaSeleccionada);
            tvCategoria.setText(categoriaPrendaSeleccionada);
            tvSubcategoria.setText(subcategoriaPrendaSeleccionada);
            tvCantidad.setText(""+cantidadPrendaSeleccionada);
            tvMarca.setText(marcaPrendaSeleccionada);
            imagenPrendaActual.setImageBitmap(imagenPrendaSeleccionada);

            if (estadoPrendaSeleccionada) {
                tvEstado.setText("Limpio");
            } else {
                tvEstado.setText("Sucio");
            }

            if (estadoIntercambio == 0) {
                switchIntercambio.setChecked(false);
            } else {
                switchIntercambio.setChecked(true);
            }
        }
        // Toobar
        appToolbar.setTitle(R.string.nombreActividadInfoPrendaCompleta);
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void inicializarVariablesConVista() {
        appToolbar = findViewById(R.id.appToolbar);
        tvCantidad = findViewById(R.id.cantidadPrendaVisualizada);
        tvTalla = findViewById(R.id.tallaPrendaVisualizada);
        tvEstilo = findViewById(R.id.estiloPrendaVisualizada);
        tvColor = findViewById(R.id.colorPrendaVisualizada);
        tvEpoca = findViewById(R.id.epocaPrendaVisualizada);
        tvCategoria = findViewById(R.id.categoriaPrendaVisualizada);
        tvSubcategoria = findViewById(R.id.subcategoriaPrendaVisualizada);
        tvEstado = findViewById(R.id.estadoPrendaVisualizada);
        imagenPrendaActual = findViewById(R.id.imagenPrendaDetallada);
        tvMarca = findViewById(R.id.marcaPrendaVisualizada);
        switchIntercambio = findViewById(R.id.switchIntercambio);
    }


    @Override
    public void onClick(View v) {
        int opcion = 0;
        switch (v.getId()) {
            case R.id.switchIntercambio:
                if (estadoIntercambio == 0) {
                    crearDialog(getResources().getString(R.string.tituloDialogIntercambio),
                            getResources().getString(R.string.subtituloDialogIntercambio),opcion);
                } else {
                    opcion = 1;
                    crearDialog(getResources().getString(R.string.tituloDialogIntercambio),
                            getResources().getString(R.string.subtituloDialogIntercambio),opcion);
                }
                break;

            case R.id.switchEstado:

                break;
        }
    }

    public void crearDialog(String titulo, String mensaje, final int opcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ServidorPHP objetoServidor = new ServidorPHP();

        builder.setMessage(mensaje)
                .setTitle(titulo);
        builder.setPositiveButton(R.string.dialogTextoSiMiCuenta, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Actualizar la tabla prendas_usuarios pasando el id del usuario y el id de la prenda
                if (opcion == 0) {
                    int valor = 1;
                        try {
                            objetoServidor.actualizarIntercambio(uidUsuario, idPrendaSeleccionada, valor);
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }
                } else {
                    int valor = 0;
                        try {
                            objetoServidor.actualizarIntercambio(uidUsuario, idPrendaSeleccionada, valor);
                        } catch (ServidorPHPException e) {
                            e.printStackTrace();
                        }
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.dialogTextoCancelarMiCuenta, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (opcion == 0) {
                    switchIntercambio.setChecked(false);
                } else {
                    switchIntercambio.setChecked(true);
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        builder.show();
    }
}
