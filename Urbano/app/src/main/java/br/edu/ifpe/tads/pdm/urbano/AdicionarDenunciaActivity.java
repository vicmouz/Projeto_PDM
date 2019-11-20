package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.urbano.entidades.Comentario;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;
import br.edu.ifpe.tads.pdm.urbano.entidades.Usuario;

public class AdicionarDenunciaActivity extends AppCompatActivity {

    DatabaseReference drDenuncia;
    Button btn_adcionar_denuncia;

    EditText titulo_denuncia;
    EditText descricao_denuncia;

    private static Usuario usuario = new Usuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_denuncia);

        titulo_denuncia = (EditText) findViewById(R.id.titulo);
        descricao_denuncia = (EditText) findViewById(R.id.descricao);
        btn_adcionar_denuncia = findViewById(R.id.btn_adc_nova_denuncia);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();



        btn_adcionar_denuncia.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String titulo = titulo_denuncia.getText().toString();
                final String descricao = descricao_denuncia.getText().toString();
                final FirebaseDatabase fbDB = FirebaseDatabase.getInstance();

                FirebaseUser fb_usuario = mAuth.getCurrentUser();
                DatabaseReference drUsuario = fbDB.getReference("users/" + fb_usuario.getUid());


                drUsuario.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        usuario = dataSnapshot.getValue(Usuario.class);

                        FirebaseDatabase fbDB_Denuncia = FirebaseDatabase.getInstance();
                        Denuncia denuncia = new Denuncia();

                        denuncia.setTitulo(titulo);
                        denuncia.setDescricao(descricao);
                        denuncia.setCriado_por(usuario);

                        drDenuncia = fbDB_Denuncia.getReference("denuncias").push();
                        drDenuncia.setValue(denuncia);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }


                });




                Intent intent = new Intent(AdicionarDenunciaActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        }

        );
    }


}
