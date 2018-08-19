package com.newevent.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.newevent.R;

public class Inicio extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        biding();
    }

    @Override
    protected void onResume() {
        checarSeJaEstaLogado();
        super.onResume();
    }

    private void checarSeJaEstaLogado() {
        if(auth.getCurrentUser() != null){
            Intent it = new Intent(this, Eventos.class);
            startActivity(it);
            finish();
        }
    }

    private void biding() {
        auth = FirebaseAuth.getInstance();
    }

    public void abrirCriarConta(View view) {
        Intent it = new Intent(this, CadastroConta.class);
        startActivity(it);
    }

    public void abrirLogin(View view) {
        Intent it = new Intent(this, Login.class);
        startActivity(it);
    }

    public void abrirEventos(View view) {
        startActivity(new Intent(this, Eventos.class));
    }
}