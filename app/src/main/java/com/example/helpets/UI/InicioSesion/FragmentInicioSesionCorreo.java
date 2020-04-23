package com.example.helpets.UI.InicioSesion;

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

import com.example.helpets.Callback;
import com.example.helpets.R;
import com.example.helpets.Usuario;
import com.example.helpets.UI.Menu.ActivityMenuPrincipal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentInicioSesionCorreo extends Fragment implements View.OnClickListener, Callback {

    private EditText campoCorreo, campoContrasenia;
    private Button botonIniciarSesion, botonNuevaCuenta;
    private FirebaseAuth sesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_sesion_correo, container, false);
    }

    /* Inicia los componentes */
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

    /* Método que se ejecuta al tocar un botón. Uno es para iniciar sesión con el correo, el otro
    es para iniciar el proceso de registro.
     */
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

    /* Método que inicia la sesión con Firebase. Obtiene el usuario y contraseña, comprueba si
    están vacíos y llama al método de Firebase para  iniciar sesión. Después, llama al método
    obtenerDatosUsuario, el cual a su regreso ejecutará datosObtenidos() o datosNoObtenidos()
     */
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
                                Usuario usuario = new Usuario();
                                usuario.registrarCallback(FragmentInicioSesionCorreo.this);
                                usuario.obtenerDatosUsuario();
                            } else{
                                Toast.makeText(getActivity(), task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else{
            Toast.makeText(getActivity(), "Por favor, llena todo los campos.", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    /* Métodos que se ejecutan después de llamar a Firebase para obtener algunos datos del usuario.
    Obtenidos o no, de igual manera llama al menú principal.
     */
    @Override
    public void datosObtenidos() {
        Toast.makeText(getActivity(), "Sesión iniciada",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), ActivityMenuPrincipal.class));
        getActivity().finish();
    }

    @Override
    public void datosNoObtenidos(Exception e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), ActivityMenuPrincipal.class));
        getActivity().finish();
    }
}
