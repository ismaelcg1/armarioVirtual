package com.example.armariovirtual;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreenObtenerDatos extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private final int TIEMPO_ESPERA_EJECUCION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quitarBarraDeNotificaciones();
        setContentView(R.layout.splash_screen_obtener_datos);

        lottieAnimationView = findViewById(R.id.animation_view_obtener);
        startCheckAnimation();
        ejecutarHandler();
    }

    private void quitarBarraDeNotificaciones() {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void ejecutarHandler() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, TIEMPO_ESPERA_EJECUCION);
    }

    private void startCheckAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(1900);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lottieAnimationView.setProgress((Float) animation.getAnimatedValue());
            }
        });

        if (lottieAnimationView.getProgress() == 0f) {
            animator.setStartDelay(50);
            animator.start();
            animator.setRepeatCount(Animation.INFINITE);
        } else {
            lottieAnimationView.setProgress(0f);
        }

    }

}
