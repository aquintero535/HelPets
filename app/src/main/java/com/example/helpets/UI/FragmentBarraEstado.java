package com.example.helpets.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpets.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;

public class FragmentBarraEstado extends Fragment {
    private Button botonMenu;
    private TextView estadoFecha;
    private TextView estadoUsuario;


    public FragmentBarraEstado() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
       botonMenu = (Button)getView().findViewById(R.id.botonMenu);
       botonMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getContext(), "Prueba exitosa", Toast.LENGTH_SHORT).show();
           }
       });
        estadoFecha = (TextView)view.findViewById(R.id.barraEstadoFecha);
        estadoUsuario = (TextView)view.findViewById(R.id.barraEstadoUsuario);
        estadoUsuario.setText("Hola "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    }

    @Override
    public void onResume() {
        super.onResume();
        estadoFecha.setText(DateFormat.getDateTimeInstance().format(Timestamp.now().toDate()));
    }
}
