package br.edu.ifpe.tads.pdm.urbano.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifpe.tads.pdm.urbano.R;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuthListener authListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);
    }

    public void buttonSignUpClick(View view) {
        EditText edEmail = (EditText) findViewById(R.id.edit_email);
        EditText edPassword = (EditText) findViewById(R.id.edit_password);

        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg = task.isSuccessful() ? "SIGN UP OK!": "SIGN UP ERROR";

                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void redirect_login(View view) {
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
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
