package com.example.helpets.UI.Menu;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.helpets.R;
import com.example.helpets.UI.InicioSesion.ActivityInicioSesion;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityMenuPrincipal extends AppCompatActivity {

    public static final int MENSAJE_ENVIADO = 500;
    public static final int FORMULARIO_ENVIADO = 678;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
    }

    /* Comprueba la sesión del usuario */
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth sesionFirebase = FirebaseAuth.getInstance();
        if (sesionFirebase.getCurrentUser() == null){
            startActivity(new Intent(ActivityMenuPrincipal.this,
                    ActivityInicioSesion.class));
            finish();
        }
    }

    /* Método que se ejecuta finalizadas algunas actividades. Sirve para mostrar un Snackbar */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, null);
        switch (resultCode){
            case MENSAJE_ENVIADO:
                Snackbar.make(findViewById(R.id.layoutMenuPrincipal),
                        "Tu mensaje ha sido enviado.",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case FORMULARIO_ENVIADO:
                Snackbar.make(findViewById(R.id.layoutMenuPrincipal),
                        "¡Hemos recibido tu formulario! Lo revisaremos y te daremos una" +
                                " respuesta por correo.",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
        }
    }
}
