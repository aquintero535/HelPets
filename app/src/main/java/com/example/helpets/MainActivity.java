package com.example.helpets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity {

    //La clase Main sirve como puente para el Activity del menú principal o el de inicio de sesión.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Por motivos de prueba, cada vez que se inicie la aplicación la sesión de Google se
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
        if (GoogleSignIn.getLastSignedInAccount(this) == null){
            startActivity(new Intent(this, ActivityInicioSesion.class));
            finish();
        } else{
            startActivity(new Intent(this, ActivityMenuPrincipal.class));
            finish();
        }
    }

}
