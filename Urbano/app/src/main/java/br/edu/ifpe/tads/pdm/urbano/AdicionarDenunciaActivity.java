package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import br.edu.ifpe.tads.pdm.urbano.auth.SignInActivity;

public class AdicionarDenunciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_denuncia);
    }

    public void adicionarDenuncia(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
