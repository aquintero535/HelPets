package com.example.helpets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMenuPrincipal extends AppCompatActivity {

    private Button botonMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        botonMenu = (Button)findViewById(R.id.botonMenu);
        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityMenuPrincipal.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
