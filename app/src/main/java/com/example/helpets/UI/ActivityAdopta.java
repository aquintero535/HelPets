package com.example.helpets.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.helpets.R;
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

    private ListView listaAdopcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopta);
        listaAdopcion = (ListView)findViewById(R.id.listaAdopta);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("adopcion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            llenarLista(task);
                        } else{
                            Toast.makeText(ActivityAdopta.this,
                                    "Error al conectar a la base de datos,",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void llenarLista(Task<QuerySnapshot> task){
        List<Map<String, String>> lista = new ArrayList<>();

        for (QueryDocumentSnapshot document : task.getResult()) {
            String nombre = "Nombre: ".concat(document.getData().get("nombre").toString());
            String edad = "Edad: ".concat(document.getData().get("edad").toString());
            String vacunas = "Vacunas: ".concat((Boolean)(document.getData().get("vacunas"))?"Sí":"No");
            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put("Nombre", nombre);
            hashmap.put("Edad", edad);
            hashmap.put("Vacunas", vacunas);
            lista.add(hashmap);
        }

        SimpleAdapter adapter = new SimpleAdapter(ActivityAdopta.this,
                lista,
                R.layout.lista_adopcion,
                new String[]{"Nombre", "Edad", "Vacunas"},
                new int[]{R.id.adopcionItemNombre, R.id.adopcionItemEdad, R.id.adopcionItemVacunas}
        );
        listaAdopcion.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth sesionFirebase = FirebaseAuth.getInstance();
        if (GoogleSignIn.getLastSignedInAccount(ActivityAdopta.this) == null
                && sesionFirebase.getCurrentUser() == null){
            startActivity(new Intent(ActivityAdopta.this,
                    ActivityInicioSesion.class));
            finish();
        }
    }
}
