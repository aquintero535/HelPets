package com.example.helpets.UI.Buzon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.helpets.R;
import com.example.helpets.UI.Menu.ActivityMenuPrincipal;

public class FragmentBuzonEnviado extends Fragment implements View.OnClickListener {


    private ImageButton botonInicio;

    public FragmentBuzonEnviado() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buzon_enviado, container, false);
    }

    /* Inicia los componentes */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        botonInicio = (ImageButton)view.findViewById(R.id.botonBuzonIrInicio);
        botonInicio.setOnClickListener(this);
    }

    /* Al tocar un botón, regresa al menú principal y le envía un código de resultado para ejecutar
    un método que muestra un Snackbar.
     */
    @Override
    public void onClick(View v) {
        getActivity().setResult(ActivityMenuPrincipal.MENSAJE_ENVIADO);
        getActivity().finish();
    }
}
