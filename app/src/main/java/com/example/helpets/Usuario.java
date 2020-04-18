package com.example.helpets;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Usuario {

    public static final int USUARIO = 0;
    public static final int VETERINARIO = 1;

    private static int tipoUsuario;
    private static FirebaseUser usuario;


    public Usuario(){
    }

    public static int getTipoUsuario() {
        return tipoUsuario;
    }

    public static void setTipoUsuario(int tipoUsuario) {
        Usuario.tipoUsuario = tipoUsuario;
    }

    public static FirebaseUser getUsuario() {
        return usuario;
    }

    public static void setUsuario(FirebaseUser usuario) {
        Usuario.usuario = usuario;
    }

    public static void obtenerDatosUsuario(){
        FirebaseAuth sesionFirebase = FirebaseAuth.getInstance();
        Usuario.setUsuario(sesionFirebase.getCurrentUser());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .document(sesionFirebase.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    try {
                        if (task.getResult().getBoolean("modoVeterinario").booleanValue()) {
                            tipoUsuario = VETERINARIO;
                        }
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        tipoUsuario = USUARIO;
                    }
                }
            }
        });
    }
}
