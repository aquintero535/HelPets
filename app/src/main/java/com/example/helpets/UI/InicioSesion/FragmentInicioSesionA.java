package com.example.helpets.ui.InicioSesion;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.ui.Menu.ActivityMenuPrincipal;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class FragmentInicioSesionA extends Fragment implements View.OnClickListener {

    private SignInButton botonIniciarSesionGoogle;
    private Button botonIniciarSesionCorreo;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_sesion_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken
                (getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        /*Para no mantener la sesión iniciada*/
        //googleSignInClient.signOut();

        mAuth = FirebaseAuth.getInstance();
        botonIniciarSesionGoogle = view.findViewById(R.id.sign_in_button);
        botonIniciarSesionGoogle.setOnClickListener(this);
        botonIniciarSesionCorreo = (Button)view.findViewById(R.id.botonIniciarSesionCorreo);
        botonIniciarSesionCorreo.setOnClickListener(this);
    }

    //Función que inicia el Activity de Google para iniciar sesión.
    private void iniciarSesion(){
        Intent inicioSesionIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(inicioSesionIntent, RC_SIGN_IN);
    }

    //Función que comprueba si el inicio de sesión fue exitoso. De serlo, envía las credenciales
    //a Firebase.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
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
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "signInWithCredentials");
                            Toast.makeText(getActivity(),
                                    "Sesión iniciada",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            startActivity(new Intent
                                    (getActivity(),
                                            ActivityMenuPrincipal.class));
                            getActivity().finish();
                        } else{
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(),
                                    "No se pudo iniciar sesión",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_button:
                iniciarSesion();
                break;
            case R.id.botonIniciarSesionCorreo:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentInicioSesion, new FragmentInicioSesionB())
                        .commit();
                break;
        }
    }
}
