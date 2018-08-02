package com.newevent.controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.newevent.R;
import com.newevent.model.Evento;
import com.newevent.model.Local;
import com.newevent.utils.CriarLocalValidador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CriarEvento extends AppCompatActivity {

    private EditText mEditNome;
    private EditText mEditTipo;
    private EditText mEditLocal;
    private EditText mEditDataInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);

        setupViews();
        setupClickViews();
    }

    public void cancelar(View view) {
        finish();
    }

    public void finalizar(View view) {
        String nome = mEditNome.getText().toString();
        String tipo = mEditTipo.getText().toString();
        Local local = pegarLocal();
        Date data = pegarData();


        try {
            new Evento(null, null, null, null);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Local pegarLocal() {
        return null;
    }

    private Date pegarData() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd - MMMM - yyyy",
                    Locale.getDefault());
            return formato.parse(mEditDataInicio.getText().toString());
        } catch (ParseException e) {
            return null;
        }
    }

    private void setupViews() {
        mEditNome = findViewById(R.id.edtxt_criar_evento_nome);
        mEditTipo = findViewById(R.id.edtxt_criar_evento_tipo);
        mEditLocal = findViewById(R.id.edtxt_criar_evento_local);
        mEditDataInicio = findViewById(R.id.edtxt_criar_evento_data);
    }

    private void setupClickViews() {
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

                if (!CriarLocalValidador.isValidoEndereco(endereco)) {
                    mEdtxtEndereco.setError("Informe seu endereço");
                    mEdtxtEndereco.requestFocus();
                } else if (!CriarLocalValidador.isvalidoBairro(bairro)) {
                    mEdtxtBairro.setError("Informe seu bairro");
                    mEdtxtBairro.requestFocus();
                }else if (!CriarLocalValidador.isvalidoCidade(cidade)) {
                    mEdtxtCidade.setError("Informe sua cidade");
                    mEdtxtCidade.requestFocus();
                } else if (!CriarLocalValidador.isvalidoUf(uf)) {
                    mEdtxtUf.setError("Informe o estado");
                    mEdtxtUf.requestFocus();
                } else {
                    mDialog.dismiss();
                }
            });

        });

        mEditDataInicio.setOnClickListener((view) -> {
            final Calendar calendario = Calendar.getInstance();
            int mAno = calendario.get(Calendar.YEAR);
            int mMes = calendario.get(Calendar.MONTH);
            int mDia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, year, month, day) -> {
                        calendario.set(year, month, day);
                        mEditDataInicio.setText(new SimpleDateFormat("dd - MMMM - yyyy" ,
                                Locale.getDefault())
                                .format(calendario.getTime()));
                    }, mAno, mMes, mDia);

//            Definir data mínima a ser escolhida, sendo essa a data atual.
            datePickerDialog.getDatePicker().setMinDate(calendario.getTimeInMillis());
            datePickerDialog.show();
        });
    }

}
