package com.example.helpets.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpets.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderMascotas extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView nombreMascota;
    private TextView edadMascota;
    private TextView vacunasMascota;
    private CircleImageView imagenMascota;
    private RecyclerViewClickListener listener;

    public ViewHolderMascotas(@NonNull View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        nombreMascota = (TextView)itemView.findViewById(R.id.adopcionItemNombre);
        edadMascota = (TextView)itemView.findViewById(R.id.adopcionItemEdad);
        vacunasMascota = (TextView)itemView.findViewById(R.id.adopcionItemVacunas);
        imagenMascota = (CircleImageView)itemView.findViewById(R.id.imagenPerfilMascota);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    public TextView getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(TextView nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public TextView getEdadMascota() {
        return edadMascota;
    }

    public void setEdadMascota(TextView edadMascota) {
        this.edadMascota = edadMascota;
    }

    public TextView getVacunasMascota() {
        return vacunasMascota;
    }

    public void setVacunasMascota(TextView vacunasMascota) {
        this.vacunasMascota = vacunasMascota;
    }

    public CircleImageView getImagenMascota() {
        return imagenMascota;
    }

    public void setImagenMascota(CircleImageView imagenMascota) {
        this.imagenMascota = imagenMascota;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
    }
}
