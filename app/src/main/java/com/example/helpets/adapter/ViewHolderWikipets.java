package com.example.helpets.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpets.R;

public class ViewHolderWikipets extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView pregunta;
    private RecyclerViewClickListener listener;


    public ViewHolderWikipets(@NonNull View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        this.listener = listener;
        pregunta = (TextView)itemView.findViewById(R.id.textoPregunta);
        itemView.setOnClickListener(this);
    }

    public TextView getPregunta() {
        return pregunta;
    }

    public void setPregunta(TextView pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
    }
}
