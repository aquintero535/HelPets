package com.example.helpets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpets.R;

import java.util.List;

public class AdaptadorWikipets extends RecyclerView.Adapter<ViewHolderWikipets> {

    private List<String> preguntas;
    private Context contexto;
    private RecyclerViewClickListener listener;

    public AdaptadorWikipets(List<String> preguntas, Context contexto, RecyclerViewClickListener listener) {
        this.preguntas = preguntas;
        this.contexto = contexto;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderWikipets onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater
                .from(contexto)
                .inflate(R.layout.lista_wikipets, parent, false);
        return new ViewHolderWikipets(item, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWikipets holder, int position) {
        holder.getPregunta().setText(preguntas.get(position));
    }

    @Override
    public int getItemCount() {
        return preguntas.size();
    }
}
