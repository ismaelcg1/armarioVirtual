package com.example.armariovirtual;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Ciclo formativo: Desarrollo de Aplicaciones Multiplataforma
 * Proyecto final DAM
 * Alumno: Ismael Casado González
 * Curso académico: 2018-2019
 * Instituto Tecnológico Poniente
 */
public class MainActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private String nombreUsuarioString;
    private TextView nombreUser, prendasAdd;
    // Para mostrar las prendas añadidos
    private int prendas;
    // Creamos el intent de la nueva actividad
    private Intent intent;
    private Button btnVerArmario;
    private Usuario userActual;
    private ImageView imagenDrawerInicial;
    private FirebaseUser user;
    private ServidorPHP objetoServidor;
    protected static final String UID_USUARIO_KEY = "UID";
    protected static final String NUMERO_PRENDAS = "CANTIDAD_PRENDAS";
    protected static final String SEXO_USUARIO = "SEXO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        conectarVariablesConVista();
        cogerUsuarioActual();
        inicializarVariables();
        btnVerArmario.setOnClickListener(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO aqui actualizamos el objeto usuario ¿se puede hacer sólo si venimos de la actividad MiCuenta???
        actualizarDatosUsuario();
        prendas = obtenerPrendas();
        prendasAdd.setText( getResources().getString(R.string.prendasAnadidasMainActivityDrawer, prendas ) );
    }

    private void actualizarDatosUsuario() {

        ServidorPHP objetoServidor = new ServidorPHP();
        Usuario usuarioObtenido = null;

        try {
            usuarioObtenido = objetoServidor.obtenerUsuario(user.getUid());
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }

        if (usuarioObtenido == null) {
            Toast.makeText(MainActivityDrawer.this, getResources().getString(R.string.toastObtenerUsuarioFalloActividadLogin), Toast.LENGTH_SHORT).show();
        } else {
            userActual = usuarioObtenido;
        }
    }

    private void verSexoUsuario() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        imagenDrawerInicial = hView.findViewById(R.id.imagenInicialUsuario);
        Sexo objetoSexo = null;
        if (userActual.getSexo() == objetoSexo.Femenino) {
            imagenDrawerInicial.setImageResource(R.drawable.usuario_femenino80);
        } else {
            imagenDrawerInicial.setImageResource(R.drawable.usuario_masculino80);
        }
    }

    private void cogerUsuarioActual() {
        Bundle objetoRecibido = getIntent().getExtras();
        userActual = null;
        if (objetoRecibido != null) {
            userActual = (Usuario) objetoRecibido.getSerializable("usuarioActual");
        }
        // Cambiamos el saludo inicial:
        if (userActual != null) {
            nombreUsuarioString = userActual.getNickName();
            verSexoUsuario();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @SuppressLint("StringFormatMatches")
    private void inicializarVariables() {
        objetoServidor = new ServidorPHP();
        user = FirebaseAuth.getInstance().getCurrentUser();
        prendas = obtenerPrendas();
        nombreUser.setText( getResources().getString(R.string.saludoMainActivityDrawer, nombreUsuarioString) );
        prendasAdd.setText( getResources().getString(R.string.prendasAnadidasMainActivityDrawer, prendas ) );
    }

    private int obtenerPrendas() {
        int cantidadPrendas;

        cantidadPrendas = objetoServidor.obtenerCantidadPrendas(user.getUid());

        return cantidadPrendas;
    }

    public void cerrarMainDrawer() {
        finish();
    }

    private void conectarVariablesConVista () {
        nombreUser = findViewById(R.id.nombreUsuario);
        prendasAdd = findViewById(R.id.prendasAdd);
        btnVerArmario = findViewById(R.id.bVerArmario);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mi_armario) {
            intent = new Intent(this, MiArmario.class);
            intent.putExtra(UID_USUARIO_KEY, user.getUid());
            intent.putExtra(NUMERO_PRENDAS, prendas);
            startActivity(intent);

        } else if (id == R.id.add_elemento) {
            intent = new Intent(this, ActividadAddPrenda.class);
            intent.putExtra("sexoUsuario", userActual.getSexo());
            intent.putExtra("tallaPorDefecto", userActual.getTallaPorDefecto());
            startActivity(intent);

        } else if (id == R.id.eliminar_elemento) {
            intent = new Intent(this, ActividadEliminar.class);
            intent.putExtra(UID_USUARIO_KEY, user.getUid());
            intent.putExtra(NUMERO_PRENDAS, prendas);
            startActivity(intent);

        } else if (id == R.id.consultar_elemento) {
            intent = new Intent(this, ActividadConsultar.class);
            intent.putExtra(UID_USUARIO_KEY, user.getUid());
            intent.putExtra(SEXO_USUARIO, userActual.getSexo());
            intent.putExtra(NUMERO_PRENDAS, prendas);
            startActivity(intent);

        } else if (id == R.id.intercambio) {
            intent = new Intent(this, MainIntercambio.class);
            intent.putExtra(UID_USUARIO_KEY, user.getUid());
            startActivity(intent);

        } else if (id == R.id.configuracion_usuario) {
            // Pasamos el objeto usuario:
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuarioConectado", userActual);

            intent = new Intent(this, MiCuenta.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.informacion) {
            intent = new Intent(this, Informacion.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bVerArmario) {
            intent = new Intent(this, MiArmario.class);
            intent.putExtra(UID_USUARIO_KEY, user.getUid());
            intent.putExtra(NUMERO_PRENDAS, prendas);

            startActivity(intent);
        }
    }
}
