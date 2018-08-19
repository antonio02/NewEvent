package com.newevent.utils;

import com.newevent.model.Local;

import java.util.Calendar;
import java.util.Date;

public class CriarEventoValidador {

    public static final int EVENTO_VALIDO = 0;
    public static final int NOME_DO_EVENTO_INVALIDO = 1;
    public static final int TIPO_DE_EVENTO_INVALIDO = 2;
    public static final int LOCAL_DO_EVENTO_INVALIDO = 3;
    public static final int DATA_INICIO_DO_EVENTO_INVALIDA = 4;

    public CriarEventoValidador() {
    }

    public static int validarNovoEvento(String nome, String tipo, Local local, Date dataInicio) {

        if(nome == null || nome.trim().length() < 6) {
            return NOME_DO_EVENTO_INVALIDO;
        }
        if(tipo == null || tipo.trim().length() < 5) {
            return TIPO_DE_EVENTO_INVALIDO;
        }
        if(local == null) {
            return LOCAL_DO_EVENTO_INVALIDO;
        }
        if(dataInicio == null ||
                dataInicio.getTime() < (Calendar.getInstance().getTimeInMillis() + 43200000)) {
            return DATA_INICIO_DO_EVENTO_INVALIDA;
        }
        return EVENTO_VALIDO;
    }

}
