package com.example.helpets.UI.Buzon;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.helpets.R;


public class ActivityBuzon extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzon);

        //Coloca el fragmento del formulario de contacto.
        getSupportFragmentManager().beginTransaction().add
                (R.id.fragmentBuzon, new FragmentBuzonFormulario()).commit();
    }


}
