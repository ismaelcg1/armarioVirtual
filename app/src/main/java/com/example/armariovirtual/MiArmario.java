package com.example.armariovirtual;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MiArmario extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_armario_principal);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.calzado);

    }

    MiArmarioRopaSuperior miArmarioRopaSuperiorFragment = new MiArmarioRopaSuperior();
    MiArmarioRopaInferior miArmarioRopaInferiorFragment = new MiArmarioRopaInferior();
    MiArmarioCalzado miArmarioCalzadoFragment = new MiArmarioCalzado();
    MiArmarioRopaInterior miArmarioRopaInteriorFragment = new MiArmarioRopaInterior();
    MiArmarioComplementos miArmarioComplementosFragment = new MiArmarioComplementos();


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
