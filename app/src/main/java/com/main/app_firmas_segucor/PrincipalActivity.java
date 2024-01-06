package com.main.app_firmas_segucor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.main.app_firmas_segucor.fragments.OtesListaSCFragment;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.getSupportActionBar().hide();

        OtesListaSCFragment newFragment=new OtesListaSCFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.principal_frame_container,newFragment);
        fragmentTransaction.commit();



    }
}