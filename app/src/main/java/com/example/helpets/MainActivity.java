package com.example.helpets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private Button botonMenu;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonMenu = (Button)findViewById(R.id.botonMenu);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken
                (getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent inicioSesionIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(inicioSesionIntent, RC_SIGN_IN);
        mAuth = FirebaseAuth.getInstance();


        /*
        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });
        //startActivity(new Intent(this, ActivityInicioSesion.class));

         */
    }

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

    @Override
    protected void onStart() {
        super.onStart();
        /*if (GoogleSignIn.getLastSignedInAccount(this) == null) {
            System.out.println("No se ha iniciado sesión.");
        } else {
            System.out.println("Sesión iniciada");
        }*/
        FirebaseUser usuarioActual = mAuth.getCurrentUser();
    }

    private void inicioSesionFirebase(GoogleSignInAccount account){
        Log.d(TAG, "firebaseAuthWithGoogle" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "signInWithCredentials");
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println("SESIÓN INICIADA");
                        } else{
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            System.out.println("INICIO DE SESIÓN FALLIDO");
                        }
                    }
                });

    }


}
