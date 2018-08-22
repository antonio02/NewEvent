package com.newevent.dao.atividade.interfaces;

import com.newevent.model.Atividade;

import java.util.List;

public interface GetAtividadesDoEventoListener {

    void onResult(List<Atividade> atividades);
}
