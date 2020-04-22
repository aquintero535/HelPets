package com.example.helpets.ui.Adopcion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.helpets.R;
import com.example.helpets.viewmodel.ViewModelAdoptar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class FragmentFormularioAdopcion extends Fragment implements View.OnClickListener {

    private ViewModelAdoptar viewModelAdoptar;
    private EditText campoNombreFormulario;
    private EditText campoTelefonoFormulario;
    private EditText campoCorreoFormulario;
    private EditText campoDireccionFormulario;
    private EditText campoMascotaFormulario;
    private EditText campoMotivoFormulario;
    private EditText campoPoderAdquisitivoFormulario;
    private EditText campoReferenciaPersonalFormulario;
    private Button botonEnviarFormulario;
    private CheckBox politicasAdopcion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario_adopcion, container, false);
    }

    //Inicia los componentes.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelAdoptar = new ViewModelProvider(getActivity()).get(ViewModelAdoptar.class);
        campoNombreFormulario = (EditText)view.findViewById(R.id.campoNombreFormulario);
        campoTelefonoFormulario = (EditText)view.findViewById(R.id.campoTelefonoFormulario);
        campoCorreoFormulario = (EditText)view.findViewById(R.id.campoCorreoFormulario);
        campoDireccionFormulario = (EditText)view.findViewById(R.id.campoDireccionFormulario);
        campoMascotaFormulario = (EditText)view.findViewById(R.id.campoMascotaFormulario);
        campoMotivoFormulario = (EditText)view.findViewById(R.id.campoMotivoFormulario);
        campoPoderAdquisitivoFormulario = (EditText)view.findViewById(R.id.campoPoderAdquisitivoFormulario);
        campoReferenciaPersonalFormulario = (EditText)view.findViewById(R.id.campoReferenciaPersonalFormulario);
        botonEnviarFormulario = (Button)view.findViewById(R.id.botonEnviarFormulario);
        politicasAdopcion = (CheckBox)view.findViewById(R.id.politicasAdopcion);
        botonEnviarFormulario.setOnClickListener(this);
    }

    //Método que se ejecuta al tocar el botón Enviar. Obtiene los strings de los campos del
    //formulario, y comprueba si están vacíos. Si lo están, o no se han aceptado las políticas de
    //adopción de Helpets, muestra un Toast que avisa al usuario que debe rellenar los campos o
    //aceptar las políticas. De haberlo hecho, envía a la base de datos un HashMap con todos los
    //datos del formulario.
    @Override
    public void onClick(View v) {
        //Envía el formulario a la base de datos
        String nombre = campoNombreFormulario.getText().toString();
        String telefono = campoTelefonoFormulario.getText().toString();
        String correo = campoCorreoFormulario.getText().toString();
        String direccion = campoDireccionFormulario.getText().toString();
        String mascota = campoMascotaFormulario.getText().toString();
        String motivo = campoMotivoFormulario.getText().toString();
        String poderAdquisitivo = campoPoderAdquisitivoFormulario.getText().toString();
        String referenciaPersonal = campoReferenciaPersonalFormulario.getText().toString();

        if (!(nombre.isEmpty() && telefono.isEmpty() && correo.isEmpty() && direccion.isEmpty() &&
        mascota.isEmpty() && motivo.isEmpty() && poderAdquisitivo.isEmpty() &&
        referenciaPersonal.isEmpty())) {
            if (politicasAdopcion.isChecked()) {
                Map<String, String> formulario = new HashMap<>();
                formulario.put("nombre", nombre);
                formulario.put("telefono", telefono);
                formulario.put("correo", correo);
                formulario.put("direccion", direccion);
                formulario.put("nombre_mascota", mascota);
                formulario.put("mascota_ID", viewModelAdoptar.getMascotaSeleccionada());
                formulario.put("motivo", motivo);
                formulario.put("poder_adquisitivo", poderAdquisitivo);
                formulario.put("referencia_personal", referenciaPersonal);
                viewModelAdoptar.getDb().collection("formularios_adopcion")
                        .add(formulario)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.contenedorFragmentsAdoptar, new FragmentMascotaAdoptada())
                                        .commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(getView(), "Ha ocurrido un error al enviar el formulario.",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
            } else{
                Toast.makeText(getContext(), "Acepte las políticas de adopción para" +
                        " continuar.", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(getContext(), "Rellene todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }
}
