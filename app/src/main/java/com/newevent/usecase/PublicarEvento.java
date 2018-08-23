package com.newevent.usecase;

import com.newevent.dao.evento.EventoSalvarAtualizar;
import com.newevent.model.Evento;
import com.newevent.utils.DataUtil;
import com.newevent.utils.UidUtil;

public class PublicarEvento {

    public static final int EVENTO_INVALIDO = -1;

    public static final int EVENTO_SEM_ATIVIDADES = 1;
    public static final int EVENTO_SEM_DATA_TERMINO = 2;
    public static final int DATA_DE_INICIO_MENOR_DATA_MINIMA_PUBLICACAO = 3;
    public static final int PUBLICADO = 4;

    private EventoSalvarAtualizar eventoSalvar;

    public PublicarEvento(){
        eventoSalvar = new EventoSalvarAtualizar();
    }

    public int publicar(Evento evento){

        if(UidUtil.eventoTemUid(evento)){
            return EVENTO_INVALIDO;
        }

        if(evento.getDataTermino() == null){
            return EVENTO_SEM_DATA_TERMINO;
        }

        if(DataUtil.getMinimaPublicarEvento().getTime() < evento.getDataInicio().getTime()){
            return DATA_DE_INICIO_MENOR_DATA_MINIMA_PUBLICACAO;
        }

        evento.publicar();
        eventoSalvar.put(evento);
        return PUBLICADO;

    }
}
