package com.example.helpets.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.helpets.R;
import com.example.helpets.adapter.AdaptadorMensajes;
import com.example.helpets.adapter.Mensaje;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityConsultaVeterinario extends AppCompatActivity
        implements View.OnClickListener {

    private CircleImageView fotoPerfil;
    private TextView nombreVeterinario;
    private RecyclerView rvMensajes;
    private EditText campoMensajeChat;
    private Button botonEnviarChat;
    private AdaptadorMensajes adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_veterinario);
        fotoPerfil = (CircleImageView)findViewById(R.id.fotoPerfil);
        nombreVeterinario = (TextView)findViewById(R.id.txtvNombreVeterinario);
        rvMensajes = (RecyclerView)findViewById(R.id.rvMensajes);
        campoMensajeChat = (EditText)findViewById(R.id.campoMensajeChat);
        botonEnviarChat = (Button)findViewById(R.id.botonEnviarChat);

        adaptador = new AdaptadorMensajes(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(linearLayoutManager);
        rvMensajes.setAdapter(adaptador);

        botonEnviarChat.setOnClickListener(this);

        adaptador.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });
    }

    @Override
    public void onClick(View v) {
        adaptador.aniadirMensaje(new Mensaje(
                nombreVeterinario.getText().toString(),
                campoMensajeChat.getText().toString(),
                "",
                "1",
                "3:21 pm"));

    }

    private void setScrollbar(){
        rvMensajes.scrollToPosition(adaptador.getItemCount()-1);
    }
}
