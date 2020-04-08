package com.example.helpets.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;


import com.example.helpets.R;

import com.google.firebase.auth.FirebaseAuth;


public class ActivityAdopta extends AppCompatActivity {

    private Fragment fragmentListaAdopcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopta);

        //Fragment de lista de adopción.
        fragmentListaAdopcion = new FragmentListaAdopcion();
        getSupportFragmentManager().beginTransaction().add
                (R.id.contenedorFragmentsAdoptar, fragmentListaAdopcion).commit();

    }



    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth sesionFirebase = FirebaseAuth.getInstance();
        if (sesionFirebase.getCurrentUser() == null){
            startActivity(new Intent(ActivityAdopta.this,
                    ActivityInicioSesion.class));
            finish();
        }
    }
}
