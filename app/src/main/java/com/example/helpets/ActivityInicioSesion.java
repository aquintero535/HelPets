package com.example.helpets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityInicioSesion extends AppCompatActivity {

    private Button botonRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        botonRegistrarse = (Button)findViewById(R.id.botonRegistrarse);
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityInicioSesion.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
