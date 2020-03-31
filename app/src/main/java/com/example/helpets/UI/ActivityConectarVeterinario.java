package com.example.helpets.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.ui.ActivityConsultaVeterinario;
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

public class ActivityConectarVeterinario extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private ListView listaVeterinarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectar_veterinario);
        listaVeterinarios = (ListView)findViewById(R.id.conectar_veterinarios_lista);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("veterinarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            llenarLista(task);
                        } else{
                            Toast.makeText(ActivityConectarVeterinario.this,
                                    "Error al conectar a la base de datos,",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        listaVeterinarios.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth sesionFirebase = FirebaseAuth.getInstance();
        if (GoogleSignIn.getLastSignedInAccount(ActivityConectarVeterinario.this) == null
                || sesionFirebase.getCurrentUser() == null){
            startActivity(new Intent(ActivityConectarVeterinario.this,
                    com.example.helpets.UI.ActivityInicioSesion.class));
            finish();
        }
    }

    private void llenarLista(Task<QuerySnapshot> task){
        List<Map<String, String>> lista = new ArrayList<>();

        for (QueryDocumentSnapshot document : task.getResult()) {
            String nombre = document.getData().get("nombre").toString();
            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put("Nombre", nombre);
            hashmap.put("Clientes", "Clientes satisfechos: "+document.getData().get("clientes")
                    .toString());
            lista.add(hashmap);
        }

        /* DATOS DE PRUEBA */
        for (int i=0;i<10;i++){
            HashMap<String,String> pruebaHashmap = new HashMap<>();
            pruebaHashmap.put("Nombre", "Adrián");
            pruebaHashmap.put("Clientes", "Clientes satisfechos: 33");
            lista.add(pruebaHashmap);
        }
        /* FIN DATOS DE PRUEBA */

        SimpleAdapter adapter = new SimpleAdapter(ActivityConectarVeterinario.this,
                lista,
                R.layout.lista_veterinarios,
                new String[]{"Nombre", "Clientes"},
                new int[]{R.id.listaTexto1, R.id.listaTexto2}
        );
        listaVeterinarios.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(ActivityConectarVeterinario.this,
                ActivityConsultaVeterinario.class));
    }
}
