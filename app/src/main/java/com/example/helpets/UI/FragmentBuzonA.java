package com.example.helpets.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.helpets.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class FragmentBuzonA extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private EditText campoNombre;
    private EditText campoTelefono;
    private EditText campoCorreo;
    private EditText campoMensaje;
    private Button botonEnviar;
    private FrameLayout fragmentBuzon;

    public FragmentBuzonA() {
        // Required empty public constructor
    }

    public static FragmentBuzonA newInstance(String param1, String param2) {
        FragmentBuzonA fragment = new FragmentBuzonA();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buzon_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        campoNombre = (EditText)view.findViewById(R.id.campoNombre);
        campoTelefono = (EditText) view.findViewById(R.id.campoTelefono);
        campoCorreo = (EditText)view.findViewById(R.id.campoCorreo);
        campoMensaje = (EditText)view.findViewById(R.id.campoMensaje);
        botonEnviar = (Button)view.findViewById(R.id.botonEnviar);
        fragmentBuzon = (FrameLayout)view.findViewById(R.id.fragmentBuzon);
        botonEnviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
     enviarMensaje();
    }

    public void enviarMensaje(){
        String nombre = campoNombre.getText().toString();
        String telefono = campoTelefono.getText().toString();
        String correo = campoCorreo.getText().toString();
        String textoMensaje = campoMensaje.getText().toString();
        if (!(nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || textoMensaje.isEmpty())){
            Map<String, String> mensaje = new HashMap<>();
            mensaje.put("nombre", campoNombre.getText().toString());
            mensaje.put("telefono", campoTelefono.getText().toString());
            mensaje.put("correo", campoCorreo.getText().toString());
            mensaje.put("mensaje", campoMensaje.getText().toString());
            mensaje.put("idUsuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("contacto")
                    .add(mensaje)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentBuzon, new FragmentBuzonB())
                                    .commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(getView().findViewById(R.id.layoutBuzon),
                                    "Ha ocurrido un error al enviar tu mensaje",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });
        } else{
            Toast.makeText(getActivity(),
                    "Por favor, llena todos los campos.",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


}
