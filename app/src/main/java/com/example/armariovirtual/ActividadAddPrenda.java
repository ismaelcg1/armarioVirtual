package com.example.armariovirtual;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ActividadAddPrenda extends AppCompatActivity implements DialogPropiedadesPrenda.acabarDialog, View.OnClickListener,
        DialogoSeleccionarEstacionPersonalizado.finalizarDialog, DialogoSeleccionarColorPersonalizado.finalizarDialogColores {

    private ImageView botonInsertarImagen;
    private int GALLERY = 1, CAMERA = 2;
    private Toolbar appToolbar;
    private Context contexto;

    private Button subirPrenda, btnTalla, btnEpoca, btnColor, btnCategoria, btnEstilo, btnSubcategoria, btnCantidadPrendas, btnMarca;
    private TextInputLayout textoTalla, textoEpoca, textoColor, textoCategoria, textoEstilo, textoSubcategoria, textoCantidadPrendas, textoMarca;
    private String seleccion, talla, epoca, color, estilo , categoria, subcategoria, imagenConvertida, marca, usuarioActual;
    private int cantidadDePrendas;
    // Para almacenar la foto de la prenda
    private Bitmap imagenFoto;
    private Bitmap bitmapRedimensionado;
    // FireBase
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_insertar);
        definicionDeVariables();
        onClickListener();

        // Pongo el titulo en la toolbar
        appToolbar.setTitle(R.string.nombreInicialActividadInsertar);
        // Asigno la flecha de atras a la toolbar
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
        // Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestMultiplePermissions();
    }

    protected void definicionDeVariables() {
        botonInsertarImagen = findViewById(R.id.imageViewInsertarImagen);
        appToolbar = findViewById(R.id.appToolbar);
        subirPrenda = findViewById(R.id.bSubirPrenda);
        btnTalla = findViewById(R.id.bTalla);
        btnEpoca = findViewById(R.id.bEpoca);
        btnColor = findViewById(R.id.bColor);
        btnCategoria = findViewById(R.id.bCategoria);
        btnEstilo = findViewById(R.id.bEstilo);
        btnSubcategoria = findViewById(R.id.bSubcategoria);
        btnCantidadPrendas = findViewById(R.id.bCantidad);
        btnMarca = findViewById(R.id.bMarca);

        textoTalla = findViewById(R.id.tallaPrenda);
        textoEpoca = findViewById(R.id.epocaPrenda);
        textoColor = findViewById(R.id.colorPrenda);
        textoCategoria = findViewById(R.id.categoriaPrenda);
        textoEstilo = findViewById(R.id.estiloPrenda);
        textoSubcategoria = findViewById(R.id.subcategoriaPrenda);
        textoCantidadPrendas = findViewById(R.id.cantidadPrendas);
        textoMarca = findViewById(R.id.marcaPrenda);

        contexto = this;

        epoca = "";
        color = "";
        estilo = "";
        talla = "";
        categoria = "";
        subcategoria = "";
        imagenConvertida = "";
        cantidadDePrendas = 1;
        marca = "";
    }

    private void onClickListener() {
        btnTalla.setOnClickListener(this);
        btnEstilo.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnEpoca.setOnClickListener(this);
        btnCategoria.setOnClickListener(this);
        btnSubcategoria.setOnClickListener(this);
        btnCantidadPrendas.setOnClickListener(this);
        btnMarca.setOnClickListener(this);
        botonInsertarImagen.setOnClickListener(this);
        subirPrenda.setOnClickListener(this);
    }


    private void showPictureDialog(){
        final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(getResources().getString(R.string.pictureDialogTituloActividadInsertar));
        String[] pictureDialogItems = {
                getResources().getString(R.string.pictureDialogGaleriaActividadInsertar),
                getResources().getString(R.string.pictureDialogRealizarFotoActividadInsertar)};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    imagenFoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmapRedimensionado = Bitmap.createScaledBitmap(imagenFoto, botonInsertarImagen.getWidth(), botonInsertarImagen.getHeight(), false);

                    Toast.makeText(ActividadAddPrenda.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    botonInsertarImagen.setImageBitmap(bitmapRedimensionado);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ActividadAddPrenda.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            imagenFoto = (Bitmap) data.getExtras().get("data");
            bitmapRedimensionado = Bitmap.createScaledBitmap(imagenFoto, botonInsertarImagen.getWidth(), botonInsertarImagen.getHeight(), false);
            botonInsertarImagen.setImageBitmap(bitmapRedimensionado);

            Toast.makeText(ActividadAddPrenda.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        // Redimensionamos la imagen para guardarla posteriormente y que no ocupe tanto espacio:
        bitmapRedimensionado = redimensionarImagen(imagenFoto, 240, 290);
        imagenConvertida = convertirImgString(bitmapRedimensionado);
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }
    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }


    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.permisosAceptadosActividadInsertar), Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onClick(View v) {
        seleccion = "";
        switch (v.getId()) {
            case R.id.bTalla:
                seleccion = "Talla";
                new DialogPropiedadesPrenda(ActividadAddPrenda.this, ActividadAddPrenda.this, seleccion, null);
                break;
            case R.id.bEpoca:
                new DialogoSeleccionarEstacionPersonalizado(ActividadAddPrenda.this, ActividadAddPrenda.this);
                break;
            case R.id.bColor:
                new DialogoSeleccionarColorPersonalizado(ActividadAddPrenda.this, ActividadAddPrenda.this);
                break;
            case R.id.bEstilo:
                seleccion = "Estilo";
                new DialogPropiedadesPrenda(ActividadAddPrenda.this, ActividadAddPrenda.this, seleccion, null);
                break;
            case R.id.bCategoria:
                seleccion = "Categoria";
                new DialogPropiedadesPrenda(ActividadAddPrenda.this, ActividadAddPrenda.this, seleccion, null);
                textoSubcategoria.getEditText().setText("");
                break;
            case R.id.bSubcategoria:
                if (categoria.isEmpty()) {
                    Toast.makeText(ActividadAddPrenda.this, getResources().getString(R.string.subcategoriaErrorActividadInsertar), Toast.LENGTH_LONG).show();
                } else {
                    seleccion = "Subcategoria";
                    // Dependiendo de la Categoría elegida, mostrar unas opciones u otra de subcategoría
                    new DialogPropiedadesPrenda(ActividadAddPrenda.this, ActividadAddPrenda.this, seleccion, categoria);
                }
                break;
            case R.id.bCantidad:
                seleccion = "Cantidad";
                new DialogPropiedadesPrenda(ActividadAddPrenda.this, ActividadAddPrenda.this, seleccion, null);
                break;
            case R.id.bMarca:
                seleccion = "Marca";
                new DialogPropiedadesPrenda(ActividadAddPrenda.this, ActividadAddPrenda.this, seleccion, null);
                break;
            case R.id.bSubirPrenda:
                if (!comprobarDatosVacios()) {
                    ServidorPHP objeto = new ServidorPHP();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    marca = "Nike";
                    objeto.cargarWebService(user.getUid(), contexto, talla, estilo, color, epoca, categoria, subcategoria, cantidadDePrendas, marca, imagenConvertida);
                }

                break;
            case R.id.imageViewInsertarImagen:
                showPictureDialog();
                break;
        }
    }

    /*

    private void insertarPrenda() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        ServidorPHP objeto = new ServidorPHP();
        // Tambien pruebas
        marca = "Nike";
        boolean pruebas = false;
        try {
            pruebas = objeto.insertarPrenda(talla, estilo, color, epoca, categoria, subcategoria, cantidadDePrendas, marca,
                    imagenConvertida, false, user.getUid() );

        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        // Pruebas
        Toast.makeText(ActividadAddPrenda.this,"Resultado consulta: "+pruebas, Toast.LENGTH_LONG).show();
    }
    */

    private boolean comprobarDatosVacios() {
        boolean algunDatoVacio = false;

        if (categoria.isEmpty()) {
            textoCategoria.getEditText().setError(getResources().getString(R.string.faltaCategoriaActividadInsertar));
            algunDatoVacio = true;
        } else {
            textoCategoria.getEditText().setError(null);
        }

        if (subcategoria.isEmpty()) {
            textoSubcategoria.getEditText().setError(getResources().getString(R.string.faltaSubcategoriaActividadInsertar));
            algunDatoVacio = true;
        } else {
            textoSubcategoria.getEditText().setError(null);
        }

        if (talla.isEmpty()) {
            textoTalla.getEditText().setError(getResources().getString(R.string.faltaTallaActividadInsertar));
            algunDatoVacio = true;
        } else {
            textoTalla.getEditText().setError(null);
        }

        if (estilo.isEmpty()) {
            textoEstilo.getEditText().setError(getResources().getString(R.string.faltaEstiloActividadInsertar));
            algunDatoVacio = true;
        } else {
            textoEstilo.getEditText().setError(null);
        }

        if (color.isEmpty()) {
            textoColor.getEditText().setError(getResources().getString(R.string.faltaColorActividadInsertar));
            algunDatoVacio = true;
        } else {
            textoColor.getEditText().setError(null);
        }

        if (epoca.isEmpty()) {
            textoEpoca.getEditText().setError(getResources().getString(R.string.faltaEpocaActividadInsertar));
            algunDatoVacio = true;
        } else {
            textoEpoca.getEditText().setError(null);
        }

        if (imagenConvertida.isEmpty()) {
            algunDatoVacio = true;
            Toast.makeText(ActividadAddPrenda.this,
                    getResources().getString(R.string.faltaImagenActividadInsertar), Toast.LENGTH_LONG).show();
        }

        return algunDatoVacio;
    }


    @Override
    public void cogerParametro(String seleccionado) {

        switch (seleccion) {
            case "Talla":
                talla = seleccionado;
                textoTalla.getEditText().setText(talla);
                break;
            case "Estilo":
                estilo = seleccionado;
                textoEstilo.getEditText().setText(estilo);
                break;
            case "Categoria":
                categoria = seleccionado;
                textoCategoria.getEditText().setText(categoria);
                break;
            case "Subcategoria":
                subcategoria = seleccionado;
                textoSubcategoria.getEditText().setText(subcategoria);
                break;
            case "Cantidad":
                cantidadDePrendas = Integer.parseInt(seleccionado);
                textoCantidadPrendas.getEditText().setText(seleccionado); // Porque en este caso sería el nº en String
                break;
            default:
                // Por defecto
                break;
        }
    }

    @Override
    public void cogerString(String seleccion) {
        epoca = seleccion;
        textoEpoca.getEditText().setText(epoca);
    }

    @Override
    public void cogerColor(String seleccion) {
        color = seleccion;
        textoColor.getEditText().setText(color);
    }
}
