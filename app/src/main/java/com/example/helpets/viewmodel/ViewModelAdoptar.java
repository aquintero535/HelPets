package com.example.helpets.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.helpets.adapter.Mascota;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewModelAdoptar extends ViewModel {

    private FirebaseFirestore db;
    private List<Mascota> listaAdopcion = new ArrayList<>();
    private String mascotaSeleccionada; //ID del documento de la mascota escogida.

    public ViewModelAdoptar(){
    }

    public List<Mascota> getListaAdopcion() {
        return listaAdopcion;
    }

    public void setListaAdopcion(List<Mascota> listaAdopcion) {
        this.listaAdopcion = listaAdopcion;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public String getMascotaSeleccionada() {
        return mascotaSeleccionada;
    }

    public void setMascotaSeleccionada(String mascotaSeleccionada) {
        this.mascotaSeleccionada = mascotaSeleccionada;
    }
}
