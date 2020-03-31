package com.example.helpets.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.helpets.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class ActivityInicioSesion extends AppCompatActivity {

    private SignInButton botonIniciarSesion;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        constraintLayout = (ConstraintLayout)findViewById(R.id.constraint_layout_iniciosesion);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken
                (getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        /*Para no mantener la sesión iniciada*/
        //googleSignInClient.signOut();

        mAuth = FirebaseAuth.getInstance();
        botonIniciarSesion = findViewById(R.id.sign_in_button);
        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

    }

    //Función que inicia el Activity de Google para iniciar sesión.
    private void iniciarSesion(){

        Intent inicioSesionIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(inicioSesionIntent, RC_SIGN_IN);
    }

    //Función que comprueba si el inicio de sesión fue exitoso. De serlo, envía las credenciales
    //a Firebase.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                inicioSesionFirebase(account);
            } catch (ApiException e){
                Log.w(TAG, "Google sign in failed", e);
                System.out.println("STATUS CODE: "+e.getStatusCode());
            }

        }
    }

    //Inicia sesión en Firebase con las credenciales de Google.
    //Luego de iniciar sesión en Google, Google envía la credencial al objeto credential
    //el cual después se enviará al método signInWithCredential para iniciar sesión en Firebase.
    private void inicioSesionFirebase(GoogleSignInAccount account){
        Log.d(TAG, "firebaseAuthWithGoogle" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "signInWithCredentials");
                            usuario = mAuth.getCurrentUser();
                            Toast.makeText(ActivityInicioSesion.this,
                                    "Sesión iniciada",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            startActivity(new Intent
                                    (ActivityInicioSesion.this,
                                            ActivityMenuPrincipal.class));
                            finish();
                        } else{
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(ActivityInicioSesion.this,
                                    "No se pudo iniciar sesión",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}
