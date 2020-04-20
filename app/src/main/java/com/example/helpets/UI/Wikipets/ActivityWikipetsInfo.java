package com.example.helpets.ui.Wikipets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helpets.R;

public class ActivityWikipetsInfo extends AppCompatActivity implements View.OnClickListener {

    private ImageButton botonRegresar;
    private ImageView imagenInfo;
    private TextView textoInfoTitulo;
    private TextView textoInfoCuerpo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wikipets_info);
        botonRegresar = (ImageButton)findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(this);
        imagenInfo = (ImageView)findViewById(R.id.imagenInformacion);
        textoInfoTitulo = (TextView)findViewById(R.id.textoInformacionTitulo);
        textoInfoCuerpo = (TextView)findViewById(R.id.textoInformacionCuerpo);
        llenarInfo();
    }

    //Llena la información según la pregunta escogida por el usuario. 
    private void llenarInfo(){
        //Obtengo el número de pregunta.
        int preguntaId = getIntent().getIntExtra("pregunta", 0);

        String url = "drawable/"+"pregunta"+ preguntaId +"_imagen"; //Dirección de la imagen.
        int imagenId = getResources().getIdentifier(url, "drawable", getPackageName());
        url = "pregunta"+ preguntaId; //Dirección del string que contiene el título.
        int tituloId = getResources().getIdentifier(url, "string", getPackageName());
        url = url.concat("Info"); //Dirección del string que contiene el cuerpo.
        int cuerpoId = getResources().getIdentifier(url, "string", getPackageName());

        //Añado la imagen y string a las View.
        imagenInfo.setImageResource(imagenId);
        textoInfoTitulo.setText(tituloId);
        textoInfoCuerpo.setText(cuerpoId);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
