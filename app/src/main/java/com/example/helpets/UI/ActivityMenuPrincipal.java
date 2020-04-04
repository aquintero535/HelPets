package com.example.helpets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpets.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityMenuPrincipal extends AppCompatActivity {

    private final int MESSAGE_SENT = 500;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

    }


    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth sesionFirebase = FirebaseAuth.getInstance();
        if (GoogleSignIn.getLastSignedInAccount(ActivityMenuPrincipal.this) == null
                || sesionFirebase.getCurrentUser() == null){
            startActivity(new Intent(ActivityMenuPrincipal.this,
                    ActivityInicioSesion.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, null);
        if (resultCode == MESSAGE_SENT){
            Snackbar.make(findViewById(R.id.layoutMenuPrincipal),
                    "Tu mensaje ha sido enviado.",
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
