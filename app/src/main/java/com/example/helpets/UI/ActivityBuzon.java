package com.example.helpets.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helpets.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityBuzon extends AppCompatActivity {

    private EditText campoNombre;
    private EditText campoTelefono;
    private EditText campoCorreo;
    private EditText campoMensaje;
    private Button botonEnviar;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzon);
        campoNombre = (EditText)findViewById(R.id.campoNombre);
        campoTelefono = (EditText)findViewById(R.id.campoTelefono);
        campoCorreo = (EditText)findViewById(R.id.campoCorreo);
        campoMensaje = (EditText)findViewById(R.id.campoMensaje);
        botonEnviar = (Button)findViewById(R.id.botonEnviar);
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje();
            }
        });
    }

    private void enviarMensaje(){
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

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("mensajes")
                    .add(mensaje)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            setResult(500);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(findViewById(R.id.layoutBuzon),
                                    "Ha ocurrido un error al enviar tu mensaje",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });
        } else{
            Toast.makeText(ActivityBuzon.this,
                    "Por favor, llena todos los campos.",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


}
