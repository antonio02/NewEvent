package com.newevent.dao.atividade.interfaces;

import com.newevent.model.Atividade;

import java.util.List;

public interface GetAtividadesDoEventoRealTimeListener {

    void onUpdate(List<Atividade> atividades);
}
