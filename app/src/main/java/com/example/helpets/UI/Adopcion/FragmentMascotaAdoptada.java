package com.example.helpets.ui.Adopcion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.helpets.R;
import com.example.helpets.ui.Menu.ActivityMenuPrincipal;


public class FragmentMascotaAdoptada extends Fragment implements View.OnClickListener {

    private ImageButton botonInicio;

    public FragmentMascotaAdoptada() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mascota_adoptada, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        botonInicio = (ImageButton)view.findViewById(R.id.botonAdoptarIrInicio);
        botonInicio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getActivity().setResult(ActivityMenuPrincipal.FORMULARIO_ENVIADO);
        getActivity().finish();
    }
}
