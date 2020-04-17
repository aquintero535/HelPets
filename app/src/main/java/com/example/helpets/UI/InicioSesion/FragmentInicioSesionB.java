package com.example.helpets.ui.InicioSesion;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.ui.Menu.ActivityMenuPrincipal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentInicioSesionB extends Fragment implements View.OnClickListener {

    private EditText campoCorreo, campoContrasenia;
    private Button botonIniciarSesion, botonNuevaCuenta;
    private FirebaseAuth sesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_sesion_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        campoCorreo = (EditText)view.findViewById(R.id.campoCorreoInicioSesion);
        campoContrasenia = (EditText)view.findViewById(R.id.campoContraseniaInicioSesion);
        botonIniciarSesion = (Button)view.findViewById(R.id.botonIniciarSesion);
        botonNuevaCuenta = (Button)view.findViewById(R.id.botonNuevaCuenta);
        botonIniciarSesion.setOnClickListener(this);
        botonNuevaCuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonIniciarSesion:
                iniciarSesion();
                break;
            case R.id.botonNuevaCuenta:
                startActivity(new Intent(getActivity(),
                        ActivityRegistro.class));
                getActivity().finish();
                break;
        }
    }

    private void iniciarSesion(){
        sesion = FirebaseAuth.getInstance();
        String correo = campoCorreo.getText().toString();
        String contrasenia = campoContrasenia.getText().toString();
        if (!(correo.isEmpty() || contrasenia.isEmpty())) {
            sesion.signInWithEmailAndPassword(correo, contrasenia)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(), "Sesión iniciada",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), ActivityMenuPrincipal.class));
                                getActivity().finish();
                            } else{
                                Toast.makeText(getActivity(), "Inicio de sesión fallido",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else{
            Toast.makeText(getActivity(), "Por favor, llena todo los campos.", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
