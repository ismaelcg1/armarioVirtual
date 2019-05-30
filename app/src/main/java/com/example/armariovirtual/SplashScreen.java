package com.example.armariovirtual;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private final int TIEMPO_EJECUCION = 1050;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quitarBarraDeNotificaciones();
        setContentView(R.layout.splash_screen);

        lottieAnimationView = findViewById(R.id.animation_view);
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
        }, TIEMPO_EJECUCION);
    }

    private void startCheckAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(1200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lottieAnimationView.setProgress((Float) animation.getAnimatedValue());
            }
        });

        if (lottieAnimationView.getProgress() == 0f) {
            //animator.setStartDelay(1500);
            animator.start();
        } else {
            lottieAnimationView.setProgress(0f);
        }

    }

}
