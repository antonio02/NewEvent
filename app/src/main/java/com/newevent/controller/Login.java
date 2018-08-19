package com.newevent.controller;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.newevent.R;
import com.newevent.factory.FirebaseErrorStringFactory;
import com.newevent.utils.ContaValidador;

public class Login extends AppCompatActivity implements OnCompleteListener<AuthResult>{

    private FirebaseAuth auth;
    private EditText edtxtEmail;
    private EditText edtxtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        biding();
    }

    private void biding() {
        auth = FirebaseAuth.getInstance();
        edtxtEmail = findViewById(R.id.login_edtxt_email);
        edtxtSenha = findViewById(R.id.login_edtxt_senha);
    }

    public void cancelar(View view) {
        finish();
    }

    public void finalizar(View view) {
        int resultado = ContaValidador.validarLogin(
                edtxtEmail.getText().toString(),
                edtxtSenha.getText().toString());

        switch (resultado){
            case ContaValidador.EMAIL_INVALIDO:
                edtxtEmail.setError("Email invalido");
                edtxtEmail.requestFocus();
                return;
            case ContaValidador.SENHA_CURTA:
                edtxtSenha.setError("Senha curta");
                edtxtSenha.requestFocus();
                return;
            case ContaValidador.SENHA_COM_ESPACO:
                edtxtSenha.setError("Senha com espaço");
                edtxtSenha.requestFocus();
                return;
            case ContaValidador.VALIDO:
                auth.signInWithEmailAndPassword(edtxtEmail.getText().toString().trim(),
                        edtxtSenha.getText().toString().trim())
                        .addOnCompleteListener(this, this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            Toast.makeText(this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro: " +
                    FirebaseErrorStringFactory.getErro(task.getException()),
                    Toast.LENGTH_LONG).show();
        }
    }
}
