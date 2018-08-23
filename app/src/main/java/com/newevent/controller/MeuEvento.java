package com.newevent.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newevent.R;
import com.newevent.dao.evento.GetEventoRealtime;
import com.newevent.dao.evento.interfaces.GetEventoRealtimeListener;
import com.newevent.model.Evento;
import com.newevent.model.Local;
import com.newevent.utils.UidUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MeuEvento extends AppCompatActivity implements GetEventoRealtimeListener {


    private EditText edTipoEvento;
    private EditText edNomeEvento;
    private EditText edDataInicioEvento;
    private EditText edDataFimEvento;

    private TextView txtEnderecoLocalEvento;
    private TextView txtBairroLocalEvento;
    private TextView txtCidadeLocalEvento;
    private TextView txtUfLocalEvento;
    private TextView txtComplementoLocalEvento;

    private CardView cardLocal;
    private Button btnNovaAtividade;
    private Button btnEditar;
    private LinearLayout btnsFinalizarCancelar;


    private String eventoUid;
    private Evento evento;
    private GetEventoRealtime getEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_evento);

        carregarEvento();
        inicializarViews();
    }

    @Override
    protected void onDestroy() {
        getEvento.stop();
        super.onDestroy();
    }

    @Override
    public void onUpdate(Evento evento) {
        this.evento = evento;
        mostrarDadosDoEvento();
        listarAtividades();
    }

    private void listarAtividades() {

    }

    private void mostrarDadosDoEvento() {
        if(evento == null) {
            Toast.makeText(this, "Evento não carregou!", Toast.LENGTH_SHORT).show();
            return;
        }

        Local local = evento.getLocal();
        SimpleDateFormat format = new SimpleDateFormat("dd - MMMM - yyyy",
                Locale.getDefault());

        edNomeEvento.setText(evento.getNome());
        edTipoEvento.setText(evento.getTipo());

        txtEnderecoLocalEvento.setText(local.getEndereco());
        txtBairroLocalEvento.setText(local.getBairro());
        txtCidadeLocalEvento.setText(local.getCidade());
        txtUfLocalEvento .setText(local.getUf());
        txtComplementoLocalEvento.setText(local.getComplemento());

        edDataInicioEvento.setText(format.format(evento.getDataInicio()));

        if (evento.getDataTermino() != null) {
            edDataFimEvento.setText(format.format(evento.getDataTermino()));
        }
    }

    @Override
    public void onDelete() {
        finish();
    }

    public void abrirCriarAtividade(View view) {
        Intent it = new Intent(this, CriarAtividade.class);
        it.putExtra("evento_uid", eventoUid);
        startActivity(it);
    }

    public void abilitarEdicao(View view) {

        btnEditar.setVisibility(View.GONE);
        btnNovaAtividade.setVisibility(View.GONE);
        btnsFinalizarCancelar.setVisibility(View.VISIBLE);

        edTipoEvento.setFocusableInTouchMode(true);
        edNomeEvento.setFocusableInTouchMode(true);

        atribuirEscutadorEmEdData();
    }

    private void atribuirEscutadorEmEdData() {
        edDataInicioEvento.setOnClickListener((view) -> {
            final Calendar calendario = Calendar.getInstance();
            int mAno = calendario.get(Calendar.YEAR);
            int mMes = calendario.get(Calendar.MONTH);
            int mDia = calendario.get(Calendar.DAY_OF_MONTH);
            int mHora = calendario.get(Calendar.HOUR_OF_DAY);
            int mMinuto = calendario.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view1, hour, minute) -> {
                        calendario.set(Calendar.HOUR_OF_DAY, hour);
                        calendario.set(Calendar.MINUTE, minute);
                        edDataInicioEvento.setText(new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                                Locale.getDefault()).format(calendario.getTime()));
                    }, mHora, mMinuto, true);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, year, month, day) -> {
                        calendario.set(year, month, day);
                        timePickerDialog.show();
                    }, mAno, mMes, mDia);

            datePickerDialog.getDatePicker().setMinDate(calendario.getTimeInMillis());
            datePickerDialog.show();
        });
    }

    public void finalizarEdicao(View view) {
    }

    public void cancelarEdicao(View view) {

        btnsFinalizarCancelar.setVisibility(View.GONE);
        btnNovaAtividade.setVisibility(View.VISIBLE);
        btnEditar.setVisibility(View.VISIBLE);

        edTipoEvento.setFocusable(false);
        edNomeEvento.setFocusable(false);

        mostrarDadosDoEvento();
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

    private void inicializarViews() {
        edTipoEvento = findViewById(R.id.edtxt_meuevento_tipo);
        edNomeEvento = findViewById(R.id.edtxt_meuevento_nome);
        edDataInicioEvento = findViewById(R.id.edtxt_meuevento_data_inicio);
        edDataFimEvento = findViewById(R.id.edtxt_meuevento_data_fim);

        txtEnderecoLocalEvento = findViewById(R.id.txt_meuevento_local_endereco);
        txtBairroLocalEvento = findViewById(R.id.txt_meuevento_local_bairro);
        txtComplementoLocalEvento = findViewById(R.id.txt_meuevento_local_complemento);
        txtCidadeLocalEvento = findViewById(R.id.txt_meuevento_local_cidade);
        txtUfLocalEvento = findViewById(R.id.txt_meuevento_local_uf);

        btnNovaAtividade = findViewById(R.id.btn_meuevento_nova_atividade);
        btnEditar = findViewById(R.id.btn_meuevento_editar);
        cardLocal = findViewById(R.id.card_meuevento_local);
        btnsFinalizarCancelar = findViewById(R.id.buttons_finalizar_cancelar);
    }
}
