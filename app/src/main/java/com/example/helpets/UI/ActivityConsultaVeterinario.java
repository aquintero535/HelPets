package com.example.helpets.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.helpets.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityConsultaVeterinario extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombreVeterinario;
    private RecyclerView rvMensajes;
    private EditText campoMensajeChat;
    private Button botonEnviarChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_veterinario);
        fotoPerfil = (CircleImageView)findViewById(R.id.fotoPerfil);
        nombreVeterinario = (TextView)findViewById(R.id.txtvNombreVeterinario);
        rvMensajes = (RecyclerView)findViewById(R.id.rvMensajes);
        campoMensajeChat = (EditText)findViewById(R.id.campoMensajeChat);
        botonEnviarChat = (Button)findViewById(R.id.botonEnviarChat);


    }
}
