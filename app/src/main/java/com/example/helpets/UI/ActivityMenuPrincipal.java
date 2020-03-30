package com.example.helpets.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpets.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityMenuPrincipal extends AppCompatActivity implements View.OnClickListener {

    private Button botonMenu;
    private Button botonCitas;
    private Button botonAdoptar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
        botonCitas = (Button)findViewById(R.id.botonCitas);
        botonAdoptar = (Button)findViewById(R.id.botonAdopta);

        botonCitas.setOnClickListener(this);

        botonAdoptar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonCitas:
                startActivity(new Intent(ActivityMenuPrincipal.this, ActivityConectarVeterinario.class));
                break;
            case R.id.botonAdopta:
                startActivity(new Intent(ActivityMenuPrincipal.this, ActivityAdopta.class));
                break;
        }
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
}
