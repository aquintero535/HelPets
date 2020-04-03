package com.example.helpets.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpets.R;
import com.example.helpets.adapter.AdaptadorMensajes;
import com.example.helpets.adapter.Mensaje;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityConsultaVeterinario extends AppCompatActivity
        implements View.OnClickListener, EventListener<QuerySnapshot> {

    private CircleImageView fotoPerfil;
    private TextView nombreVeterinario;
    private RecyclerView rvMensajes;
    private EditText campoMensajeChat;
    private Button botonEnviarChat;
    private ImageButton botonEnviarImagen;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //Identificadores del usuario y el veterinario.
    private String idVeterinario;
    private String idUsuario;
    private String idChat;
    private String strNombreVeterinario;
    private String nombreUsuario;

    private AdaptadorMensajes adaptador;
    private FirebaseFirestore db;
    private static final int ENVIAR_FOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_veterinario);
        fotoPerfil = (CircleImageView)findViewById(R.id.fotoPerfil);
        nombreVeterinario = (TextView)findViewById(R.id.txtvNombreVeterinario);
        rvMensajes = (RecyclerView)findViewById(R.id.rvMensajes);
        campoMensajeChat = (EditText)findViewById(R.id.campoMensajeChat);
        botonEnviarChat = (Button)findViewById(R.id.botonEnviarChat);

        storage = FirebaseStorage.getInstance(); //Base de datos de imágenes.
        adaptador = new AdaptadorMensajes(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(linearLayoutManager);
        rvMensajes.setAdapter(adaptador);
        botonEnviarImagen = (ImageButton)findViewById(R.id.botonEnviarImagen);
        botonEnviarChat.setOnClickListener(this);
        botonEnviarImagen.setOnClickListener(this);


        //Identificadores de usuario
        idUsuario = getIntent().getStringExtra("idUsuario");
        idVeterinario = getIntent().getStringExtra("idVeterinario");
        strNombreVeterinario = getIntent().getStringExtra("nombreVeterinario");
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        nombreVeterinario.setText(strNombreVeterinario);

        //Obtengo la instancia de la base de datos.
        db = FirebaseFirestore.getInstance();

        //Listener para detectar cambios en la base de datos. (onEvent)
        db.collection("chat")
                .document(idChat())
                .collection("mensajes")
                .addSnapshotListener(this);

        //Detecta algún cambio en el adaptador (la lista de mensajes) para que la lista
        //se desplaze hacia abajo al recibir un nuevo mensaje.
        adaptador.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonEnviarChat:
                if (!campoMensajeChat.getText().toString().isEmpty()) {
                    db.collection("chat")
                            .document(idChat())
                            .collection("mensajes")
                            .add(new Mensaje(
                            nombreUsuario,
                            campoMensajeChat.getText().toString(),
                            "",
                            "1",
                            DateFormat.getTimeInstance().format(Timestamp.now().toDate()),
                            idUsuario,
                            idVeterinario));
                    campoMensajeChat.setText("");
                }
                break;
            case R.id.botonEnviarImagen:
                Intent intentGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                intentGaleria.setType("image/jpeg");
                intentGaleria.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser
                        (intentGaleria, "Selecciona una foto"), ENVIAR_FOTO);
                break;

        }
    }

    private void setScrollbar(){
        rvMensajes.scrollToPosition(adaptador.getItemCount()-1);
    }

    //Obtiene el mensaje nuevo en la base de datos, lo cambia a la clase Mensaje
    // y lo envía al adaptador.
    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                        @Nullable FirebaseFirestoreException e) {
        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
            if (documentChange.getType() == DocumentChange.Type.ADDED){
                Mensaje mensaje = documentChange.getDocument().toObject(Mensaje.class);
                adaptador.aniadirMensaje(mensaje);
            }
        }
    }

    //Método que se ejecuta al enviar una imagen.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENVIAR_FOTO  && resultCode == RESULT_OK){
            Uri uri = data.getData();
            storageReference = storage.getReference("chat_imagenes");
            final StorageReference fotoReferencia = storageReference.child
                    (uri.getLastPathSegment());

            //Listener para subir la imagen.
            fotoReferencia.putFile(uri).addOnSuccessListener(this,
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Listener para obtener la URL de la imagen.
                            fotoReferencia.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Mensaje mensajeFoto = new Mensaje
                                            (nombreUsuario,
                                                    "Ha enviado una foto",
                                                    "",
                                                    "2",
                                                    DateFormat.getTimeInstance().format
                                                            (Timestamp.now().toDate()),
                                                    uri.toString(),
                                                    idUsuario,
                                                    idVeterinario);
                                    db.collection("chat").add(mensajeFoto);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText
                            (ActivityConsultaVeterinario.this,
                                    "No se pudo subir la imagen",
                                    Toast.LENGTH_SHORT)
                            .show();
                    System.out.println(e.getMessage());
                }
            });
        }
    }

    protected String idChat(){
        int comparar = idUsuario.compareTo(idVeterinario);
        if (comparar < 0){
            return idUsuario+idVeterinario;
        } else{
            return idVeterinario+idUsuario;
        }
    }
}
