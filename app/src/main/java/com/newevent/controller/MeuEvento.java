package com.newevent.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.newevent.R;
import com.newevent.dao.evento.GetEventoRealtime;
import com.newevent.dao.evento.interfaces.GetEventoRealtimeListener;
import com.newevent.model.Evento;
import com.newevent.utils.UidUtil;

public class MeuEvento extends AppCompatActivity implements GetEventoRealtimeListener {

    private String eventoUid;
    private Evento evento;
    private GetEventoRealtime getEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_evento);

        carregarEvento();
    }

    @Override
    protected void onDestroy() {
        getEvento.stop();
        super.onDestroy();
    }

    private void carregarEvento() {
        if(getIntent().hasExtra("evento_uid")){
            eventoUid = getIntent().getStringExtra("evento_uid");
            if(!UidUtil.isValido(eventoUid)){
                finish();
                return;
            }
        } else {
            finish();
            return;
        }

        getEvento = new GetEventoRealtime(eventoUid, this);
    }

    public void abrirCriarAtividade(View view) {
        Intent it = new Intent(this, CriarAtividade.class);
        it.putExtra("evento_uid", eventoUid);
        startActivity(it);
    }

    @Override
    public void onUpdate(Evento evento) {
        this.evento = evento;
    }

    @Override
    public void onDelete() {
        finish();
    }

    public void finalizarEdicao(View view) {
    }

    public void cancelarEdicao(View view) {
    }

    public void abilitarEdicao(View view) {
    }
}
