package com.main.app_firmas_segucor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        SharedPreferences preferencias = getSharedPreferences("sesion", MODE_PRIVATE);
        if(preferencias.getBoolean("estado_usu",false)==false) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent pantallaLogin = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(pantallaLogin);
                    finish();
                }
            },4000);

        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Intent pantallaListadoSolicitudes = new Intent(getApplicationContext(), SolicitudesCotizacionListado.class);
                    Intent pantallaListadoSolicitudes = new Intent(getApplicationContext(), OtesSCActivity.class);
                    startActivity(pantallaListadoSolicitudes);
                }
            },4000);
        }

    }
}