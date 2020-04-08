package com.example.helpets.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpets.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegistro extends AppCompatActivity implements View.OnClickListener {

    private EditText campoNombreRegistro, campoTelefonoRegistro, campoCorreoRegistro,
    campoContraseniaRegistro, campoTipoMascotaRegistro, campoNombreMascotaRegistro,
            campoEdadMascotaRegistro;
    private ImageButton botonEnviarRegistro;
    private CheckBox politicasServicioHelpets;
    private FirebaseFirestore db;
    private FirebaseAuth sesion;
    private Map<String, String> nuevoUsuario;
    private final String fotoPerfilDefault = "https://firebasestorage.googleapis.com/v0/b/helpets-4bc74" +
            ".appspot.com/o/foto_perfil_usuarios%2Fuser_profile_default.jpg?alt=media" +
            "&token=f32f295a-1833-492b-afdf-9778f7b092f5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        campoNombreRegistro = (EditText)findViewById(R.id.campoNombreRegistro);
        campoTelefonoRegistro = (EditText)findViewById(R.id.campoTelefonoRegistro);
        campoCorreoRegistro = (EditText)findViewById(R.id.campoCorreoRegistro);
        campoContraseniaRegistro = (EditText)findViewById(R.id.campoContraseniaRegistro);
        campoTipoMascotaRegistro = (EditText)findViewById(R.id.campoTipoMascotaRegistro);
        campoNombreMascotaRegistro = (EditText)findViewById(R.id.campoNombreRegistro);
        campoEdadMascotaRegistro = (EditText)findViewById(R.id.campoEdadMascotaRegistro);
        botonEnviarRegistro = (ImageButton)findViewById(R.id.botonEnviarRegistro);
        politicasServicioHelpets = (CheckBox)findViewById(R.id.politicasServicioHelpets);
        db = FirebaseFirestore.getInstance();
        botonEnviarRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nombre = campoNombreRegistro.getText().toString();
        String telefono = campoTelefonoRegistro.getText().toString();
        String correo = campoCorreoRegistro.getText().toString();
        String contrasenia = campoContraseniaRegistro.getText().toString();
        String tipoMascota = campoTipoMascotaRegistro.getText().toString();
        String nombreMascota = campoNombreMascotaRegistro.getText().toString();
        String edadMascota = campoEdadMascotaRegistro.getText().toString();

        if (!(nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || contrasenia.isEmpty() ||
        tipoMascota.isEmpty() || nombreMascota.isEmpty() || edadMascota.isEmpty())){
            if (politicasServicioHelpets.isChecked()){
                nuevoUsuario = new HashMap<>();
                nuevoUsuario.put("nombre", nombre);
                nuevoUsuario.put("telefono", telefono);
                nuevoUsuario.put("correo", correo);
                nuevoUsuario.put("contrasenia", contrasenia);
                nuevoUsuario.put("tipoMascota", tipoMascota);
                nuevoUsuario.put("nombreMascota", nombreMascota);
                nuevoUsuario.put("edadMascota", edadMascota);
                registrarUsuario();
            } else{
                Toast.makeText(this, "Por favor, acepte las políticas de servicio" +
                        " para continuar", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Por favor, llene todo los campos.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void registrarUsuario(){
        // Registro de usuario
        sesion = FirebaseAuth.getInstance();
        sesion.createUserWithEmailAndPassword(nuevoUsuario.get("correo"), nuevoUsuario.get("contrasenia"))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            nuevoUsuario.put("uid", task.getResult().getUser().getUid());
                            subirUsuarioDB();
                        } else{
                            Snackbar.make(findViewById(R.id.activityRegistro), "Ha ocurrido" +
                                    " un error al registrarte. " +
                                    "Verifica que tus datos sean correctos.", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    private void subirUsuarioDB(){
        db.collection("usuarios")
                .document(nuevoUsuario.get("uid"))
                .set(nuevoUsuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            crearPerfil();
                        }
                    }
                });
    }

    private void crearPerfil(){
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest nuevoPerfil = new UserProfileChangeRequest.Builder()
                .setDisplayName(nuevoUsuario.get("nombre"))
                .setPhotoUri(Uri.parse(fotoPerfilDefault))
                .build();
        usuario.updateProfile(nuevoPerfil).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(ActivityRegistro.this,
                            ActivityMenuPrincipal.class));
                    finish();
                }
            }
        });
    }
}
