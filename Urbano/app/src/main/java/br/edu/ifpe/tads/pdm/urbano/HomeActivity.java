package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifpe.tads.pdm.urbano.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.urbano.auth.SignInActivity;
import br.edu.ifpe.tads.pdm.urbano.entidades.Comentario;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;
import br.edu.ifpe.tads.pdm.urbano.fragmentos.HomeFragment;
import br.edu.ifpe.tads.pdm.urbano.fragmentos.MapaFragment;
import br.edu.ifpe.tads.pdm.urbano.fragmentos.PerfilFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    FirebaseAuthListener authListener;

    private static final Denuncia [] denuncias = {
            /*new Denuncia(1, "Falta de sinalização sonora", "Não há sinalização sonora", new Comentario("Esta sendo resolvido")),
            new Denuncia(2, "Ausência de rampas", "Não há rampas na faixa", new Comentario("Ta horrível"))*/
            new Denuncia( "Falta de sinalização sonora", "Não há sinalização sonora"),
            new Denuncia( "Ausência de rampas", "Não há rampas na faixa")

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        getSupportActionBar().setElevation(0);

        BottomNavigationView navView = findViewById(R.id.menu_bottom);
        navView.setOnNavigationItemSelectedListener(this);

        /*ListView listView = (ListView)findViewById(R.id.lista_denuncias);
        listView.setAdapter(new DenunciaArrayListAdapter(this,
                        R.layout.denuncia_listitem, denuncias
                )
        );*/

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_frag, new HomeFragment())
                .commit();



    }

    public void buttonSignOutClick(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonMapsClick(View view){
        Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.menu_home:
                fragment = new HomeFragment();
                break;
            case R.id.menu_perfil:
                fragment = new PerfilFragment();
                break;

        }
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_frag, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    /*@Override
    public void onFragmentInteraction(Uri uri) {

    }*/

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

    public void adicionarNovaDenuncia(View view){
        Intent intent = new Intent(this, AdicionarDenunciaActivity.class);
        startActivity(intent);
    }

}
