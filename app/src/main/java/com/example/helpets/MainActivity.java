package com.example.helpets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity {

    //La clase Main sirve como puente para el Activity del menú principal o el de inicio de sesión.
    //Contador de la pantalla del logo.
    private final int TIEMPO = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Por motivos de prueba, cada vez que se inicie la aplicación la sesión de Google se
        cerrará.*/
        

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken
                (getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut();


    }

    @Override
    protected void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Si el usuario ya ha iniciado sesión en Google, inicia el Activity del menú
                //principal. De lo contrario, lo envía al de inicio de sesión y registro.
                if (GoogleSignIn.getLastSignedInAccount(MainActivity.this) == null){
                    startActivity(new Intent(MainActivity.this, ActivityInicioSesion.class));
                    finish();
                } else{
                    startActivity(new Intent(MainActivity.this, ActivityMenuPrincipal.class));
                    finish();
                }
            }
        }, TIEMPO);

    }

}
