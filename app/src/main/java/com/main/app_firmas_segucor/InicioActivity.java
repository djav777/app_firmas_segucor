package com.main.app_firmas_segucor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.main.app_firmas_segucor.fragments.OtesListaSCFragment;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        SharedPreferences preferencias = getSharedPreferences("sesion", MODE_PRIVATE);

        Intent pantallaLogin = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(pantallaLogin);


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
            OtesListaSCFragment newFragment=new OtesListaSCFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.principal_frame_container,newFragment);
            fragmentTransaction.commit();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                   /* Intent pantallaListadoSolicitudes = new Intent(getApplicationContext(), OtesSCActivity.class);
//                    startActivity(pantallaListadoSolicitudes);
//*/
//
//
//
//                }
//            },4000);
        }

    }
}