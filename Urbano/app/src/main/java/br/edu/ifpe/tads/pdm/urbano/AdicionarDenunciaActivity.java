package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    Button btn_go_Map;
    Button btn_voltar;

    EditText titulo_denuncia;
    EditText descricao_denuncia;
    TextView latitude_denuncia;
    TextView longitude_denuncia;

    private static Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_denuncia);

        final SharedPreferences titulo_sharedPreferences = getSharedPreferences("titulo_denuncia", Context.MODE_PRIVATE);
        final SharedPreferences descricao_sharedPreferences = getSharedPreferences("descricao_denuncia", Context.MODE_PRIVATE);

        //Pega a intent de outra activity
        Intent intent = getIntent();
        final Double lat = intent.getDoubleExtra("latitude", 0.0);
        final Double lng = intent.getDoubleExtra("longitude", 0.0);

        System.out.println("Latitude recebida: " + lat);
        System.out.println("Longitude recebida: " + lng);

        titulo_denuncia = (EditText) findViewById(R.id.titulo);
        descricao_denuncia = (EditText) findViewById(R.id.descricao);
        latitude_denuncia = (EditText) findViewById(R.id.latitudeDenuncia);
        longitude_denuncia = (EditText) findViewById(R.id.longitudeDenuncia);
        latitude_denuncia.setText(lat.toString());
        longitude_denuncia.setText(lng.toString());
        btn_adcionar_denuncia = findViewById(R.id.btn_adc_nova_denuncia);
        btn_go_Map = findViewById(R.id.btn_mapa);
        btn_voltar = findViewById(R.id.btn_cancelar);

        String tituloDenuncia = titulo_sharedPreferences.getString("titulo_denuncia", "");
        String descricaoDenuncia = descricao_sharedPreferences.getString("descricao_denuncia", "");

        if(tituloDenuncia != ""){
            titulo_denuncia.setText(tituloDenuncia);
        }

        if(descricaoDenuncia != ""){
            descricao_denuncia.setText(descricaoDenuncia);
        }

        btn_go_Map.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor titulo_editor = titulo_sharedPreferences.edit();
                titulo_editor.putString("titulo_denuncia", titulo_denuncia.getText().toString());
                titulo_editor.commit();

                SharedPreferences.Editor descricao_editor = descricao_sharedPreferences.edit();
                descricao_editor.putString("descricao_denuncia", descricao_denuncia.getText().toString());
                descricao_editor.commit();

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        }));


        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        btn_adcionar_denuncia.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String titulo = titulo_denuncia.getText().toString();
                final String descricao = descricao_denuncia.getText().toString();
                final String latDenuncia = latitude_denuncia.getText().toString();
                final String lngDenuncia = longitude_denuncia.getText().toString();
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
                        denuncia.setLatitude(Double.parseDouble(latDenuncia));
                        denuncia.setLongitude(Double.parseDouble(lngDenuncia));
                        denuncia.setCriado_por(usuario);

                        drDenuncia = fbDB_Denuncia.getReference("denuncias").push();
                        drDenuncia.setValue(denuncia);

                        SharedPreferences.Editor titulo_editor = titulo_sharedPreferences.edit();
                        titulo_editor.putString("titulo_denuncia", "");
                        titulo_editor.commit();

                        SharedPreferences.Editor descricao_editor = descricao_sharedPreferences.edit();
                        descricao_editor.putString("descricao_denuncia", "");
                        descricao_editor.commit();

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }


                });




                Intent intent = new Intent(AdicionarDenunciaActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        }

        );

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor titulo_editor = titulo_sharedPreferences.edit();
                titulo_editor.putString("titulo_denuncia", "");
                titulo_editor.commit();

                SharedPreferences.Editor descricao_editor = descricao_sharedPreferences.edit();
                descricao_editor.putString("descricao_denuncia", "");
                descricao_editor.commit();

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        });


    }




}
