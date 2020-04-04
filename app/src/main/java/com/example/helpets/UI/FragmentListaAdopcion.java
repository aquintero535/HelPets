package com.example.helpets.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.viewmodel.ViewModelAdoptar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListaAdopcion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListaAdopcion extends Fragment implements AdapterView.OnItemClickListener {

    private ViewModelAdoptar viewModelAdoptar;
    private ListView listaAdopcion;

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
        listaAdopcion = (ListView)view.findViewById(R.id.listaAdopta);
        viewModelAdoptar.setDb(FirebaseFirestore.getInstance());
        viewModelAdoptar.getDb().collection("adopcion")
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
        listaAdopcion.setOnItemClickListener(this);
    }

    private void llenarLista(Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            String nombre = "Nombre: ".concat(document.getData().get("nombre").toString());
            String edad = "Edad: ".concat(document.getData().get("edad").toString());
            String vacunas = "Vacunas: ".concat
                    ((Boolean)(document.getData().get("vacunas"))?"Sí":"No");
            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put("Nombre", nombre);
            hashmap.put("Edad", edad);
            hashmap.put("Vacunas", vacunas);
            hashmap.put("idDocumento", document.getId());
            viewModelAdoptar.getListaAdopcion().add(hashmap);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                viewModelAdoptar.getListaAdopcion(),
                R.layout.lista_adopcion,
                new String[]{"Nombre", "Edad", "Vacunas"},
                new int[]{R.id.adopcionItemNombre, R.id.adopcionItemEdad, R.id.adopcionItemVacunas}
        );
        listaAdopcion.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        viewModelAdoptar.setMascotaSeleccionada
                (viewModelAdoptar
                        .getListaAdopcion()
                        .get(position)
                        .get("idDocumento"));
        getActivity().getSupportFragmentManager().beginTransaction().replace
                (R.id.contenedorFragmentsAdoptar, new FragmentFormularioAdopcion()).commit();
    }
}
