package com.newevent.dao.evento;

import com.newevent.model.Evento;

public interface GetEventoRealtimeListener {

    void onUpdate(Evento evento);
    void onDelete();
}
