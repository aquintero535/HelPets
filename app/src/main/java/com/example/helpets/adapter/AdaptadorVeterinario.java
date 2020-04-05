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



public class AdaptadorVeterinario extends RecyclerView.Adapter<HolderVeterinario> {

    private List<Veterinario> listaVeterinarios;
    private Context contexto;
    private RecyclerViewClickListener recyclerViewClickListener;

    public AdaptadorVeterinario(Context contexto, List<Veterinario> lista,
                                RecyclerViewClickListener listener){
        this.contexto = contexto;
        listaVeterinarios = lista;
        recyclerViewClickListener = listener;
    }

    @NonNull
    @Override
    public HolderVeterinario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater
                .from(contexto)
                .inflate(R.layout.lista_veterinarios, parent, false);
        return new HolderVeterinario(item, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderVeterinario holder, int position) {
        holder.getNombreVeterinario().setText(listaVeterinarios.get(position).getNombreVeterinario());
        holder.getClientesSatisfechos().setText(listaVeterinarios.get(position).getClientes());
        Glide.with(contexto)
                .load(listaVeterinarios.get(position).getFotoPerfil())
                .into(holder.getImagenPerfilVeterinario());
    }

    @Override
    public int getItemCount() {
        return listaVeterinarios.size();
    }
}
