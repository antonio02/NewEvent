package com.newevent.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.newevent.R;
import com.newevent.dao.evento.GetEventoRealtime;
import com.newevent.dao.evento.GetEventoRealtimeListener;
import com.newevent.model.Evento;
import com.newevent.usecase.CriarNovaAtividade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CriarAtividade extends AppCompatActivity {

    private EditText nomeAtividade;
    private EditText tipoAtividade;
    private EditText valorAtividade;
    private EditText maxInscricoesAtividade;
    private EditText dataInicioAtividade;
    private EditText dataFimAtividade;

    private Evento mEvento;
    private CriarNovaAtividade criarAtividade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_atividade);

        criarAtividade = new CriarNovaAtividade();

        pegarEvento();
        inicializarViews();
        atribuirEscutadorEmEditData(dataInicioAtividade);
        atribuirEscutadorEmEditData(dataFimAtividade);

    }

    private Evento pegarEvento() {
        String eventoUid = getIntent().getStringExtra("eventoUid");
        new GetEventoRealtime(eventoUid, new GetEventoRealtimeListener() {
            @Override
            public void onUpdate(Evento evento) {
                mEvento = evento;
            }

            @Override
            public void onDelete() {

            }
        });
        return null;
    }

    public void finalizar(View view) {
        String nome = nomeAtividade.getText().toString();
        String tipo = tipoAtividade.getText().toString();
        double valor = pegarValorAtividade();
        int maxInscricoes = pegarMaxInscricoes();
        Date dataInicio = pegarData();
        Date dataFim = pegarData();

        switch (criarAtividade.criar(mEvento, nome, tipo, valor, dataInicio,
                dataFim, maxInscricoes)) {

            case CriarNovaAtividade.SALVO:
                Toast.makeText(this, "Atividade inclusa ao evento",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;

            case CriarNovaAtividade.NOME_INVALIDO:
            nomeAtividade.setError("Nome nulo ou menor que 4 caracteres");
            nomeAtividade.requestFocus();
            break;

            case CriarNovaAtividade.TIPO_INVALIDO:
            tipoAtividade.setError("Nome nulo ou menor que 4 caracteres");
            tipoAtividade.requestFocus();
            break;

            case CriarNovaAtividade.VALOR_INVALIDO:
            valorAtividade.setError("Valor nulo ou menor que 0");
            valorAtividade.requestFocus();
            break;

            case CriarNovaAtividade.MAX_INCRICOES_INVALIDO:
                maxInscricoesAtividade.setError("Numero de inscrições menor que 1");
                maxInscricoesAtividade.requestFocus();
                break;

            case CriarNovaAtividade.DATA_INICIO_INVALIDA:
            dataInicioAtividade.setError("Data de Inicio nula");
            dataInicioAtividade.requestFocus();
            break;

            case CriarNovaAtividade.DATA_TERMINO_INVALIDA:
            dataFimAtividade.setError("Data de Termino nula");
            dataFimAtividade.requestFocus();
            break;

        }

    }

    public void cancelar(View view) {
        finish();
    }

    private int pegarMaxInscricoes() {
        try {
            return Integer.parseInt(maxInscricoesAtividade.getText().toString());
        }catch (NumberFormatException e) {
            return -1;
        }
    }

    private double pegarValorAtividade() {
        try {
            return Double.parseDouble(valorAtividade.getText().toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Date pegarData() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                    Locale.getDefault());
            return formato.parse(dataInicioAtividade.getText().toString());
        } catch (ParseException e) {
            return null;
        }
    }

    private void atribuirEscutadorEmEditData(EditText editText) {

        editText.setOnClickListener((view) -> {
            final Calendar calendario = Calendar.getInstance();
            int ano = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            int hora = calendario.get(Calendar.HOUR);
            int minuto = calendario.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view1, hour, minute) -> {
                        calendario.set(Calendar.HOUR, hour);
                        calendario.set(Calendar.MINUTE, minute);
                        editText.setText(new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                                Locale.getDefault()).format(calendario.getTime()));
                    }, hora, minuto, true);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, year, month, day) -> {
                        calendario.set(year, month, day);
                        timePickerDialog.show();
                    }, ano, mes, dia);

            datePickerDialog.getDatePicker().setMinDate(calendario.getTimeInMillis());
            datePickerDialog.show();
        });

    }

    private void inicializarViews() {
        nomeAtividade = findViewById(R.id.edtxt_criar_atividade_nome);
        tipoAtividade = findViewById(R.id.edtxt_criar_atividade_tipo);
        valorAtividade = findViewById(R.id.edtxt_criar_atividade_valor);
        maxInscricoesAtividade = findViewById(R.id.edtxt_criar_atividade_max_inscricoes);
        dataInicioAtividade = findViewById(R.id.edtxt_criar_atividade_data_inicio);
        dataFimAtividade = findViewById(R.id.edtxt_criar_atividade_data_fim);
    }
}
