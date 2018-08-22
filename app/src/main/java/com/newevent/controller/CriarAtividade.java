package com.newevent.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.newevent.R;
import com.newevent.model.Evento;

public class CriarAtividade extends AppCompatActivity {

    private TextView nomeAtividade;
    private TextView tipoAtividade;
    private TextView valorAtividade;
    private TextView dataAtividade;
    private Evento evento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_atividade);

        inicializarViews();
    }

    private void inicializarViews() {
        nomeAtividade = findViewById(R.id.edtxt_criar_atividade_nome);
        tipoAtividade = findViewById(R.id.edtxt_criar_atividade_tipo);
        valorAtividade = findViewById(R.id.edtxt_criar_atividade_valor);
        dataAtividade = findViewById(R.id.edtxt_criar_atividade_data);
    }
}
