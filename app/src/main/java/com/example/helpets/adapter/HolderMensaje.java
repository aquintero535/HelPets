package com.example.helpets.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.helpets.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderMensaje extends RecyclerView.ViewHolder {

    private TextView nombreUsuario;
    private TextView mensajeChat;
    private TextView hora;
    private CircleImageView fotoPerfilMensaje;
    private ImageView mensajeImagen;

    public HolderMensaje(View itemView){
        super(itemView);
        nombreUsuario = (TextView)itemView.findViewById(R.id.txtvNombreUsuario);
        mensajeChat = (TextView)itemView.findViewById(R.id.txtvMensaje);
        hora = (TextView)itemView.findViewById(R.id.txtvHora);
        fotoPerfilMensaje = (CircleImageView) itemView.findViewById(R.id.fotoPerfilMensaje);
        mensajeImagen = (ImageView)itemView.findViewById(R.id.mensajeImagen);
    }

    public TextView getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(TextView nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public TextView getMensajeChat() {
        return mensajeChat;
    }

    public void setMensajeChat(TextView mensajeChat) {
        this.mensajeChat = mensajeChat;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotoPerfilMensaje() {
        return fotoPerfilMensaje;
    }

    public void setFotoPerfilMensaje(CircleImageView fotoPerfilMensaje) {
        this.fotoPerfilMensaje = fotoPerfilMensaje;
    }

    public ImageView getMensajeImagen() {
        return mensajeImagen;
    }

    public void setMensajeImagen(ImageView mensajeImagen) {
        this.mensajeImagen = mensajeImagen;
    }
}
