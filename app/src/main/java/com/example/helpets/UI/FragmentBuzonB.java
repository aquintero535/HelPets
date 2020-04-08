package com.example.helpets.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.helpets.R;

public class FragmentBuzonB extends Fragment implements View.OnClickListener {


    private ImageButton botonInicio;

    public FragmentBuzonB() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buzon_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        botonInicio = (ImageButton)view.findViewById(R.id.botonBuzonIrInicio);
        botonInicio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getActivity().setResult(ActivityMenuPrincipal.MENSAJE_ENVIADO);
        getActivity().finish();
    }
}
