package com.example.helpets.ui.InicioSesion;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpets.R;

public class ActivityInicioSesion extends AppCompatActivity {


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        getSupportFragmentManager().beginTransaction().replace
                (R.id.fragmentInicioSesion, new FragmentInicioSesionGoogle()).commit();
    }

}