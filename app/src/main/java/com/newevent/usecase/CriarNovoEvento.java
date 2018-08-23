package com.newevent.usecase;

import com.newevent.dao.evento.EventoSalvarAtualizar;
import com.newevent.model.Evento;
import com.newevent.model.Local;
import com.newevent.utils.DataUtil;
import com.newevent.utils.UsuarioUtils;

import java.util.Date;

public class CriarNovoEvento {

    public static final int SALVO = 0;
    public static final int NOME_DO_EVENTO_INVALIDO = 1;
    public static final int TIPO_DE_EVENTO_INVALIDO = 2;
    public static final int LOCAL_DO_EVENTO_INVALIDO = 3;
    public static final int DATA_INICIO_DO_EVENTO_INVALIDA = 4;
    public static final int USUARIO_DESLOGADO = 5;
    public static final int DATA_DE_INICIO_MENOR_DATA_MINIMA_CRIACAO = 6;

    private EventoSalvarAtualizar eventoSalvar;

    private UseCaseOnCompleteListener listener;
    private int requestCode;

    public CriarNovoEvento(int requestCode, UseCaseOnCompleteListener listener){
        this.requestCode = requestCode;
        this.listener = listener;
        eventoSalvar = new EventoSalvarAtualizar();
    }

    public void criar(String nome, String tipo, Local local, Date dataInicio){

        if(!UsuarioUtils.isLogado()){
            listener.onComplete(USUARIO_DESLOGADO, requestCode);
            return;
        }

        try {
            Evento.validarNome(nome);
        } catch (IllegalArgumentException e){
            listener.onComplete(NOME_DO_EVENTO_INVALIDO, requestCode);
            return;
        }

        try {
            Evento.validarTipo(tipo);
        } catch (IllegalArgumentException e){
            listener.onComplete(TIPO_DE_EVENTO_INVALIDO, requestCode);
            return;
        }

        try {
            Evento.validarLocal(local);
        } catch (IllegalArgumentException e){
            listener.onComplete(LOCAL_DO_EVENTO_INVALIDO, requestCode);
            return;
        }

        try {
            Evento.validarData(dataInicio);
        } catch (IllegalArgumentException e){
            listener.onComplete(DATA_INICIO_DO_EVENTO_INVALIDA, requestCode);
            return;
        }

        if(DataUtil.getMinimaCriarEvento().getTime() > dataInicio.getTime()){
            listener.onComplete(DATA_DE_INICIO_MENOR_DATA_MINIMA_CRIACAO, requestCode);
            return;
        }

        Evento evento = new Evento(nome, tipo, local, dataInicio);
        evento.setDonoUid(UsuarioUtils.getUid());

        eventoSalvar.put(evento);

        listener.onComplete(SALVO, requestCode);
    }
}
