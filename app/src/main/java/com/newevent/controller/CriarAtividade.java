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
import com.newevent.dao.evento.interfaces.GetEventoRealtimeListener;
import com.newevent.model.Evento;
import com.newevent.usecase.CriarNovaAtividade;
import com.newevent.utils.UidUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CriarAtividade extends AppCompatActivity implements GetEventoRealtimeListener {

    private EditText nomeAtividade;
    private EditText tipoAtividade;
    private EditText valorAtividade;
    private EditText maxInscricoesAtividade;
    private EditText dataInicioAtividade;
    private EditText dataFimAtividade;

    private Evento mEvento;
    private CriarNovaAtividade criarAtividade;
    private GetEventoRealtime getEvento;


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

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void pegarEvento() {
        String eventoUid;
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

    public void finalizar(View view) {
        if(mEvento == null){
            Toast.makeText(this, "Evento não carregado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String nome = nomeAtividade.getText().toString();
        String tipo = tipoAtividade.getText().toString();
        double valor = pegarValorAtividade();
        int maxInscricoes = pegarMaxInscricoes();
        Date dataInicio = pegarData(dataInicioAtividade);
        Date dataFim = pegarData(dataFimAtividade);

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
                tipoAtividade.setError("Tipo nulo ou menor que 4 caracteres");
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
                Toast.makeText(this,
                        "Data de inicio vazia ou invalida",
                        Toast.LENGTH_SHORT).show();
                break;

            case CriarNovaAtividade.DATA_DE_INICIO_MENOR_QUE_EVENTO:
                Toast.makeText(this,
                        "Data de inicio menor que o inicio do evento: " + new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                    Locale.getDefault()).format(mEvento.getDataInicio()),
                        Toast.LENGTH_LONG).show();
                break;

            case CriarNovaAtividade.DATA_TERMINO_INVALIDA:
                Toast.makeText(this,
                        "Data de termino vazia ou menor que a data de inicio",
                        Toast.LENGTH_SHORT).show();
                break;

            case CriarNovaAtividade.DATA_DE_TERMINO_MAIOR_QUE_EVENTO:
                Toast.makeText(this,
                        "Data de termino maior que o fim do evento: " + new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                                Locale.getDefault()).format(mEvento.getDataTermino()),
                        Toast.LENGTH_SHORT).show();
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

    private Date pegarData(EditText edtxtData) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                    Locale.getDefault());
            return formato.parse(edtxtData.getText().toString());
        } catch (ParseException e) {
            return null;
        }
    }

    private void atribuirEscutadorEmEditData(EditText editText) {

        editText.setOnClickListener((view) -> {
            final Calendar calendario = Calendar.getInstance();
            calendario.setTime(mEvento.getDataInicio());
            int ano = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minuto = calendario.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view1, hour, minute) -> {
                        calendario.set(Calendar.HOUR_OF_DAY, hour);
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

    @Override
    public void onUpdate(Evento evento) {
        this.mEvento = evento;
    }

    @Override
    public void onDelete() {

    }
}
