package com.example.helpets.ui;

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
import com.example.helpets.adapter.AdaptadorVeterinario;
import com.example.helpets.adapter.Veterinario;
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
    private List<Veterinario> lista = new ArrayList<>();

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
                    com.example.helpets.ui.ActivityInicioSesion.class));
            finish();
        }
    }

    private void llenarLista(Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            String nombre = document.getData().get("nombre").toString();
            String clientes = "Clientes satisfechos: "+document.getData().get("clientes").toString();
            String imagenPerfil = document.getData().get("fotoPerfil").toString();
            String idVeterinario = document.getData().get("idVeterinario").toString();
            lista.add(new Veterinario(nombre, clientes, imagenPerfil, idVeterinario));
        }

        AdaptadorVeterinario adaptadorVeterinario = new AdaptadorVeterinario(this, lista);
        listaVeterinarios.setAdapter(adaptadorVeterinario);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent consultaVeterinaria = new Intent(ActivityConectarVeterinario.this,
                ActivityConsultaVeterinario.class);
        consultaVeterinaria.putExtra("idUsuario",
                FirebaseAuth.getInstance().getCurrentUser().getUid());
        consultaVeterinaria.putExtra("nombreUsuario",
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        consultaVeterinaria.putExtra("idVeterinario", lista.get(position).getIdVeterinario());
        consultaVeterinaria.putExtra("nombreVeterinario", lista.get(position).getNombreVeterinario());
        startActivity(consultaVeterinaria);
    }
}
