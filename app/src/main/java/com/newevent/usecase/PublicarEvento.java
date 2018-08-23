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

    private UseCaseOnCompleteListener listener;
    private int requestCode;

    public PublicarEvento(int requestCode, UseCaseOnCompleteListener listener){
        this.requestCode = requestCode;
        this.listener = listener;
        this.eventoSalvar = new EventoSalvarAtualizar();
    }

    public void publicar(Evento evento){

        if(UidUtil.eventoTemUid(evento)){
            listener.onComplete(EVENTO_INVALIDO, requestCode);
            return;
        }

        if(evento.getDataTermino() == null){
            listener.onComplete(EVENTO_SEM_DATA_TERMINO, requestCode);
            return;
        }

        if(DataUtil.getMinimaPublicarEvento().getTime() < evento.getDataInicio().getTime()){
            listener.onComplete(DATA_DE_INICIO_MENOR_DATA_MINIMA_PUBLICACAO, requestCode);
            return;
        }

        evento.publicar();
        eventoSalvar.put(evento);
        listener.onComplete(PUBLICADO, requestCode);

    }
}
