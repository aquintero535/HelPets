package com.example.helpets;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpets.UI.InicioSesion.ActivityInicioSesion;
import com.example.helpets.UI.Menu.ActivityMenuPrincipal;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements Callback {

    /* Universidad Interamericana de Panamá
    * Programación de Computadoras II - Grupo X
    * Prof. Leonardo Esqueda
    *
    * Integrantes del grupo:
    * Adrián Quintero
    * Edixo Jiménez
    * Juan Hincapié
    * Henry Cárdenas
    * Juan Solís
    */


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

    //Método que se ejecuta después de 3000 milisegundos, para dar el efecto de Splash Screen.
    //Primero comprueba si el usuario tiene una sesión iniciada. Si no, lo envía al Activity de
    //inicio de sesión. Si la tiene, llama a la base de datos para comprobar si el usuario es
    //un veterinario o un cliente, después continúa hacia el menú principal.
    @Override
    protected void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Si el usuario no ha iniciado sesión en Google y Firebase, lo envía al Activity
                //de inicio de sesión. De haberlo hecho, lo envía al menú principal.
                if (sesionFirebase.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this,
                            ActivityInicioSesion.class));
                    finish();
                } else{
                    Usuario usuario = new Usuario();
                    usuario.obtenerDatosUsuario();
                    usuario.registrarCallback(MainActivity.this);
                }
            }
        }, TIEMPO);
    }

    /* Métodos que se ejecutan después de llamar al método obtenerDatosUsuario(); */
    @Override
    public void datosObtenidos() {
        startActivity(new Intent(MainActivity.this,
                ActivityMenuPrincipal.class));
        finish();
    }

    @Override
    public void datosNoObtenidos(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivity.this,
                ActivityMenuPrincipal.class));
        finish();
    }
}
