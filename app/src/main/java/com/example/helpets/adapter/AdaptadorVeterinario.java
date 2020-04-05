package com.example.helpets.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.helpets.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorVeterinario extends ArrayAdapter<Veterinario> {

    public AppCompatActivity appCompatActivity;
    private TextView nombreVeterinario;
    private TextView clientesSatisfechos;
    private CircleImageView imagenPerfilVeterinario;
    private List<Veterinario> listaVeterinarios;

    public AdaptadorVeterinario(AppCompatActivity contexto, List<Veterinario> lista){
        super(contexto, R.layout.lista_veterinarios, lista);
        appCompatActivity = contexto;
        listaVeterinarios = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = appCompatActivity.getLayoutInflater();
        View item = inflater.inflate(R.layout.lista_veterinarios, null);
        nombreVeterinario = (TextView)item.findViewById(R.id.listaTexto1);
        clientesSatisfechos = (TextView)item.findViewById(R.id.listaTexto2);
        imagenPerfilVeterinario = (CircleImageView)item.findViewById(R.id.imgPerfilVeterinario);

        //Establece el nombre, los clientes y la imagen de perfil del veterinario en los componentes
        //del UI.
        nombreVeterinario.setText(listaVeterinarios.get(position).getNombreVeterinario());
        clientesSatisfechos.setText(listaVeterinarios.get(position).getClientes());
        Glide.with(item)
                .load(listaVeterinarios.get(position).getFotoPerfil())
                .into(imagenPerfilVeterinario);

        return item;
    }
}
