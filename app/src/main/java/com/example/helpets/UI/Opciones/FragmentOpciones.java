package com.example.helpets.ui.Opciones;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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


    //Llama a Firebase para subir los nuevos datos del usuario.
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
            usuario.updateEmail(sharedPreferences.getString(key,null))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                                    "Correo electrónico cambiado correctamente",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                            "Ha ocurrido un error al cambiar tu correo.",
                            Snackbar.LENGTH_SHORT).show();
                }
            });
        } else if(key.equals(CONTRASENIA)){ //El usuario cambia su contraseña.
            usuario.updatePassword(sharedPreferences.getString(key, null))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                            "Tu contrasenia ha sido cambiada correctamente.", Snackbar.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                            "Ha ocurrido un error al cambiar tu contrasenia",
                            Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

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
