package com.example.helpets.UI.Wikipets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.helpets.R;
import com.example.helpets.adapter.AdaptadorWikipets;
import com.example.helpets.adapter.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityWikipets extends AppCompatActivity implements RecyclerViewClickListener {

    private RecyclerView listaWikipets;
    private List<String> listaPreguntas = new ArrayList<>();
    private RecyclerViewClickListener listener;

    /* Inicia los componentes. Crea una lista para el RecyclerView con las preguntas de la Wikipets */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wikipets);
        listaWikipets = (RecyclerView)findViewById(R.id.rvWikipets);
        listaWikipets.setLayoutManager(new LinearLayoutManager(this));
        listaPreguntas.add(getString(R.string.pregunta0));
        listaPreguntas.add(getString(R.string.pregunta1));
        listaPreguntas.add(getString(R.string.pregunta2));
        listaPreguntas.add(getString(R.string.pregunta3));
        listaPreguntas.add(getString(R.string.pregunta4));
        listaPreguntas.add(getString(R.string.pregunta5));
        listaPreguntas.add(getString(R.string.pregunta6));
        listaPreguntas.add(getString(R.string.pregunta7));
        listaPreguntas.add(getString(R.string.pregunta8));
        listaPreguntas.add(getString(R.string.pregunta9));

        //Al tocar un item de la lista
        listener = this;

        AdaptadorWikipets adaptador = new AdaptadorWikipets(listaPreguntas, this, listener);
        listaWikipets.setAdapter(adaptador);
    }

    //Se ejecuta al tocar un item de la lista.
    @Override
    public void onClick(View view, int position) {
        Intent abrirPregunta = new Intent(this, ActivityWikipetsInfo.class);
        abrirPregunta.putExtra("pregunta", position);
        startActivity(abrirPregunta);
    }
}
