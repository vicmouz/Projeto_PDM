package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.urbano.adapters.ComentarioArrayListAdapter;
import br.edu.ifpe.tads.pdm.urbano.entidades.Comentario;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;
import br.edu.ifpe.tads.pdm.urbano.entidades.Usuario;

public class DetalheDenunciaActivity extends AppCompatActivity {

    Button btn_comentar;
    Button btn_curtir;
    EditText comentario;
    TextView view_titulo;
    TextView view_descricao;
    TextView view_usuario;
    ImageView imageView;
    TextView view_curtidas;
    String titulo_denuncia;
    String denunciaKey;


    private static Usuario usuario = new Usuario();
    private static Denuncia denuncia = new Denuncia();
    private static final ArrayList<Denuncia> denuncias = new ArrayList<Denuncia>();
    private static final ArrayList<Comentario> comentarios = new ArrayList<Comentario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_denuncia);


        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        FirebaseUser fb_usuario = mAuth.getCurrentUser();
        final DatabaseReference drUsuario = fbDB.getReference("users/" + fb_usuario.getUid());

        view_titulo = (TextView) findViewById(R.id.denuncia_titulo);
        view_descricao = (TextView) findViewById(R.id.descricao_denuncia);
        view_usuario = (TextView) findViewById(R.id.nome_usuario) ;
        view_curtidas = (TextView) findViewById(R.id.curtidas);
        btn_comentar = (Button) findViewById(R.id.btn_enviar_comentario);
        btn_curtir = (Button) findViewById(R.id.btn_curtir);
        comentario = (EditText) findViewById(R.id.comentario);
        imageView = (ImageView) findViewById(R.id.imageView);
        titulo_denuncia = getIntent().getStringExtra("titulo_denuncia");


        //Recuperar Usuario
        drUsuario.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                view_usuario.setText(usuario.getNome() + ": ");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }


        });

        //Recuperar denuncia
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("denuncias");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Denuncia denuncia = postSnapshot.getValue(Denuncia.class);

                        if(denuncia.getTitulo().equals(titulo_denuncia)){
                            view_titulo.setText(denuncia.getTitulo());
                            view_descricao.setText(denuncia.getDescricao());

                                try {
                                    Bitmap image = decodeFromFirebaseBase64(denuncia.getFoto());
                                    System.out.println(image.toString());
                                    imageView.setImageBitmap(image);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            view_curtidas.setText(denuncia.getCurtidas() + " curtidas");
                            denunciaKey = postSnapshot.getKey();

                            DatabaseReference drComentario = fbDB.getReference("denuncias/" + denunciaKey + "/comentarios");

                            drComentario.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    comentarios.clear();

                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        Comentario comentario = postSnapshot.getValue(Comentario.class);
                                        dataSnapshot.getKey();
                                        comentarios.add(comentario);

                                    }

                                    ListView listView = findViewById(R.id.lista_comentarios);
                                    listView.setAdapter(new ComentarioArrayListAdapter(getBaseContext(),
                                            R.layout.comentarios_listitem, comentarios)
                                    );
                                    Helper.getListViewSize(listView);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });

                        }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //Adicionar comentario
        btn_comentar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String comment = comentario.getText().toString();
                final DatabaseReference drDenuncia = fbDB.getReference("denuncias/" + denunciaKey + "/comentarios");

                //Obtem o usuario
                drUsuario.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usuario = dataSnapshot.getValue(Usuario.class);

                        Comentario comentario_usuario = new Comentario();
                        comentario_usuario.setComentario(comment);
                        comentario_usuario.setUsuario(usuario);

                        drDenuncia.push().setValue(comentario_usuario);
                        comentario.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


            }

        });

        //Curtir denuncia
        btn_curtir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final DatabaseReference drCurtidas = fbDB.getReference("denuncias/" + denunciaKey);
                drCurtidas.addValueEventListener(new ValueEventListener() {
                    int curtidas;
                    int count = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Denuncia denuncia = dataSnapshot.getValue(Denuncia.class);
                        curtidas = denuncia.getCurtidas();
                        if(count == 0){
                            denuncia.setCurtidas(curtidas + 1);
                            drCurtidas.setValue(denuncia);
                            count++;
                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

            }
        });



    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        System.out.println("Antes: " + image);
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }


}
