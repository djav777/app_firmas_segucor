package com.main.app_firmas_segucor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.main.app_firmas_segucor.fragments.OtesListaSCFragment;
import com.main.app_firmas_segucor.models.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.main.app_firmas_segucor.config.ApiConf;
import com.main.app_firmas_segucor.session.SessionOperarios;


public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText etUser, etPassword;

    private SessionOperarios sessionOperarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setTitle("Acceder a Firmas");
        int colorBanner= Color.parseColor("#0000f4");
        setActionBarColor(colorBanner);
        int colorbtnLogin= Color.parseColor("#FF5900");

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA,
                    },
                    0);
        }
        sessionOperarios = new SessionOperarios(MainActivity.this);
        btn_login = findViewById(R.id.btn_login);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = etUser.getText().toString();
                String contrasena = etPassword.getText().toString();
                if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                    login(usuario, contrasena);
                } else {
                    Toast.makeText(MainActivity.this, "Faltan parametro(s)", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    void login(String email, String password) {
        Call<Login> call = ApiConf.getData().getInitData(email, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful())
                {
                    Login login = response.body();

                    if(login.getRut()==0){
                        Toast.makeText(MainActivity.this, login.getNombre(), Toast.LENGTH_LONG).show();

                    }else {
                        //Setear variable de session. Los del operador y del login
                        guardarDatosOperador(String.valueOf(login.getRut()),
                                login.getDiv(), login.getNombre(), String.valueOf(login.getEmpresa()));

                        sessionOperarios.loginSession(String.valueOf(login.getRut()),
                                login.getDiv(), login.getNombre(), String.valueOf(login.getEmpresa()));

                        guardarSession();
                        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                        startActivity(intent);
                        /*Intent pantallaListadoSolicitudes = new Intent(getApplicationContext(), OtesSCActivity.class);
                        startActivity(pantallaListadoSolicitudes);*/
//                        OtesListaSCFragment newFragment=new OtesListaSCFragment();
//                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.principal_frame_container,newFragment);
//                        fragmentTransaction.commit();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error login", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e("ERROR--->", t.toString());
            }
        });
    }

    void guardarDatosOperador(String rut, String dv, String nombre, String empresa){
        SharedPreferences pref = getSharedPreferences("datos_operador", MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("rut",rut);
        editor.putString("dv",dv);
        editor.putString("nombre",nombre);
        editor.putString("empresa",empresa);
        editor.commit();
    }
    void guardarSession(){
        SharedPreferences pref = getSharedPreferences("sesion", MODE_PRIVATE);
        boolean estado = true;
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("estado_usu",estado);
        editor.commit();
    }
    public void setActionBarColor(int parsedColor){
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(parsedColor));
        //mActionBar.setDisplayShowTitleEnabled(false);
        //mActionBar.setDisplayShowTitleEnabled(true);
    }
}