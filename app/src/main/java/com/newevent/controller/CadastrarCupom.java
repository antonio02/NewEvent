package com.newevent.controller;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.newevent.R;
import com.newevent.usecase.CriarCupom;
import com.newevent.usecase.UseCaseOnCompleteListener;
import com.newevent.utils.UsuarioUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CadastrarCupom extends AppCompatActivity implements UseCaseOnCompleteListener{

    private EditText edtxtCodigo;
    private EditText edtxtPorcetagem;
    private EditText edtxtQuantidade;
    private EditText edtxtData;

    private CriarCupom criarCupom;

    private String eventoUid;

    private static int CRIAR_CUPOM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cupom);
        binding();
        pegarEventoUid();
    }

    private void pegarEventoUid() {
        if(getIntent().hasExtra("evento_uid")){
            eventoUid = getIntent().getStringExtra("evento_uid");

        } else {
            finish();
        }
    }

    private void binding() {
        this.edtxtData = findViewById(R.id.edtxt_cupom_data);
        this.edtxtCodigo = findViewById(R.id.edtxt_cupom_codigo);
        this.edtxtPorcetagem = findViewById(R.id.edtxt_cupom_porcetagem);
        this.edtxtQuantidade = findViewById(R.id.edtxt_cupom_quantidade);

        this.criarCupom = new CriarCupom(CRIAR_CUPOM, this);
    }

    private Date pegarData() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                    Locale.getDefault());
            return formato.parse(edtxtData.getText().toString());
        } catch (ParseException e) {
            return null;
        }
    }

    public void abrirDatePicker(View view) {
        final Calendar calendario = Calendar.getInstance();
        int mAno = calendario.get(Calendar.YEAR);
        int mMes = calendario.get(Calendar.MONTH);
        int mDia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (datePicker, year, month, day) -> {
                    calendario.set(year, month, day);
                    calendario.set(Calendar.HOUR_OF_DAY, 23);
                    calendario.set(Calendar.MINUTE, 59);
                    edtxtData.setText(new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                            Locale.getDefault()).format(calendario.getTime()));
                }, mAno, mMes, mDia);

        datePickerDialog.getDatePicker().setMinDate(calendario.getTimeInMillis());
        datePickerDialog.show();
    }

    public void cancelar(View view) {
        finish();
    }

    public void finalizar(View view) {
        String codigo = edtxtCodigo.getText().toString().trim();
        int porcentagem = pegarPorcentagem();
        int quantidade = pegarQuantidade();
        Date dataValidade = pegarData();

        criarCupom.criar(eventoUid, UsuarioUtils.getUid(), codigo, porcentagem, dataValidade, quantidade);

    }

    private int pegarQuantidade() {
        try{
            return Integer.valueOf(edtxtQuantidade.getText().toString().trim());
        } catch (Exception e){
            return -1;
        }
    }

    private int pegarPorcentagem() {
        try{
            return Integer.valueOf(edtxtPorcetagem.getText().toString().trim());
        } catch (Exception e){
            return -1;
        }
    }

    @Override
    public void onComplete(int resultCode, int requestCode) {
        if(requestCode == CRIAR_CUPOM){
            switch (resultCode){

                case CriarCupom.CODIGO_INVALIDO:
                    edtxtCodigo.setError("Codigo Invalido");
                    edtxtCodigo.requestFocus();
                    break;

                case CriarCupom.PORCENTAGEM_INVALIDA:
                    edtxtPorcetagem.setError("Porcentagem invalida (0-100)");
                    edtxtPorcetagem.requestFocus();
                    break;

                case CriarCupom.QUANTIDADE_INVALIDA:
                    edtxtQuantidade.setError("Quantidade deve ser maior que 1");
                    edtxtQuantidade.requestFocus();
                    break;

                case CriarCupom.DATA_INVALIDA:
                    Toast.makeText(this,
                            "Data vazia ou menor que a data atual",
                            Toast.LENGTH_SHORT).show();
                    break;

                case CriarCupom.CUPOM_CRIADO:
                    finish();
                    break;
            }
        }
    }
}
