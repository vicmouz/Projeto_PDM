package br.edu.ifpe.tads.pdm.urbano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class DetalheDenunciaActivity extends AppCompatActivity {

    Button btn_comentar;
    EditText comentario;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_denuncia);

        textView = (TextView) findViewById(R.id.denuncia_titulo);
        System.out.println("******************* - " + getIntent().getStringExtra("titulo_denuncia"));

        btn_comentar = (Button) findViewById(R.id.btn_enviar_comentario);
        comentario = (EditText) findViewById(R.id.comentario);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        btn_comentar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String comment = comentario.getText().toString();

                final FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
                FirebaseUser fb_usuario = mAuth.getCurrentUser();

                DatabaseReference drUsuario = fbDB.getReference("users/" + fb_usuario.getUid());
                DatabaseReference drDenuncia = fbDB.getReference("denuncias/");


            }

        });


    }


}
