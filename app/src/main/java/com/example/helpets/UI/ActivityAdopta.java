package com.example.helpets.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.viewmodel.ViewModelAdoptar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAdopta extends AppCompatActivity {

    private Fragment fragmentListaAdopcion, fragmentFormularioAdopcion;
    private ViewModelAdoptar viewModelAdoptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopta);

        //Fragment de lista de adopción.
        fragmentListaAdopcion = new FragmentListaAdopcion();
        getSupportFragmentManager().beginTransaction().replace
                (R.id.contenedorFragmentsAdoptar, fragmentListaAdopcion).commit();

    }



    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth sesionFirebase = FirebaseAuth.getInstance();
        if (GoogleSignIn.getLastSignedInAccount(ActivityAdopta.this) == null
                || sesionFirebase.getCurrentUser() == null){
            startActivity(new Intent(ActivityAdopta.this,
                    ActivityInicioSesion.class));
            finish();
        }
    }
}
