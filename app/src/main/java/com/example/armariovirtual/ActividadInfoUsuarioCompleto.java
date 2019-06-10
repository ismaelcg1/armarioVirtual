package com.example.armariovirtual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActividadInfoUsuarioCompleto extends AppCompatActivity {

    private Toolbar appToolbar;
    private TextView tvUid, tvNickName, tvAltura, tvPeso, tvTalla, tvFechaNacimiento, tvGenero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_todo_informacion_usuario);

        inicializarVariablesConVista();

        // Cogemos los datos pasados al intent
        Intent intentActividadActual = getIntent();
        Bundle b = intentActividadActual.getExtras();

        if(b!=null)
        {
            String uidUsuarioTurno =(String) b.get("uidUsuarioTurno");
            String nickUsuarioTurno =(String) b.get("nickUsuarioTurno");
            String tallaTurno =(String) b.get("tallaTurno");
            String fechaNacimientoTurno =(String) b.get("fechaNacimientoTurno");
            String sexoTurno =(String) b.get("sexoTurno");

            int alturaTurno =(int) b.get("alturaTurno");
            int pesoTurno =(int) b.get("pesoTurno");

            tvUid.setText(uidUsuarioTurno);
            tvNickName.setText(nickUsuarioTurno);
            tvTalla.setText(tallaTurno);
            tvFechaNacimiento.setText(fechaNacimientoTurno);
            tvGenero.setText(sexoTurno);
            tvAltura.setText(Integer.toString(alturaTurno));
            tvPeso.setText(Integer.toString(pesoTurno));

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

        tvUid = findViewById(R.id.uidUsuarioVisualizado);
        tvNickName = findViewById(R.id.nickNameUsuarioVisualizado);
        tvAltura = findViewById(R.id.alturaUsuarioVisualizado);
        tvPeso = findViewById(R.id.pesoUsuarioVisualizado);
        tvTalla = findViewById(R.id.tallaUsuarioVisualizado);
        tvFechaNacimiento = findViewById(R.id.fechaNacimientoUsuarioVisualizado);
        tvGenero = findViewById(R.id.generoUsuarioVisualizado);
    }
}
