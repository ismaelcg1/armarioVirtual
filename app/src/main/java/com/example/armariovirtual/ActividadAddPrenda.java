package com.example.armariovirtual;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.IOException;
import java.util.List;

public class ActividadAddPrenda extends AppCompatActivity implements DialogPropiedadesPrenda.acabarDialog, View.OnClickListener,
        DialogoSeleccionarEstacionPersonalizado.finalizarDialog, DialogoSeleccionarColorPersonalizado.finalizarDialogColores {

    private ImageView botonInsertarImagen;
    private static final String IMAGE_DIRECTORY = "/imagenes_Armario_Virtual";
    private int GALLERY = 1, CAMERA = 2;
    private Toolbar appToolbar;

    private Button subirPrenda, btnTalla, btnEpoca, btnColor, btnCategoria, btnEstilo, btnSubcategoria;
    private TextInputLayout textoTalla, textoEpoca, textoColor, textoCategoria, textoEstilo, textoSubcategoria;
    private String seleccion, talla, estacion, color, estilo , categoria, subcategoria;
    // Para almacenar la foto de la prenda
    private Bitmap imagenFoto;

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

        botonInsertarImagen = findViewById(R.id.imageViewInsertarImagen);
        subirPrenda = findViewById(R.id.bSubirPrenda);


        botonInsertarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }


    private void onClickListener() {
        btnTalla.setOnClickListener(this);
        btnEstilo.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnEpoca.setOnClickListener(this);
        btnCategoria.setOnClickListener(this);
        btnSubcategoria.setOnClickListener(this);
    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
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

    protected void definicionDeVariables() {
        appToolbar = findViewById(R.id.appToolbar);
        subirPrenda = findViewById(R.id.bSubirPrenda);
        btnTalla = findViewById(R.id.bTalla);
        btnEpoca = findViewById(R.id.bEpoca);
        btnColor = findViewById(R.id.bColor);
        btnCategoria = findViewById(R.id.bCategoria);
        btnEstilo = findViewById(R.id.bEstilo);
        btnSubcategoria = findViewById(R.id.bSubcategoria);

        textoTalla = findViewById(R.id.tallaPrenda);
        textoEpoca = findViewById(R.id.epocaPrenda);
        textoColor = findViewById(R.id.colorPrenda);
        textoCategoria = findViewById(R.id.categoriaPrenda);
        textoEstilo = findViewById(R.id.estiloPrenda);
        textoSubcategoria = findViewById(R.id.subcategoriaPrenda);

        estacion = "";
        color = "";
        estilo = "";
        categoria = "";
        subcategoria = "";
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
                    Bitmap bitmapaux = Bitmap.createScaledBitmap(imagenFoto, botonInsertarImagen.getWidth(), botonInsertarImagen.getHeight(), false);

                    Toast.makeText(ActividadAddPrenda.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    botonInsertarImagen.setImageBitmap(bitmapaux);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ActividadAddPrenda.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Bitmap bitmap = Bitmap.createScaledBitmap(thumbnail, botonInsertarImagen.getWidth(), botonInsertarImagen.getHeight(), false);
            botonInsertarImagen.setImageBitmap(bitmap);

            Toast.makeText(ActividadAddPrenda.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

        // TODO guardar Imagen

    }

    /* PARA GUARDAR LA IMAGEN EN EL DISPOSITIVO
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

*/
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
            case R.id.bSubirPrenda:
                // TODO porque no van los errores?
                textoTalla.setError("Talla requerida");
                break;
        }
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
            default:
                // Por defecto
                break;
        }
    }

    @Override
    public void cogerString(String seleccion) {
        estacion = seleccion;
        textoEpoca.getEditText().setText(estacion);
    }

    @Override
    public void cogerColor(String seleccion) {
        color = seleccion;
        textoColor.getEditText().setText(color);
    }
}
