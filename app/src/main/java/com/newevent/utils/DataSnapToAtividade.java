package com.newevent.utils;

import com.google.firebase.database.DataSnapshot;
import com.newevent.model.Atividade;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataSnapToAtividade {

    public static Atividade get(DataSnapshot d){
        Map<String, Object> map = new HashMap<>();

        map.put("uid", d.getKey());
        map.put("dono_uid", d.child("dono_uid").getValue());
        map.put("evento_uid", d.child("evento_uid").getValue());

        map.put("nome", d.child("nome").getValue());
        map.put("tipo", d.child("tipo").getValue());
        map.put("valor", d.child("valor").getValue());
        map.put("data_inicio", new Date((Long) d.child("data_inicio").getValue()));
        map.put("data_termino", new Date((Long) d.child("data_termino").getValue()));
        map.put("max_inscricoes", d.child("max_inscricoes").getValue());
        map.put("inscricoes_abertas", d.child("inscricoes_abertas").getValue());

        return Atividade.mapToAtividade(map);

    }
}
