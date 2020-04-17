package com.example.helpets;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpets.ui.InicioSesion.ActivityInicioSesion;
import com.example.helpets.ui.Menu.ActivityMenuPrincipal;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //La clase Main sirve como puente para el Activity del menú principal o el de inicio de sesión.
    //Contador de la pantalla del logo.
    private final int TIEMPO = 3000;
    private FirebaseAuth sesionFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sesionFirebase = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Usuario.obtenerDatosUsuario();
                //Si el usuario no ha iniciado sesión en Google y Firebase, lo envía al Activity
                //de inicio de sesión. De haberlo hecho, lo envía al menú principal.
                if (sesionFirebase.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this,
                            ActivityInicioSesion.class));
                    finish();
                } else{
                    System.out.println("USUARIO: "+sesionFirebase.getCurrentUser().getDisplayName());
                    startActivity(new Intent(MainActivity.this,
                            ActivityMenuPrincipal.class));
                    finish();
                }
            }
        }, TIEMPO);

    }

}
