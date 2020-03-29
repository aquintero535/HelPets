package com.example.helpets.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpets.R;

public class ActivityMenuPrincipal extends AppCompatActivity {

    private Button botonMenu;
    private Button botonCitas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        botonMenu = (Button)findViewById(R.id.botonMenu);
        botonCitas = (Button)findViewById(R.id.botonCitas);
        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityMenuPrincipal.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });


        botonCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMenuPrincipal.this,
                        ActivityConectarVeterinario.class));
            }
        });
    }


}
