package com.example.helpets.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helpets.R;
import com.example.helpets.Usuario;
import com.example.helpets.UI.Opciones.ActivityOpciones;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;

public class FragmentBarraEstado extends Fragment {
    private Button botonMenu;
    private TextView estadoFecha;
    private TextView estadoUsuario;
    private TextView modoVeterinario;


    public FragmentBarraEstado() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barra_estado, container, false);
    }

    /* Llama a la actividad de opciones si el usuario toca el botón. Si el usuario es un veterinario,
    * muestra un TextView informando que es un veterinario.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
       botonMenu = (Button)getView().findViewById(R.id.botonMenu);
       botonMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().startActivity(new Intent(getActivity(), ActivityOpciones.class));
           }
       });
        estadoFecha = (TextView)view.findViewById(R.id.barraEstadoFecha);
        estadoUsuario = (TextView)view.findViewById(R.id.barraEstadoUsuario);
        estadoUsuario.setText("Hola "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        modoVeterinario = (TextView)view.findViewById(R.id.txtvModoVeterinario);
        if (Usuario.getTipoUsuario() == Usuario.VETERINARIO){
            modoVeterinario.setVisibility(View.VISIBLE);
        } else{
            modoVeterinario.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        estadoFecha.setText(DateFormat.getDateTimeInstance().format(Timestamp.now().toDate()));
    }
}
