package com.newevent.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.newevent.R;

public class CadastroConta extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText edtxtEmail;
    private EditText edtxtSenha;
    private EditText edtxtConfirmeSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_conta);
    }

    public void cancelar(View view) {
        finish();
    }

    public void finalizar(View view) {
    }
}
