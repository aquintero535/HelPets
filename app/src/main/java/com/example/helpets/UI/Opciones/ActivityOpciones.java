package com.example.helpets.ui.Opciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.helpets.R;

public class ActivityOpciones extends AppCompatActivity {

    private ImageButton botonRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorOpciones,
                new FragmentDetallesUsuario()).commit();
        botonRegresar = (ImageButton)findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
