package com.newevent.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.newevent.R;
import com.newevent.adapter.AtividadesRvAdapter;
import com.newevent.dao.evento.GetEventoRealtime;
import com.newevent.dao.evento.interfaces.GetEventoRealtimeListener;
import com.newevent.model.Evento;
import com.newevent.model.Local;
import com.newevent.utils.UidUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
    private AtividadesRvAdapter adapter;

    DatabaseReference eventosDb;
    private GetEventoRealtime getEvento;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_evento);

        eventosDb = FirebaseDatabase.getInstance().getReference("eventos");
        inicializarViews();
        pegarEvento();
        listarAtividades();
    }

    private void listarAtividades() {
        rvAtividades = findViewById(R.id.rv_evento_publicado_atividades);
        rvAtividades.setAdapter(new AtividadesRvAdapter(this));
        rvAtividades.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getEvento.stop();
    }

    @Override
    public void onUpdate(Evento evento) {
        this.evento = evento;
        mostrarDadosDoEveto();
    }

    @Override
    public void onDelete() {

    }

    public void inscrever(View view) {
    }

    private void mostrarDadosDoEveto() {
        if(evento == null) {
            Toast.makeText(this, "Deu Ruim", Toast.LENGTH_SHORT).show();
            return;
        }

        Local local = evento.getLocal();
        SimpleDateFormat format = new SimpleDateFormat("dd - MMMM - yyyy",
                Locale.getDefault());

        txtNomeEvento.setText(evento.getNome());
        txtTipoEvento.setText(evento.getTipo());

        txtEnderecoLocalEvento.setText(local.getEndereco());
        txtBairroLocalEvento.setText(local.getBairro());
        txtCidadeLocalEvento.setText(local.getCidade());
        txtUfLocalEvento .setText(local.getUf());
        txtComplementoLocalEvento.setText(local.getComplemento());

        if (local.getComplemento() == null || local.getComplemento().isEmpty()) {
            TextView textoAntesDoComplemento = findViewById(R.id.txt_evento_publico_compl_fixo);
            textoAntesDoComplemento.setVisibility(View.GONE);
        }

        txtDataIniEvento.setText(format.format(evento.getDataInicio()));

        if (evento.getDataTermino() != null) {
            txtDataFimEvento.setText(format.format(evento.getDataTermino()));
        }
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
