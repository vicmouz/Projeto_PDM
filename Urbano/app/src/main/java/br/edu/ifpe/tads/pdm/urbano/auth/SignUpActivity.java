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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.entidades.Usuario;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuthListener authListener;
    EditText edEmail;
    EditText edPassword;
    EditText edNome;
    EditText edCPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);
    }

    public void buttonSignUpClick(View view) {
        edEmail = (EditText) findViewById(R.id.edit_email);
        edPassword = (EditText) findViewById(R.id.edit_password);
        edNome = (EditText) findViewById(R.id.edit_name);
        edCPF = (EditText) findViewById(R.id.edit_Cpf);

        final String email = edEmail.getText().toString();
        final String password = edPassword.getText().toString();
        final String nome = edNome.getText().toString();
        final String CPF = edCPF.getText().toString();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg = task.isSuccessful()? "Cadastro Realizado com Sucesso!": "Erro ao realizar cadastro";

                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();

                        if (task.isSuccessful()) {
                            Usuario user = new Usuario();
                            user.setNome(nome);
                            user.setEmail(email);
                            user.setCpf(CPF);

                            DatabaseReference drUsers = FirebaseDatabase.
                                    getInstance().getReference("users");
                            drUsers.child(mAuth.getCurrentUser().getUid()).
                                    setValue(user);
                        }


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
