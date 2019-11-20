package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.urbano.adapters.DenunciaListAdapter;
import br.edu.ifpe.tads.pdm.urbano.entidades.Comentario;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;
import br.edu.ifpe.tads.pdm.urbano.entidades.Usuario;

public class DetalheDenunciaActivity extends AppCompatActivity {

    Button btn_comentar;
    EditText comentario;
    TextView view_titulo;
    TextView view_descricao;
    String titulo_denuncia;
    String denunciaKey;

    private static Usuario usuario = new Usuario();
    private static Denuncia denuncia = new Denuncia();
    private static final ArrayList<Denuncia> denuncias = new ArrayList<Denuncia>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_denuncia);


        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        view_titulo = (TextView) findViewById(R.id.denuncia_titulo);
        view_descricao = (TextView) findViewById(R.id.info_local);
        btn_comentar = (Button) findViewById(R.id.btn_enviar_comentario);
        comentario = (EditText) findViewById(R.id.comentario);
        titulo_denuncia = getIntent().getStringExtra("titulo_denuncia");


        //Recuperar denuncia
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("denuncias");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Denuncia denuncia = postSnapshot.getValue(Denuncia.class);
                    denunciaKey = postSnapshot.getKey();

                        if(denuncia.getTitulo().equals(titulo_denuncia)){
                            view_titulo.setText(denuncia.getTitulo());
                            view_descricao.setText(denuncia.getDescricao());


                        }

                }


        //Adicionar comentario
        btn_comentar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String comment = comentario.getText().toString();

                final FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
                FirebaseUser fb_usuario = mAuth.getCurrentUser();

                DatabaseReference drUsuario = fbDB.getReference("users/" + fb_usuario.getUid());
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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /*drDenuncia.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            denuncia = postSnapshot.getValue(Denuncia.class);

                            if(denuncia.getTitulo().equals(titulo_denuncia)){
                                Comentario comentario_usuario = new Comentario();
                                comentario_usuario.setComentario(comment);
                                comentario_usuario.setUsuario(usuario);

                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/


            }

        });








                /*RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_denuncias2);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(new DenunciaListAdapter(getActivity(), denuncias));*/



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


}
