package com.newevent.usecase;

import com.newevent.dao.evento.EventoSalvarAtualizar;
import com.newevent.dao.usuario.UsuarioSalvarUidEvento;
import com.newevent.model.Evento;
import com.newevent.model.Local;
import com.newevent.utils.UsuarioUtils;

import java.util.Date;

public class CriarNovoEvento {

    public static final int SALVO = 0;
    public static final int NOME_DO_EVENTO_INVALIDO = 1;
    public static final int TIPO_DE_EVENTO_INVALIDO = 2;
    public static final int LOCAL_DO_EVENTO_INVALIDO = 3;
    public static final int DATA_INICIO_DO_EVENTO_INVALIDA = 4;
    public static final int USUARIO_DESLOGADO = 5;

    private EventoSalvarAtualizar eventoSalvar;
    private UsuarioSalvarUidEvento savarUid;

    public CriarNovoEvento(){
        eventoSalvar = new EventoSalvarAtualizar();
        savarUid = new UsuarioSalvarUidEvento();
    }

    public int criar(String nome, String tipo, Local local, Date dataInicio){

        if(!UsuarioUtils.isLogado()){
            return USUARIO_DESLOGADO;
        }

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

        Evento evento = new Evento(nome, tipo, local, dataInicio);
        evento.setDonoUid(UsuarioUtils.getUid());

        eventoSalvar.put(evento);
        savarUid.salvar(evento.getUid());

        return SALVO;
    }
}
