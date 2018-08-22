package com.newevent.dao.evento;

import com.newevent.model.Evento;

import java.util.List;

public interface GetEventosDoUsuarioListener {

    void onResult(List<Evento> eventos);
}
