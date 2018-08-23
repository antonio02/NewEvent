package com.newevent.usecase;

import com.newevent.dao.atividade.AtividadeSalvarAtualizar;
import com.newevent.dao.evento.EventoSalvarAtualizar;
import com.newevent.model.Atividade;
import com.newevent.model.Evento;
import com.newevent.utils.UidUtil;
import com.newevent.utils.UsuarioUtils;

import java.util.Date;

public class CriarNovaAtividade {

    public static final int SALVO = 0;
    public static final int NOME_INVALIDO = 1;
    public static final int TIPO_INVALIDO = 2;
    public static final int VALOR_INVALIDO = 3;
    public static final int DATA_INICIO_INVALIDA = 4;
    public static final int DATA_TERMINO_INVALIDA = 5;
    public static final int MAX_INCRICOES_INVALIDO = 6;
    public static final int USUARIO_DESLOGADO = 7;
    public static final int EVENTO_INVALIDO = 8;
    public static final int DATA_DE_INICIO_MENOR_QUE_EVENTO = 9;
    public static final int DATA_DE_TERMINO_MAIOR_QUE_EVENTO = 10;

    private int requestCode;
    private UseCaseOnCompleteListener listener;

    private AtividadeSalvarAtualizar atividadeSalvar;
    private EventoSalvarAtualizar eventoAtualizar;

    public CriarNovaAtividade(int requestCode, UseCaseOnCompleteListener listener){
        this.listener = listener;
        this.requestCode = requestCode;
        this.atividadeSalvar = new AtividadeSalvarAtualizar();
        this.eventoAtualizar = new EventoSalvarAtualizar();
    }

    public void criar(Evento evento, String nome, String tipo, Double valor,
                     Date dataInicio, Date dataTermino, int maxIncricoes){

        if(!UsuarioUtils.isLogado()){
            listener.onComplete(USUARIO_DESLOGADO, requestCode);
            return;
        }

        if(!UidUtil.eventoTemUid(evento)){
            listener.onComplete(EVENTO_INVALIDO, requestCode);
            return;
        }

        try {
            Atividade.validarNome(nome);
        } catch (IllegalArgumentException e){
            listener.onComplete(NOME_INVALIDO, requestCode);
            return;
        }

        try {
            Atividade.validarTipo(tipo);
        } catch (IllegalArgumentException e){
            listener.onComplete(TIPO_INVALIDO, requestCode);
            return;
        }

        try {
            Atividade.validarValor(valor);
        } catch (IllegalArgumentException e){
            listener.onComplete(VALOR_INVALIDO, requestCode);
            return;
        }

        try {
            Atividade.validarDataInicio(dataInicio);
        } catch (IllegalArgumentException e){
            listener.onComplete(DATA_INICIO_INVALIDA, requestCode);
            return;
        }

        if(evento.getDataInicio().getTime() > dataInicio.getTime()){
            listener.onComplete(DATA_DE_INICIO_MENOR_QUE_EVENTO, requestCode);
            return;
        }

        if(dataTermino == null || dataTermino.getTime() < dataInicio.getTime()){
            listener.onComplete(DATA_TERMINO_INVALIDA, requestCode);
            return;
        }

        if(evento.getDataTermino() != null &&
                evento.getDataTermino().getTime() < dataTermino.getTime()){
            listener.onComplete(DATA_DE_TERMINO_MAIOR_QUE_EVENTO, requestCode);
            return;
        }

        try {
            Atividade.validarMaxInscricoes(maxIncricoes);
        } catch (IllegalArgumentException e){
            listener.onComplete(MAX_INCRICOES_INVALIDO, requestCode);
            return;
        }

        Atividade atividade = new Atividade(evento.getUid(), nome, tipo,
                valor, dataInicio, dataTermino, maxIncricoes);
        atividade.setDonoUid(UsuarioUtils.getUid());
        atividadeSalvar.put(atividade);

        listener.onComplete(SALVO, requestCode);

    }
}
