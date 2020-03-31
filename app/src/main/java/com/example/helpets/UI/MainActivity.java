package com.example.helpets.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.helpets.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
        /* Por motivos de prueba, cada vez que se inicie la aplicación la sesión de Google se
        cerrará.*/
        
        /*
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken
                (getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut();
        sesionFirebase.signOut();
        */

    }

    @Override
    protected void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Si el usuario no ha iniciado sesión en Google y Firebase, lo envía al Activity
                //de inicio de sesión. De haberlo hecho, lo envía al menú principal.
                if (GoogleSignIn.getLastSignedInAccount(MainActivity.this) == null
                        || sesionFirebase.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this,
                            com.example.helpets.UI.ActivityInicioSesion.class));
                    finish();
                } else{
                    System.out.println("USUARIO: "+sesionFirebase.getCurrentUser().getDisplayName());
                    startActivity(new Intent(MainActivity.this,
                            com.example.helpets.UI.ActivityMenuPrincipal.class));
                    finish();
                }
            }
        }, TIEMPO);

    }

}
