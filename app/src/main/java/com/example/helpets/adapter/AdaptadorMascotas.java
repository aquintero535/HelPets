package com.example.helpets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.helpets.R;
import com.example.helpets.model.Mascota;

import java.util.List;

public class AdaptadorMascotas extends RecyclerView.Adapter<ViewHolderMascotas> {

    private List<Mascota> listaMascotas;
    private Context contexto;
    private RecyclerViewClickListener recyclerViewClickListener;

    public AdaptadorMascotas(List<Mascota> listaMascotas, Context contexto,
                             RecyclerViewClickListener recyclerViewClickListener) {
        this.listaMascotas = listaMascotas;
        this.contexto = contexto;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    /* Crea una nueva vista para el item
    * Cada Item es un ViewHolder. */
    public ViewHolderMascotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater
                .from(contexto)
                .inflate(R.layout.lista_adopcion, parent, false);
        return new ViewHolderMascotas(item, recyclerViewClickListener);
    }

    /* Creado el ViewHolder, este método añade la información a cada ViewHolder, según su
    * posición.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderMascotas holder, int position) {
        holder.getNombreMascota().setText("Nombre: "+listaMascotas.get(position).getNombre());
        holder.getEdadMascota().setText("Edad: "+listaMascotas.get(position).getEdad());

        holder.getVacunasMascota().setText
                ("Vacunas: ".concat((listaMascotas.get(position).getVacunas())?"Sí":"No"));
        Glide.with(contexto)
                .load(listaMascotas.get(position).getImagenMascota())
                .into(holder.getImagenMascota());
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }
}
