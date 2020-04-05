package com.example.helpets.adapter;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.helpets.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderVeterinario extends RecyclerView.ViewHolder implements View.OnClickListener {
    private  TextView nombreVeterinario;
    private  TextView clientesSatisfechos;
    private  CircleImageView imagenPerfilVeterinario;
    private RecyclerViewClickListener recyclerViewClickListener;

    public HolderVeterinario(View item, RecyclerViewClickListener listener){
        super(item);
        nombreVeterinario = (TextView)item.findViewById(R.id.listaTexto1);
        clientesSatisfechos = (TextView)item.findViewById(R.id.listaTexto2);
        imagenPerfilVeterinario = (CircleImageView)item.findViewById(R.id.imgPerfilVeterinario);
        recyclerViewClickListener = listener;
        item.setOnClickListener(this);
    }

    public TextView getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(TextView nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public TextView getClientesSatisfechos() {
        return clientesSatisfechos;
    }

    public void setClientesSatisfechos(TextView clientesSatisfechos) {
        this.clientesSatisfechos = clientesSatisfechos;
    }

    public CircleImageView getImagenPerfilVeterinario() {
        return imagenPerfilVeterinario;
    }

    public void setImagenPerfilVeterinario(CircleImageView imagenPerfilVeterinario) {
        this.imagenPerfilVeterinario = imagenPerfilVeterinario;
    }

    @Override
    public void onClick(View v) {
        recyclerViewClickListener.onClick(v, getAdapterPosition());
    }
}
