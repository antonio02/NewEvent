package com.newevent.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.newevent.R;
import com.newevent.dao.evento.GetEventoRealtime;
import com.newevent.dao.evento.interfaces.GetEventoRealtimeListener;
import com.newevent.model.Evento;
import com.newevent.utils.UidUtil;

public class DetalhesEvento extends AppCompatActivity implements GetEventoRealtimeListener {

    private TextView txtTipoEvento;
    private TextView txtNomeEvento;
    private TextView txtEnderecoLocalEvento;
    private TextView txtBairroLocalEvento;
    private TextView txtComplementoLocalEvento;
    private TextView txtCidadeLocalEvento;
    private TextView txtUfLocalEvento;
    private TextView txtDataIniEvento;
    private TextView txtDataFimEvento;

    private RecyclerView rvAtividades;

    private GetEventoRealtime getEvento;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_evento);

        inicializarViews();
        pegarEvento();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getEvento.stop();
    }

    @Override
    public void onUpdate(Evento evento) {
        this.evento = evento;
    }

    @Override
    public void onDelete() {

    }

    private void pegarEvento() {
        String eventoUid;
        if(getIntent().hasExtra("evento_uid")){
            eventoUid = getIntent().getStringExtra("evento_uid");
            if(UidUtil.isValido(eventoUid)){
                getEvento = new GetEventoRealtime(eventoUid, this);
                return;
            }
        }
        finish();
        return;
    }

    private void inicializarViews() {
        txtTipoEvento = findViewById(R.id.txt_evento_publico_tipo);
        txtNomeEvento = findViewById(R.id.txt_evento_publico_nome);
        txtEnderecoLocalEvento = findViewById(R.id.txt_evento_publico_local_endereco);
        txtBairroLocalEvento = findViewById(R.id.txt_evento_publico_local_bairro);
        txtComplementoLocalEvento = findViewById(R.id.txt_evento_publico_local_complemento);
        txtCidadeLocalEvento = findViewById(R.id.txt_evento_publico_local_cidade);
        txtUfLocalEvento = findViewById(R.id.txt_evento_publico_local_uf);
        txtDataIniEvento = findViewById(R.id.txt_evento_publico_data_inicio);
        txtDataFimEvento = findViewById(R.id.txt_evento_publico_data_fim);
    }
}
