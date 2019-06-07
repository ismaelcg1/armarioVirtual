package com.example.armariovirtual;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MicrophoneInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MiCuenta extends AppCompatActivity implements View.OnClickListener, DialogPersonalizadoPesoAltura.finalizarDialog,
                                                           DialogPropiedadesPrenda.acabarDialog, DialogoReautentificacion.obtenerPasswordRe {
    private Toolbar appToolbar;
    private Button bCerrarSesion, bCambiarPassword, bEliminarArmario, bEliminarCuenta, bAltura, bPeso, bTalla;
    private TextView emailUser, alturaUsuario, pesoUsuario, tallaUsuario;
    private String email, talla, seleccion, seleccionado;
    // FireBase
    private FirebaseAuth mAuth = null;
    private FirebaseUser user;
    // Ponemos el contexto para el Dialog
    private Context contexto;
    private Usuario usuarioActual;
    private int peso;
    private double altura;
    private boolean cambiosRealizados = false;
    private ImageView imagenDelSexoUsuario;
    // Para reautentificación
    private String passwordIntroducida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_cuenta);
        contexto = this;
        conectarVariablesConVista();
        onClickListener();
        ponerEmailUsuario();
        cogerUsuarioActual();
        incluirVariablesMostradas();
        inicializarToolbar();
        verSexoUsuario();
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiosRealizados) actualizarDatosBBDD();
                finish();
            }
        });
    }

    private void verSexoUsuario() {
        Sexo objetoSexo = null;
        if (usuarioActual.getSexo() == objetoSexo.Femenino) {
            imagenDelSexoUsuario.setImageResource(R.drawable.usuario_femenino80);
        } else {
            imagenDelSexoUsuario.setImageResource(R.drawable.usuario_masculino80);
        }
    }

    private void actualizarDatosBBDD() {

        ServidorPHP objetoServidor = new ServidorPHP();
        Boolean todoCorrecto = false;
        int alturaInt = (int)( altura * 100);
        try {
            todoCorrecto = objetoServidor.actualizarUsuario(user.getUid(), alturaInt, peso, talla);
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
        if (!todoCorrecto) {
            Toast.makeText(MiCuenta.this, R.string.falloActualizarBBDDMiCuenta, Toast.LENGTH_SHORT).show();
        }
    }

    private void cogerUsuarioActual() {
        Bundle objetoRecibido = getIntent().getExtras();
        usuarioActual = null;
        if (objetoRecibido != null) {
            usuarioActual = (Usuario) objetoRecibido.getSerializable("usuarioConectado");
        }
    }

    private void incluirVariablesMostradas() {
        altura = usuarioActual.getAltura();
        altura = altura / 100;
        String alturaMostrada = String.valueOf(altura);
        peso = usuarioActual.getPeso();
        alturaUsuario.setText(getResources().getString(R.string.alturaMiCuenta, alturaMostrada));
        pesoUsuario.setText(getResources().getString(R.string.pesoMiCuenta, peso));
        // Ahora obtenemos la talla:
        talla = usuarioActual.getTallaPorDefecto();
        tallaUsuario.setText(getResources().getString(R.string.tallaMiCuenta, talla));
    }

    private void inicializarToolbar() {
        appToolbar.setTitle(R.string.nombreActividadMiCuenta);
        // Asigno la flecha atras
        appToolbar.setNavigationIcon(R.drawable.atras_34dp);
    }

    private void updateUI(int id) {
        switch (id) {
            case 1:
                // Borramos todas las actividades y abrimos la primera
                Intent intentLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                intentLoginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivityDrawer pantallaDrawer = new MainActivityDrawer();
                pantallaDrawer.finish();
                finish();
                startActivity(intentLoginActivity);
                break;

            default:

                break;
        }
    }

    protected void conectarVariablesConVista() {
        bAltura = findViewById(R.id.bAlturaMiCuenta);
        bPeso = findViewById(R.id.bPesoMiCuenta);
        bTalla = findViewById(R.id.bTallaMiCuenta);

        bCerrarSesion = findViewById(R.id.btnCerrarSesion);
        appToolbar = findViewById(R.id.appToolbar);
        bCambiarPassword = findViewById(R.id.btnCambiarPassword);
        bEliminarArmario = findViewById(R.id.btnEliminarArmario);
        bEliminarCuenta = findViewById(R.id.btnEliminarCuenta);
        emailUser = findViewById(R.id.tvEmailUsuario);
        alturaUsuario = findViewById(R.id.textoAlturaUsuario);
        pesoUsuario = findViewById(R.id.textoPesoUsuario);
        tallaUsuario = findViewById(R.id.textoTallaUsuario);

        imagenDelSexoUsuario = findViewById(R.id.ivMiCuenta);
    }

    protected void onClickListener() {
        bAltura.setOnClickListener(this);
        bPeso.setOnClickListener(this);
        bTalla.setOnClickListener(this);

        bCerrarSesion.setOnClickListener(this);
        bCambiarPassword.setOnClickListener(this);
        bEliminarArmario.setOnClickListener(this);
        bEliminarCuenta.setOnClickListener(this);
    }

    private void ponerEmailUsuario() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            // Mostramos el email al usuario:
            emailUser.setText(email);
        }
    }

    // Salir de la sesión
    private void signOut(int id) {
        // FireBase
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        updateUI(id);

        // TODO Eliminar intent de MainDrawerActivity
        MainActivityDrawer ob = new MainActivityDrawer();
        ob.finish();
    }

    private void sendPasswordReset(final String email) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MiCuenta.this,
                                    getResources().getString(R.string.cambioPasswordActividadLogin)+" "+email, Toast.LENGTH_LONG).show();
                        } else {
                            // falloCambioPasswordActividadLogin
                            Toast.makeText(MiCuenta.this,
                                    getResources().getString(R.string.falloCambioPasswordActividadLogin), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void crearDialog(String titulo, String mensaje, final int opcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Ponemos el mensaje y el título:
        builder.setMessage(mensaje)
                .setTitle(titulo);
        builder.setPositiveButton(R.string.dialogTextoSiMiCuenta, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (opcion == 1) {
                    signOut(opcion);
                } else if (opcion == 2) {
                    sendPasswordReset(email);
                } else if (opcion == 3) {
                    eliminarMiArmario(false);
                } else if (opcion == 4){
                    new DialogoReautentificacion(MiCuenta.this, MiCuenta.this);
                } else {

                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.dialogTextoCancelarMiCuenta, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        builder.show();
    }

    private void eliminarMiArmario(Boolean deEliminarCuenta) {
        Boolean borrado = true;
        ServidorPHP objetoServidor = new ServidorPHP();
        try {
            borrado = objetoServidor.eliminarArmario(user.getUid());
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

        if (!deEliminarCuenta) {
            Toast.makeText(MiCuenta.this, getResources().getString(R.string.eliminarArmarioCorrectoMiCuenta),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void eliminarUsuario() {
        final Boolean actividadEliminar = true;
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            eliminarMiArmario(actividadEliminar);
                            // Eliminamos el usuario en nuestra BBDD
                            ServidorPHP objetoPHP = new ServidorPHP();
                            try {
                                objetoPHP.eliminarUsuario(user.getUid());
                            } catch (ServidorPHPException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(MiCuenta.this, getResources().getString(R.string.eliminarCuentaCorrectoMiCuenta),
                                    Toast.LENGTH_LONG).show();
                            finish();
                            Intent intentLoginActivity = new Intent(MiCuenta.this, LoginActivity.class);
                            startActivity(intentLoginActivity);
                        } else {
                            Toast.makeText(MiCuenta.this, getResources().getString(R.string.eliminarCuentaFalloMiCuenta),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void reAutentificacion() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), passwordIntroducida);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Usuario re-autentificado
                        eliminarUsuario();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        int opcion;

        switch (v.getId()) {
            case R.id.btnCerrarSesion:
                opcion = 1;
                crearDialog(getResources().getString(R.string.cerrarSesionTituloDialogMiCuenta),
                        getResources().getString(R.string.cerrarSesionDialogMiCuenta),opcion);
                break;

            case R.id.btnCambiarPassword:
                opcion = 2;
                crearDialog(getResources().getString(R.string.cambiarPasswordTituloDialogMiCuenta),
                        getResources().getString(R.string.cambiarPasswordDialogMiCuenta, email),opcion);
                break;

            case R.id.btnEliminarArmario:
                opcion = 3;
                crearDialog(getResources().getString(R.string.eliminarArmarioTituloDialogMiCuenta),
                        getResources().getString(R.string.eliminarArmarioDialogMiCuenta),opcion);
                break;

            case R.id.btnEliminarCuenta:
                opcion = 4;
                crearDialog(getResources().getString(R.string.eliminarCuentaTituloDialogMiCuenta),
                        getResources().getString(R.string.eliminarCuentaDialogMiCuenta, email),opcion);
                break;

            case R.id.bAlturaMiCuenta:
                seleccion = "Altura";
                new DialogPersonalizadoPesoAltura(contexto, MiCuenta.this, seleccion);
                usuarioActual.setAltura((int) altura);
                break;

            case R.id.bPesoMiCuenta:
                seleccion = "Peso";
                new DialogPersonalizadoPesoAltura(contexto, MiCuenta.this, seleccion);
                usuarioActual.setPeso(peso);
                break;

            case R.id.bTallaMiCuenta:
                seleccionado = "Talla";
                new DialogPropiedadesPrenda(MiCuenta.this, MiCuenta.this, seleccionado, null);
                break;
        }
    }

    @Override
    public void resultado(int num) {
        if (seleccion.equalsIgnoreCase("Altura")) {
            altura = Double.valueOf(num) / 100;
            String alturaMostrada = String.valueOf(altura);
            alturaUsuario.setText(getResources().getString(R.string.alturaMiCuenta, alturaMostrada));
            Toast.makeText(MiCuenta.this, getResources().getString(R.string.toastCambiadaAlturaRegistroIncial),
                            Toast.LENGTH_LONG).show();
            cambiosRealizados = true;
        } else if (seleccion.equalsIgnoreCase("Peso")) {
            peso = num;
            pesoUsuario.setText(getResources().getString(R.string.pesoMiCuenta, num));
            Toast.makeText(MiCuenta.this, getResources().getString(R.string.toastCambiadoPesoRegistroIncial),
                            Toast.LENGTH_LONG).show();
            cambiosRealizados = true;
        }
    }

    @Override
    public void cogerParametro(String seleccion) {
        if (seleccionado.equalsIgnoreCase("Talla")) {
            talla = seleccion;
            tallaUsuario.setText(getResources().getString(R.string.tallaMiCuenta, talla));
            Toast.makeText(MiCuenta.this, getResources().getString(R.string.toastCambiadaTallaRegistroIncial),
                            Toast.LENGTH_LONG).show();
            cambiosRealizados = true;
        }
    }

    @Override
    public void cogerString(String seleccion) {
        passwordIntroducida = seleccion;
        reAutentificacion();
    }
}
