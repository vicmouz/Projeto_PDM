package br.edu.ifpe.tads.pdm.urbano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifpe.tads.pdm.urbano.adapters.DenunciaArrayListAdapter;
import br.edu.ifpe.tads.pdm.urbano.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuthListener authListener;

    private static final Denuncia [] denuncias = {
            new Denuncia(1, "Falta de sinalização sonora", "Não há sinalização sonora"),
            new Denuncia(2, "Ausência de rampas", "Não há rampas na faixa")

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);


        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(new DenunciaArrayListAdapter(this,
                        R.layout.denuncia_listitem, denuncias
                )
        );
    }

    public void buttonSignOutClick(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mAuth.signOut();
        } else {
            Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonMapsClick(View view){
        Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }
}
