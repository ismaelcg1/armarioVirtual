package com.example.armariovirtual;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.MenuItem;
import java.util.ArrayList;


public class MiArmario extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private FirebaseUser user;
    private ServidorPHP objetoServidor;
    private String uidUsuarioActual;
    protected static final String UID_USUARIO = "UID";
    private final int TIEMPO_ESPERA_INICIAL = 600;

    protected static final String ARRAY_LIST_ROPA_SUPERIOR   = "ROPA_SUPERIOR";
    protected static final String ARRAY_LIST_ROPA_INFERIOR   = "ROPA_INFERIOR";
    protected static final String ARRAY_LIST_ROPA_INTERIOR   = "ROPA_INTERIOR";
    protected static final String ARRAY_LIST_CALZADO         = "CALZADO";
    protected static final String ARRAY_LIST_COMPLEMENTOS    = "COMPLEMENTOS";

    protected static final String VARIABLE_FILTRADA          = "categoria";
    protected static final String VALOR_FILTRO_ROPA_SUPERIOR = "Ropa superior";
    protected static final String VALOR_FILTRO_ROPA_INFERIOR = "Ropa inferior";
    protected static final String VALOR_FILTRO_ROPA_INTERIOR = "Ropa interior";
    protected static final String VALOR_FILTRO_CALZADO       = "Calzado";
    protected static final String VALOR_FILTRO_COMPLEMENTOS  = "Complementos";

    protected static ArrayList <Prenda> listadoPrendasSuperiores;
    protected static ArrayList <Prenda> listadoPrendasInferiores;
    protected static ArrayList <Prenda> listadoPrendasInteriores;
    protected static ArrayList <Prenda> listadoCalzado;
    protected static ArrayList <Prenda> listadoComplementos;

    Bundle bundleRopaSuperior;
    Bundle bundleRopaInferior;
    Bundle bundleRopaInterior;
    Bundle bundleCalzado;
    Bundle bundleComplementos;

    MiArmarioRopaSuperior miArmarioRopaSuperiorFragment;
    MiArmarioRopaInferior miArmarioRopaInferiorFragment;
    MiArmarioCalzado miArmarioCalzadoFragment;
    MiArmarioRopaInterior miArmarioRopaInteriorFragment;
    MiArmarioComplementos miArmarioComplementosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_armario_principal);

        inicializarVariablesIniciales();
        abrirSplashEsperarDatos();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.calzado);

        obtenerPrendasSuperiores();
        obtenerDatosServidor();
    }

    private void inicializarVariablesIniciales() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        uidUsuarioActual = user.getUid();
        objetoServidor = new ServidorPHP();
        listadoPrendasSuperiores = new ArrayList();
        listadoPrendasInferiores = new ArrayList();
        bundleRopaSuperior = new Bundle();
        bundleRopaInferior = new Bundle();
        bundleRopaInterior = new Bundle();
        bundleCalzado      = new Bundle();
        bundleComplementos = new Bundle();

        inicializarFragment();
    }

    private void abrirSplashEsperarDatos() {
        Intent intent = new Intent(this, SplashScreenCargarMiArmario.class);
        startActivity(intent);
    }

    private void obtenerDatosServidor() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                obtenerPrendasRestantes();
            }
        }, TIEMPO_ESPERA_INICIAL);
    }

    private void obtenerPrendasSuperiores() {
        try {
            listadoCalzado = objetoServidor.obtenerPrendas(uidUsuarioActual, VARIABLE_FILTRADA, VALOR_FILTRO_CALZADO);
            insertarParametrosCalzado();
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
    }

    private void obtenerPrendasRestantes() {
        try {
            listadoPrendasSuperiores = objetoServidor.obtenerPrendas(uidUsuarioActual, VARIABLE_FILTRADA, VALOR_FILTRO_ROPA_SUPERIOR);
            listadoPrendasInferiores = objetoServidor.obtenerPrendas(uidUsuarioActual, VARIABLE_FILTRADA, VALOR_FILTRO_ROPA_INFERIOR);
            listadoPrendasInteriores = objetoServidor.obtenerPrendas(uidUsuarioActual, VARIABLE_FILTRADA, VALOR_FILTRO_ROPA_INTERIOR);
            listadoComplementos = objetoServidor.obtenerPrendas(uidUsuarioActual, VARIABLE_FILTRADA, VALOR_FILTRO_COMPLEMENTOS);
            // Una vez obtenidos los parámetros, los insertamos para pasarlos a los fragment
            insertarParametrosRestantes();
        } catch (ServidorPHPException e) {
            e.printStackTrace();
        }
    }

    private void inicializarFragment() {
        miArmarioRopaSuperiorFragment = new MiArmarioRopaSuperior();
        miArmarioRopaInferiorFragment = new MiArmarioRopaInferior();
        miArmarioCalzadoFragment = new MiArmarioCalzado();
        miArmarioRopaInteriorFragment = new MiArmarioRopaInterior();
        miArmarioComplementosFragment = new MiArmarioComplementos();
    }

    private void insertarParametrosCalzado() {
        bundleCalzado.putString(UID_USUARIO, uidUsuarioActual);
        bundleCalzado.putParcelableArrayList(ARRAY_LIST_CALZADO, listadoCalzado);
        miArmarioCalzadoFragment.setArguments(bundleCalzado);
    }

    private void insertarParametrosRestantes() {
        bundleRopaSuperior.putString(UID_USUARIO, uidUsuarioActual);
        bundleRopaSuperior.putParcelableArrayList(ARRAY_LIST_ROPA_SUPERIOR, listadoPrendasSuperiores);
        miArmarioRopaSuperiorFragment.setArguments(bundleRopaSuperior);

        bundleRopaInferior.putString(UID_USUARIO, uidUsuarioActual);
        bundleRopaInferior.putParcelableArrayList(ARRAY_LIST_ROPA_INFERIOR, listadoPrendasInferiores);
        miArmarioRopaInferiorFragment.setArguments(bundleRopaInferior);

        bundleRopaInterior.putString(UID_USUARIO, uidUsuarioActual);
        bundleRopaInterior.putParcelableArrayList(ARRAY_LIST_ROPA_INTERIOR, listadoPrendasInteriores);
        miArmarioRopaInteriorFragment.setArguments(bundleRopaInterior);

        bundleComplementos.putString(UID_USUARIO, uidUsuarioActual);
        bundleComplementos.putParcelableArrayList(ARRAY_LIST_COMPLEMENTOS, listadoComplementos);
        miArmarioComplementosFragment.setArguments(bundleComplementos);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ropa_superior:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, miArmarioRopaSuperiorFragment).commit();
                return true;

            case R.id.ropa_inferior:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, miArmarioRopaInferiorFragment).commit();
                return true;

            case R.id.calzado:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, miArmarioCalzadoFragment).commit();
                return true;

            case R.id.ropa_interior:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, miArmarioRopaInteriorFragment).commit();
                return true;

            case R.id.ropa_complementos:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, miArmarioComplementosFragment).commit();
                return true;
        }
        return false;
    }
}
