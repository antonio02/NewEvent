package com.newevent.controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.newevent.R;
import com.newevent.model.Local;
import com.newevent.usecase.CriarNovoEvento;
import com.newevent.usecase.UseCaseOnCompleteListener;
import com.newevent.utils.CriarLocalValidador;
import com.newevent.utils.UsuarioUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CriarEvento extends AppCompatActivity implements UseCaseOnCompleteListener{

    private EditText mEditNome;
    private EditText mEditTipo;
    private EditText mEditLocal;
    private EditText mEditDataInicio;

    private CriarNovoEvento criarEvento;

    private final int CRIAR_EVENTO = 0;

    private Local local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);

        criarEvento = new CriarNovoEvento(CRIAR_EVENTO, this);

        if(!UsuarioUtils.isLogado()){
            finish();
        }

        inicializarViews();
        atribuirEscutadorEmEditLocal();
        atribuirEscutadorEmEditDataInicio();
    }

    public void finalizar(View view) {
        String nome = mEditNome.getText().toString();
        String tipo = mEditTipo.getText().toString();
        Date data = pegarData();

        criarEvento.criar(nome, tipo, local, data);

    }

    public void cancelar(View view) {
        finish();
    }

    private Date pegarData() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                    Locale.getDefault());
            return formato.parse(mEditDataInicio.getText().toString());
        } catch (ParseException e) {
            return null;
        }
    }

    private void inicializarViews() {
        mEditNome = findViewById(R.id.edtxt_criar_evento_nome);
        mEditTipo = findViewById(R.id.edtxt_criar_evento_tipo);
        mEditLocal = findViewById(R.id.edtxt_criar_evento_local);
        mEditDataInicio = findViewById(R.id.edtxt_criar_evento_data);
    }

    private void inicializarLocal(String endereco, String bairro, String cidade,
                                  String uf, String complemento) {
        local = new Local(endereco, bairro, cidade, uf);
        local.setComplemento(complemento);
    }

    private void atribuirEscutadorEmEditLocal() {
        mEditLocal.setOnClickListener((View view) -> {
            View inflater = getLayoutInflater()
                    .inflate(R.layout.dialog_adicionar_local, null);

            EditText mEdtxtEndereco = inflater.findViewById(R.id.edtxt_local_endereco);
            EditText mEdtxtBairro = inflater.findViewById(R.id.edtxt_local_bairro);
            EditText mEdtxtComplemento = inflater.findViewById(R.id.edtxt_local_complemento);
            EditText mEdtxtCidade = inflater.findViewById(R.id.edtxt_local_cidade);
            EditText mEdtxtUf = inflater.findViewById(R.id.edtxt_local_uf);

            AlertDialog mDialog = new AlertDialog.Builder(this).create();

            mDialog.setView(inflater);
            mDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Ok", (dialog, which) -> {});
            mDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancelar",
                    (dialog, which) -> dialog.dismiss());
            mDialog.show();
            mDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String endereco = mEdtxtEndereco.getText().toString();
                String bairro = mEdtxtBairro.getText().toString();
                String complemento = mEdtxtComplemento.getText().toString();
                String cidade = mEdtxtCidade.getText().toString();
                String uf = mEdtxtUf.getText().toString();

                switch (CriarLocalValidador.validar(endereco, bairro, cidade, uf)){
                    case CriarLocalValidador.ENDERECO_INVALIDO:
                        mEdtxtEndereco.setError("Informe seu endereço");
                        mEdtxtEndereco.requestFocus();
                        break;

                    case CriarLocalValidador.BAIRRO_INVALIDO:
                        mEdtxtBairro.setError("Informe seu bairro");
                        mEdtxtBairro.requestFocus();
                        break;

                    case CriarLocalValidador.CIDADE_INVALIDO:
                        mEdtxtCidade.setError("Informe sua cidade");
                        mEdtxtCidade.requestFocus();
                        break;

                    case CriarLocalValidador.UF_INVALIDO:
                        mEdtxtUf.setError("Informe o estado");
                        mEdtxtUf.requestFocus();
                        break;

                    case CriarLocalValidador.VALIDO:
                        inicializarLocal(endereco, bairro, cidade, uf, complemento);
                        mEditLocal.setText(endereco);
                        mDialog.dismiss();
                        break;
                }
            });

        });
    }

    private void atribuirEscutadorEmEditDataInicio() {
        mEditDataInicio.setOnClickListener((view) -> {
            final Calendar calendario = Calendar.getInstance();
            int mAno = calendario.get(Calendar.YEAR);
            int mMes = calendario.get(Calendar.MONTH);
            int mDia = calendario.get(Calendar.DAY_OF_MONTH);
            int mHora = calendario.get(Calendar.HOUR);
            int mMinuto = calendario.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view1, hour, minute) -> {
                        calendario.set(Calendar.HOUR_OF_DAY, hour);
                        calendario.set(Calendar.MINUTE, minute);
                        mEditDataInicio.setText(new SimpleDateFormat("dd - MMMM - yyyy HH:mm",
                                Locale.getDefault()).format(calendario.getTime()));
                    }, mHora, mMinuto, true);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, year, month, day) -> {
                        calendario.set(year, month, day);
                        timePickerDialog.show();
                    }, mAno, mMes, mDia);

//            Definir data mínima a ser escolhida, sendo essa a data atual.
            datePickerDialog.getDatePicker().setMinDate(calendario.getTimeInMillis());
            datePickerDialog.show();
        });
    }

    @Override
    public void onComplete(int resultCode, int requestCode) {
        if(requestCode == CRIAR_EVENTO){
            switch(resultCode){

                case CriarNovoEvento.USUARIO_DESLOGADO:
                    finish();
                    break;

                case CriarNovoEvento.NOME_DO_EVENTO_INVALIDO:
                    mEditNome.setError("Nome vazio ou menor que 6 caracteres");
                    mEditNome.requestFocus();
                    break;

                case CriarNovoEvento.TIPO_DE_EVENTO_INVALIDO:
                    mEditTipo.setError("Tipo vazio ou menor que 5 caracteres");
                    mEditTipo.requestFocus();
                    break;

                case CriarNovoEvento.LOCAL_DO_EVENTO_INVALIDO:
                    Toast.makeText(this, "Local do evento vazio",
                            Toast.LENGTH_SHORT).show();
                    break;

                case CriarNovoEvento.DATA_INICIO_DO_EVENTO_INVALIDA:
                    Toast.makeText(this, "Data vazia ou invalida",
                            Toast.LENGTH_SHORT).show();
                    break;

                case CriarNovoEvento.DATA_DE_INICIO_MENOR_DATA_MINIMA_CRIACAO:
                    Toast.makeText(this, "A data precisa ter pelo menos 6 horas para o inicio do evento",
                            Toast.LENGTH_LONG).show();
                    break;

                case CriarNovoEvento.SALVO:
                    finish();
                    break;
            }
        }
    }
}
