package com.example.helpets.ui.Opciones;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.helpets.MainActivity;
import com.example.helpets.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;


public class FragmentDetallesUsuario extends Fragment implements View.OnClickListener {

    private ImageView imagenPerfilUsuario;
    private TextView nombreUsuarioOpciones;
    private TextView correoPerfil;
    private Button botonOpcionesUsuario;
    private Button botonCerrarSesion;
    private Button botonCambiarFotoPerfil;
    private FirebaseUser usuario;
    public static final int RC_SIGN_IN = 9001;
    public static final int SELECCIONAR_IMAGEN = 3337;

    public FragmentDetallesUsuario() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalles_usuario, container, false);
    }

    //Inicia los componentes de la pantalla.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        imagenPerfilUsuario = (ImageView)view.findViewById(R.id.imagenPerfilUsuario);
        nombreUsuarioOpciones = (TextView)view.findViewById(R.id.txtvNombreUsuarioOpciones);
        correoPerfil = (TextView)view.findViewById(R.id.txtvCorreoPerfil);
        Glide.with(view).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(imagenPerfilUsuario);
        nombreUsuarioOpciones.setText("Nombre de usuario: "+usuario.getDisplayName());
        correoPerfil.setText(usuario.getEmail());
        botonOpcionesUsuario = (Button)view.findViewById(R.id.botonOpcionesCuenta);
        botonCambiarFotoPerfil = (Button)view.findViewById(R.id.botonCambiarImagen);
        botonCerrarSesion = (Button)view.findViewById(R.id.botonCerrarSesion);
        botonOpcionesUsuario.setOnClickListener(this);
        botonCambiarFotoPerfil.setOnClickListener(this);
        botonCerrarSesion.setOnClickListener(this);
    }

    //Listener de los botones.
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonOpcionesCuenta:
                reautenticar();
                break;
            case R.id.botonCambiarImagen:
                Intent intentGaleria = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGaleria, SELECCIONAR_IMAGEN);
                break;
            case R.id.botonCerrarSesion:
                FirebaseAuth.getInstance().signOut();
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                break;
        }
    }

    //Método que inicia el intent proporcionado por Firebase para iniciar sesión, antes de iniciar
    //el activity para cambiar opciones de la cuenta.
    private void reautenticar(){
        Intent inicioSesion = AuthUI.getInstance().createSignInIntentBuilder().build();
        startActivityForResult(inicioSesion, RC_SIGN_IN);
    }

    //Método que se ejecuta después de seleccionar una imagen de la galería, o después de
    //iniciar sesión. En el primer caso, ejecuta el método para subir la imagen de perfil y en el
    //segundo, cambia al Fragment para cambiar las opciones de la cuenta.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RC_SIGN_IN:
                if (resultCode == Activity.RESULT_OK){
                    getActivity().getSupportFragmentManager().beginTransaction().replace
                            (R.id.contenedorOpciones, new FragmentOpciones()).commit();
                } else if (resultCode == Activity.RESULT_CANCELED){
                    Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                            "Inicia sesión para cambiar las opciones de tu cuenta",
                            Snackbar.LENGTH_LONG).show();
                }
                break;
            case SELECCIONAR_IMAGEN:
                if (resultCode == Activity.RESULT_OK){
                    subirImagenPerfilFirestore(data.getData());
                }
                break;
        }
    }

    //Sube la imagen de perfil a la nube, para obtener el URI de la nueva imagen de perfil.
    private void subirImagenPerfilFirestore(final Uri nuevaImagenPerfil){
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("foto_perfil_usuarios");
        final StorageReference fotoPerfilReferencia = storageReference.child
                (nuevaImagenPerfil.getLastPathSegment());
        fotoPerfilReferencia.putFile(nuevaImagenPerfil).addOnSuccessListener
                (getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fotoPerfilReferencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        subirImagenPerfilFirebaseAuth(uri);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                        e.getMessage(),
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    //Luego de obtener el URI de la nube, pide el cambio de imagen de perfil al usuario.
    private void subirImagenPerfilFirebaseAuth(final Uri nuevaImagenPerfil){
        UserProfileChangeRequest peticion = new UserProfileChangeRequest.Builder()
                .setPhotoUri(nuevaImagenPerfil)
                .build();
        usuario.updateProfile(peticion)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                                "Imagen de perfil actualizada.",
                                Snackbar.LENGTH_LONG)
                                .show();
                        Glide.with(getView())
                                .load(nuevaImagenPerfil)
                                .into(imagenPerfilUsuario);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(getActivity().findViewById(R.id.contenedorOpciones),
                        e.getMessage(), Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

}
