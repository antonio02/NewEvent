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
import com.newevent.utils.CadastroContaValidador;

public class CadastroConta extends AppCompatActivity implements OnCompleteListener<AuthResult>{

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText edtxtEmail;
    private EditText edtxtSenha;
    private EditText edtxtConfirmeSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_conta);
        binding();
    }

    private void binding() {
        edtxtEmail = findViewById(R.id.cadastroConta_edtxt_email);
        edtxtSenha = findViewById(R.id.cadastroConta_edtxt_senha);
        edtxtConfirmeSenha = findViewById(R.id.cadastroConta_edtxt_senhaConfirmacao);
    }

    public void cancelar(View view) {
        finish();
    }

    public void finalizar(View view) {
        int resultado = CadastroContaValidador.validar(
                edtxtEmail.getText().toString(),
                edtxtSenha.getText().toString(),
                edtxtConfirmeSenha.getText().toString());

        switch (resultado){
            case CadastroContaValidador.EMAIL_INVALIDO:
                edtxtEmail.setError("Email invalido");
                edtxtEmail.requestFocus();
                return;

            case CadastroContaValidador.SENHA_CURTA:
                edtxtSenha.setError("Senha curta");
                edtxtSenha.requestFocus();
                return;

            case CadastroContaValidador.SENHA_COM_ESPACO:
                edtxtSenha.setError("A senha não pode ter espaço");
                edtxtSenha.requestFocus();
                return;

            case CadastroContaValidador.SENHAS_NAO_SAO_IGUAIS:
                edtxtConfirmeSenha.setError("As senhas não sao iguais");
                edtxtConfirmeSenha.requestFocus();
                return;

            case CadastroContaValidador.VALIDO:
                auth.createUserWithEmailAndPassword(edtxtEmail.getText().toString().trim(),
                        edtxtSenha.getText().toString().trim())
                        .addOnCompleteListener(this, this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            Toast.makeText(this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,"Error: " + task.getResult().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
