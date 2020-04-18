package com.example.helpets.ui.Opciones;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.helpets.MainActivity;
import com.example.helpets.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


public class FragmentDetallesUsuario extends Fragment implements View.OnClickListener {

    private ImageView imagenPerfilUsuario;
    private TextView nombreUsuarioOpciones;
    private Button botonOpcionesUsuario;
    private Button botonCerrarSesion;
    private Button botonCambiarFotoPerfil;

    public FragmentDetallesUsuario() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalles_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagenPerfilUsuario = (ImageView)view.findViewById(R.id.imagenPerfilUsuario);
        nombreUsuarioOpciones = (TextView)view.findViewById(R.id.txtvNombreUsuarioOpciones);

        Glide.with(view).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(imagenPerfilUsuario);
        nombreUsuarioOpciones.setText("Nombre de usuario: "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        botonOpcionesUsuario = (Button)view.findViewById(R.id.botonOpcionesCuenta);
        botonCambiarFotoPerfil = (Button)view.findViewById(R.id.botonCambiarImagen);
        botonCerrarSesion = (Button)view.findViewById(R.id.botonCerrarSesion);
        botonOpcionesUsuario.setOnClickListener(this);
        botonCambiarFotoPerfil.setOnClickListener(this);
        botonCerrarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonOpcionesCuenta:
                getActivity().getSupportFragmentManager().beginTransaction().replace
                        (R.id.contenedorOpciones, new FragmentOpciones()).commit();
                break;
            case R.id.botonCambiarImagen:
                break;
            case R.id.botonCerrarSesion:
                FirebaseAuth.getInstance().signOut();
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                break;
        }
    }
}
