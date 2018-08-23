package com.newevent.dao.evento.interfaces;

import com.newevent.model.Evento;

import java.util.List;

public interface GetEventosDoUsuarioListener {

    void onGetFinish(List<Evento> eventos);
}
