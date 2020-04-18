package com.example.helpets.ui.Opciones;

import android.app.Activity;
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
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;


public class FragmentDetallesUsuario extends Fragment implements View.OnClickListener {

    private ImageView imagenPerfilUsuario;
    private TextView nombreUsuarioOpciones;
    private Button botonOpcionesUsuario;
    private Button botonCerrarSesion;
    private Button botonCambiarFotoPerfil;
    public static final int RC_SIGN_IN = 9001;

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
                reautenticar();
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

    private void reautenticar(){
        Intent inicioSesion = AuthUI.getInstance().createSignInIntentBuilder().build();
        startActivityForResult(inicioSesion, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                getActivity().getSupportFragmentManager().beginTransaction().replace
                        (R.id.contenedorOpciones, new FragmentOpciones()).commit();
            } else if (resultCode == Activity.RESULT_CANCELED){
                Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                        "Inicia sesión para cambiar las opciones de tu cuenta",
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
