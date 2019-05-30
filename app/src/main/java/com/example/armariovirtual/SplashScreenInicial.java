package com.example.armariovirtual;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenInicial extends AppCompatActivity {

    private final int TIEMPO_SPLASH_INICIAL = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quitarBarraDeNotificaciones();
        setContentView(R.layout.splash_inicio);
        ejecutarApp();
    }

    private void quitarBarraDeNotificaciones() {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * Método que se encarga en mostrar un Splash Screen inicial y pasados @TIEMPO_SPLASH_INICIAL milisegundos
     * Se carga la actividad principal de la APP
     */
    private void ejecutarApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenInicial.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIEMPO_SPLASH_INICIAL);
    }
}
