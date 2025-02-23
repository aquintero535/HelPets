package com.example.helpets.UI.CitasVeterinarias;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.helpets.R;


public class FragmentCitas extends Fragment implements View.OnClickListener {

    private Button botonCitas24Hr;

    public FragmentCitas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_citas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        botonCitas24Hr = (Button)view.findViewById(R.id.botonCitas24Hr);
        botonCitas24Hr.setOnClickListener(this);
    }

    /* Método que se ejecuta al tocar un botón. Comienza una nueva actividad */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonCitas24Hr:
                startActivity(new Intent(getContext(), ActivityConectarVeterinario.class));
                break;
        }
    }
}
