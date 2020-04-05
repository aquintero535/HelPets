package com.example.helpets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.helpets.R;

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
    public ViewHolderMascotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater
                .from(contexto)
                .inflate(R.layout.lista_adopcion, parent, false);
        return new ViewHolderMascotas(item, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMascotas holder, int position) {
        holder.getNombreMascota().setText(listaMascotas.get(position).getNombre());
        holder.getEdadMascota().setText(listaMascotas.get(position).getEdad());
        holder.getVacunasMascota().setText(listaMascotas.get(position).getVacunas());
        Glide.with(contexto)
                .load(listaMascotas.get(position).getImagenMascota())
                .into(holder.getImagenMascota());
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }
}
