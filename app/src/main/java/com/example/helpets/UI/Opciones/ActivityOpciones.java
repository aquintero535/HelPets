package com.example.helpets.UI.Opciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.helpets.R;

public class ActivityOpciones extends AppCompatActivity {

    private ImageButton botonRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        //Fragment con los datos del usuario.
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorOpciones,
                new FragmentDetallesUsuario()).commit();
        botonRegresar = (ImageButton)findViewById(R.id.botonRegresar);

        //Para finalizar la actividad.
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
