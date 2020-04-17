package com.example.helpets.ui.Menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.helpets.R;
import com.example.helpets.ui.Adopcion.ActivityAdopta;
import com.example.helpets.ui.Buzon.ActivityBuzon;
import com.example.helpets.ui.CitasVeterinarias.FragmentCitas;

public class FragmentMenuPrincipal extends Fragment implements View.OnClickListener {

    private Button botonCitas;
    private Button botonAdoptar;
    private Button botonBuzon;
    private final int MESSAGE_SENT = 500;


    public FragmentMenuPrincipal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        botonCitas = (Button)view.findViewById(R.id.botonCitas);
        botonAdoptar = (Button)view.findViewById(R.id.botonAdopta);
        botonBuzon = (Button)view.findViewById(R.id.botonBuzon);

        botonCitas.setOnClickListener(this);
        botonAdoptar.setOnClickListener(this);
        botonBuzon.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.botonCitas:
                Fragment nuevoFragment = new FragmentCitas();
                FragmentTransaction transicion = this.getFragmentManager().beginTransaction();
                transicion.replace(R.id.fragmentMenuPrincipal, nuevoFragment);
                transicion.addToBackStack(null);
                transicion.commit();
                break;

            case R.id.botonAdopta:
                startActivityForResult(new Intent(getContext(), ActivityAdopta.class), 678);
                break;
            case R.id.botonBuzon:
                startActivityForResult(new Intent(getContext(), ActivityBuzon.class), MESSAGE_SENT);

        }
    }


}
