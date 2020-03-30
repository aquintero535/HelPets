package com.example.helpets.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.helpets.R;
import com.google.android.material.snackbar.Snackbar;

public class ActivityBuzon extends AppCompatActivity {

    private EditText campoNombre;
    private EditText campoTelefono;
    private EditText campoCorreo;
    private EditText campoMensaje;
    private Button botonEnviar;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzon);
        campoNombre = (EditText)findViewById(R.id.campoNombre);
        campoTelefono = (EditText)findViewById(R.id.campoTelefono);
        campoCorreo = (EditText)findViewById(R.id.campoCorreo);
        campoMensaje = (EditText)findViewById(R.id.campoMensaje);
        botonEnviar = (Button)findViewById(R.id.botonEnviar);

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(500);
                finish();
            }
        });

    }
}
