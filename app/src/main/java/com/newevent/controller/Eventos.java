package com.newevent.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.newevent.R;
import com.newevent.adapter.EventosRvAdapter;
import com.newevent.utils.UsuarioUtils;

public class Eventos extends AppCompatActivity {

    private RecyclerView rv;
    private EventosRvAdapter adapter;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        biding();
    }

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private void biding() {
        auth = FirebaseAuth.getInstance();
        rv = findViewById(R.id.rv_eventos);
        adapter = new EventosRvAdapter(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater m = new MenuInflater(this);
        menu.clear();
        if(UsuarioUtils.isLogado()){
            m.inflate(R.menu.eventos_menu_logado, menu);
        } else {
            m.inflate(R.menu.eventos_menu_deslogado, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.eventos_menu_logout:
                auth.signOut();
                Intent itIncio = new Intent(this, Inicio.class);
                startActivity(itIncio);
                finish();
                break;

            case R.id.eventos_menu_login:
                Intent itLogin = new Intent(this, Login.class);
                startActivity(itLogin);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Deseja sair");
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Eventos.super.onBackPressed();
            }
        });
        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    }

    public void abrirCriarEvento(View view) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, Login.class));
        } else {
            startActivity(new Intent(this, CriarEvento.class));
        }
    }
}
