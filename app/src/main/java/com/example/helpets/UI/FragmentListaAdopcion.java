package com.example.helpets.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.adapter.AdaptadorMascotas;
import com.example.helpets.model.Mascota;
import com.example.helpets.adapter.RecyclerViewClickListener;
import com.example.helpets.viewmodel.ViewModelAdoptar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentListaAdopcion extends Fragment {

    private ViewModelAdoptar viewModelAdoptar;
    private RecyclerView listaAdopcion;
    private RecyclerViewClickListener listener;

    public FragmentListaAdopcion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adopta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelAdoptar = new ViewModelProvider(getActivity()).get(ViewModelAdoptar.class);
        listaAdopcion = (RecyclerView)view.findViewById(R.id.listaAdopta);
        listaAdopcion.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModelAdoptar.setDb(FirebaseFirestore.getInstance());
        viewModelAdoptar.getDb().collection("mascotas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            llenarLista(task);
                        } else{
                            Toast.makeText(getContext(),
                                    "Error al conectar a la base de datos,",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                viewModelAdoptar.setMascotaSeleccionada
                        (viewModelAdoptar
                                .getListaAdopcion()
                                .get(position)
                                .getIdMascota());
                getActivity().getSupportFragmentManager().beginTransaction().replace
                        (R.id.contenedorFragmentsAdoptar, new FragmentFormularioAdopcion()).commit();
            }
        };
    }

    private void llenarLista(Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            Mascota itemMascota = document.toObject(Mascota.class);
            viewModelAdoptar.getListaAdopcion()
                    .add(itemMascota);
        }
        AdaptadorMascotas adaptadorMascotas = new AdaptadorMascotas
                (viewModelAdoptar.getListaAdopcion(), getContext(), listener);
        listaAdopcion.setAdapter(adaptadorMascotas);
    }
}
