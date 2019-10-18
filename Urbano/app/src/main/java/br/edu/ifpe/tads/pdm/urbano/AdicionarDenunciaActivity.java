package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.edu.ifpe.tads.pdm.urbano.auth.SignInActivity;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;

public class AdicionarDenunciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_denuncia);
    }

    public void adicionarDenuncia(View view){

        Denuncia denuncia = new Denuncia(1,"", "");


        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
