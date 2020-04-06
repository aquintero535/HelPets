package com.example.helpets.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.adapter.AdaptadorVeterinario;
import com.example.helpets.adapter.RecyclerViewClickListener;
import com.example.helpets.adapter.Veterinario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import java.util.List;


public class ActivityConectarVeterinario extends AppCompatActivity {

    private RecyclerView listaVeterinarios;
    private List<Veterinario> lista = new ArrayList<>();
    private RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectar_veterinario);
        listaVeterinarios = (RecyclerView) findViewById(R.id.conectar_veterinarios_lista);
        listaVeterinarios.setLayoutManager(new LinearLayoutManager(this));

        //Obtiene la lista de veterinarios de la base de datos
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("veterinarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            llenarLista(task);
                        } else {
                            Toast.makeText(ActivityConectarVeterinario.this,
                                    "Error al conectar a la base de datos,",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Listener para cuando el usuario toque un item de la lista de veterinarios.
        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent consultaVeterinaria = new Intent(ActivityConectarVeterinario.this,
                        ActivityConsultaVeterinario.class);
                consultaVeterinaria.putExtra("idUsuario",
                        FirebaseAuth.getInstance().getCurrentUser().getUid());
                consultaVeterinaria.putExtra("nombreUsuario",
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                consultaVeterinaria.putExtra("idVeterinario", lista.get(position).getIdVeterinario());
                consultaVeterinaria.putExtra("nombreVeterinario", lista.get(position).getNombreVeterinario());
                consultaVeterinaria.putExtra("imgPerfilVeterinario", lista.get(position).getFotoPerfil());
                startActivity(consultaVeterinaria);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth sesionFirebase = FirebaseAuth.getInstance();
        if (sesionFirebase.getCurrentUser() == null){
            startActivity(new Intent(ActivityConectarVeterinario.this,
                    com.example.helpets.ui.ActivityInicioSesion.class));
            finish();
        }
    }

    //Obtiene los datos de los documentos y los coloca en un Lista de objetos Veterinario.
    private void llenarLista(Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            String nombre = document.getData().get("nombre").toString();
            String clientes = "Clientes satisfechos: "+document.getData().get("clientes").toString();
            String imagenPerfil = document.getData().get("fotoPerfil").toString();
            String idVeterinario = document.getData().get("idVeterinario").toString();
            lista.add(new Veterinario(nombre, clientes, imagenPerfil, idVeterinario));
        }
        AdaptadorVeterinario adaptadorVeterinario = new AdaptadorVeterinario(this, lista, listener);
        listaVeterinarios.setAdapter(adaptadorVeterinario);
    }
}
