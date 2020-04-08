package com.example.helpets.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.adapter.AdaptadorVeterinario;
import com.example.helpets.adapter.RecyclerViewClickListener;
import com.example.helpets.model.Veterinario;
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
                consultaVeterinaria.putExtra(ActivityConsultaVeterinario.ID_USUARIO,
                        FirebaseAuth.getInstance().getCurrentUser().getUid());
                consultaVeterinaria.putExtra(ActivityConsultaVeterinario.NOMBRE_USUARIO,
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                consultaVeterinaria.putExtra(ActivityConsultaVeterinario.ID_VETERINARIO,
                        lista.get(position).getIdVeterinario());
                consultaVeterinaria.putExtra(ActivityConsultaVeterinario.NOMBRE_VETERINARIO,
                        lista.get(position).getNombreVeterinario());
                consultaVeterinaria.putExtra(ActivityConsultaVeterinario.IMG_PERFIL_VETERINARIO,
                        lista.get(position).getFotoPerfil());
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

    //Obtiene los datos de los documentos y los coloca en una Lista de objetos Veterinario.
    private void llenarLista(Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            Veterinario itemVeterinario = document.toObject(Veterinario.class);
            lista.add(itemVeterinario);
        }
        AdaptadorVeterinario adaptadorVeterinario = new AdaptadorVeterinario
                (this, lista, listener);
        listaVeterinarios.setAdapter(adaptadorVeterinario);
    }
}
