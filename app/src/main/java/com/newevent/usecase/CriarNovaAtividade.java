package com.newevent.usecase;

import com.newevent.dao.atividade.AtividadeSalvarAtualizar;
import com.newevent.dao.atividade.GetAtividadesDoEvento;
import com.newevent.dao.atividade.interfaces.GetAtividadesDoEventoListener;
import com.newevent.dao.evento.EventoSalvarAtualizar;
import com.newevent.model.Atividade;
import com.newevent.model.Evento;
import com.newevent.utils.UidUtil;
import com.newevent.utils.UsuarioUtils;

import java.util.Date;
import java.util.List;

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

    private AtividadeSalvarAtualizar atividadeSalvar;
    private EventoSalvarAtualizar eventoAtualizar;

    public CriarNovaAtividade(){
        atividadeSalvar = new AtividadeSalvarAtualizar();
        eventoAtualizar = new EventoSalvarAtualizar();
    }

    public int criar(Evento evento, String nome, String tipo, Double valor,
                     Date dataInicio, Date dataTermino, int maxIncricoes){

        if(!UsuarioUtils.isLogado()){
            return USUARIO_DESLOGADO;
        }

        if(UidUtil.eventoTemUid(evento)){
            return EVENTO_INVALIDO;
        }

        try {
            Atividade.validarNome(nome);
        } catch (IllegalArgumentException e){
            return NOME_INVALIDO;
        }

        try {
            Atividade.validarTipo(tipo);
        } catch (IllegalArgumentException e){
            return TIPO_INVALIDO;
        }

        try {
            Atividade.validarValor(valor);
        } catch (IllegalArgumentException e){
            return VALOR_INVALIDO;
        }

        try {
            Atividade.validarDataInicio(dataInicio);
        } catch (IllegalArgumentException e){
            return DATA_INICIO_INVALIDA;
        }

        if(evento.getDataInicio().getTime() > dataInicio.getTime()){
            return DATA_DE_INICIO_MENOR_QUE_EVENTO;
        }

        if(dataTermino == null || dataTermino.getTime() < dataInicio.getTime()){
            return DATA_TERMINO_INVALIDA;
        }

        if(evento.getDataTermino() != null &&
                evento.getDataTermino().getTime() < dataTermino.getTime()){
            return DATA_DE_TERMINO_MAIOR_QUE_EVENTO;
        }

        try {
            Atividade.validarMaxInscricoes(maxIncricoes);
        } catch (IllegalArgumentException e){
            return MAX_INCRICOES_INVALIDO;
        }

        Atividade atividade = new Atividade(evento.getUid(), nome, tipo,
                valor, dataInicio, dataTermino, maxIncricoes);
        atividade.setDonoUid(UsuarioUtils.getUid());
        atividadeSalvar.put(atividade);

        evento.addAtividadeUid(atividade.getUid());
        eventoAtualizar.put(evento);

        return SALVO;

    }
}
