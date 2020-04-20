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

    private int pregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wikipets_info);
        botonRegresar = (ImageButton)findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(this);
        imagenInfo = (ImageView)findViewById(R.id.imagenInformacion);
        textoInfoTitulo = (TextView)findViewById(R.id.textoInformacionTitulo);
        textoInfoCuerpo = (TextView)findViewById(R.id.textoInformacionCuerpo);
        pregunta = getIntent().getIntExtra("pregunta", 0);
        llenarInfo();
    }

    private void llenarInfo(){
        switch (pregunta){
            case 0:
                imagenInfo.setImageResource(R.drawable.pregunta0_imagen);
                textoInfoTitulo.setText(R.string.pregunta0);
                textoInfoCuerpo.setText(R.string.pregunta0Info);
                break;
            case 1:
                imagenInfo.setImageResource(R.drawable.pregunta1_imagen);
                textoInfoTitulo.setText(R.string.pregunta1);
                textoInfoCuerpo.setText(R.string.pregunta1Info);
                break;
            case 2:
                imagenInfo.setImageResource(R.drawable.pregunta2_imagen);
                textoInfoTitulo.setText(R.string.pregunta2);
                textoInfoCuerpo.setText(R.string.pregunta2Info);
                break;
            case 3:
                imagenInfo.setImageResource(R.drawable.pregunta3_imagen);
                textoInfoTitulo.setText(R.string.pregunta3);
                textoInfoCuerpo.setText(R.string.pregunta3Info);
                break;
            case 4:
                imagenInfo.setImageResource(R.drawable.pregunta4_imagen);
                textoInfoTitulo.setText(R.string.pregunta4);
                textoInfoCuerpo.setText(R.string.pregunta4Info);
                break;
            case 5:
                imagenInfo.setImageResource(R.drawable.pregunta5_imagen);
                textoInfoTitulo.setText(R.string.pregunta5);
                textoInfoCuerpo.setText(R.string.pregunta5Info);
                break;
            case 6:
                imagenInfo.setImageResource(R.drawable.pregunta6_imagen);
                textoInfoTitulo.setText(R.string.pregunta6);
                textoInfoCuerpo.setText(R.string.pregunta6Info);
                break;
            case 7:
                imagenInfo.setImageResource(R.drawable.pregunta7_imagen);
                textoInfoTitulo.setText(R.string.pregunta7);
                textoInfoCuerpo.setText(R.string.pregunta7Info);
                break;
            case 8:
                imagenInfo.setImageResource(R.drawable.pregunta8_imagen);
                textoInfoTitulo.setText(R.string.pregunta8);
                textoInfoCuerpo.setText(R.string.pregunta8Info);
                break;
            case 9:
                imagenInfo.setImageResource(R.drawable.pregunta9_imagen);
                textoInfoTitulo.setText(R.string.pregunta9);
                textoInfoCuerpo.setText(R.string.pregunta9Info);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
