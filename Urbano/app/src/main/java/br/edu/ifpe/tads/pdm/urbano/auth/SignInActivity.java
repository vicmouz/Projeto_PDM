package br.edu.ifpe.tads.pdm.urbano.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifpe.tads.pdm.urbano.HomeActivity;
import br.edu.ifpe.tads.pdm.urbano.R;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuthListener authListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);
    }

    public void buttonSignInClick(View view) {
        EditText edEmail = (EditText) findViewById(R.id.edit_email);
        EditText edPassword = (EditText) findViewById(R.id.edit_password);

        String login = edEmail.getText().toString();
        String passwd = edPassword.getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(login, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
    }

    public void redirect_cadastrar(View view) {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
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
