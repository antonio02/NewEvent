package com.newevent.utils;

import com.newevent.model.Evento;
import com.newevent.model.Local;

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

        try {
            Evento.validarNome(nome);
        } catch (IllegalArgumentException e){
            return NOME_DO_EVENTO_INVALIDO;
        }

        try {
            Evento.validarTipo(tipo);
        } catch (IllegalArgumentException e){
            return TIPO_DE_EVENTO_INVALIDO;
        }

        try {
            Evento.validarLocal(local);
        } catch (IllegalArgumentException e){
            return LOCAL_DO_EVENTO_INVALIDO;
        }

        try {
            Evento.validarDataInicio(dataInicio);
        } catch (IllegalArgumentException e){
            return DATA_INICIO_DO_EVENTO_INVALIDA;
        }

        return EVENTO_VALIDO;
    }

}
