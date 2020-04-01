package com.example.helpets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.helpets.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorMensajes extends RecyclerView.Adapter<HolderMensaje> {

    private List<Mensaje> listaMensajes = new ArrayList<>();
    private Context contexto;

    public AdaptadorMensajes(Context contexto) {
        this.contexto = contexto;
    }

    public void aniadirMensaje(Mensaje mensaje){
        listaMensajes.add(mensaje);
        notifyItemInserted(listaMensajes.size());
    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(contexto)
                .inflate(R.layout.mensajes_consulta_layout,
                        parent,
                        false);
        return new HolderMensaje(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {
        holder.getNombreUsuario().setText(listaMensajes.get(position).getNombreUsuario());
        holder.getMensajeChat().setText(listaMensajes.get(position).getMensaje());
        holder.getHora().setText(listaMensajes.get(position).getHora());
        if (listaMensajes.get(position).getTipoMensaje().equals("2")){
            holder.getMensajeImagen().setVisibility(View.VISIBLE);
            holder.getMensajeChat().setVisibility(View.VISIBLE);
            Glide.with(contexto).load(listaMensajes.get(position).getUrlFoto()).into
                    (holder.getMensajeImagen());
        } else if (listaMensajes.get(position).getTipoMensaje().equals("1")){
            holder.getMensajeImagen().setVisibility(View.GONE);
            holder.getMensajeChat().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }
}
