package com.example.helpets.ui.Opciones;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

import com.example.helpets.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FragmentOpciones extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    //Constantes con las llaves de las preferencias.
    private static final String NOMBRE_USUARIO = "nombreUsuario";
    private static final String CORREO_USUARIO = "correoUsuario";
    private static final String CONTRASENIA = "contraseniaHelpets";


    public FragmentOpciones() {
    }

    //Coloca las preferencias del archivo XML.
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferencias, rootKey);
    }


    //Llama a Firebase para realizar los cambios a la cuenta del usuario. Se ejecuta si detecta un
    //cambio en las preferencias.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (key.equals(NOMBRE_USUARIO)){ //El usuario cambia su nombre para mostrar
            UserProfileChangeRequest cambiarPerfil = new UserProfileChangeRequest.Builder()
                    .setDisplayName(sharedPreferences.getString(key, null))
                    .build();
            usuario.updateProfile(cambiarPerfil)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                            "Nombre de usuario cambiado correctamente",
                            Snackbar.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                            "Ha ocurrido un error al cambiar tu nombre de usuario",
                            Snackbar.LENGTH_SHORT).show();
                }
            });
        } else if(key.equals(CORREO_USUARIO)){ //El usuario cambia su correo electrónico
            usuario.updateEmail(sharedPreferences.getString(key, null))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                                    "Correo electrónico actualizado correctamente",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                            e.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                }
            });
        } else if(key.equals(CONTRASENIA)) { //El usuario cambia su contraseña.
            usuario.updatePassword(sharedPreferences.getString(key, null))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                                    "Tu contraseña ha sido cambiada correctamente.", Snackbar.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                            e.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    //Establece la escucha de cambios en las preferencias.
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
