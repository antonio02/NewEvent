package com.newevent.utils;

import com.google.firebase.database.DataSnapshot;
import com.newevent.model.Evento;
import com.newevent.model.Local;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataSnapToEvento {

    public static Evento get(DataSnapshot d){
        Map<String, Object> map = new HashMap<>();

        map.put("uid", d.getKey());
        map.put("dono_uid", d.child("dono_uid").getValue());

        map.put("nome", d.child("nome").getValue());
        map.put("tipo", d.child("tipo").getValue());
        map.put("data_inicio", new Date((Long) d.child("data_inicio").getValue()));
        map.put("data_termino", d.child("data_termino").exists() ?
                new Date((Long) d.child("data_termino").getValue()) : null);
        map.put("inscricoes_abertas", d.child("inscricoes_abertas").getValue());
        map.put("local", getlocal(d.child("local")));
        map.put("publicado", d.child("publicado").getValue());

        return Evento.mapToEvento(map);
    }

    private static Local getlocal(DataSnapshot d) {
        Map<String, Object> map = new HashMap<>();

        map.put("endereco", d.child("endereco").getValue());
        map.put("bairro", d.child("bairro").getValue());
        map.put("cidade", d.child("cidade").getValue());
        map.put("uf", d.child("uf").getValue());
        map.put("complemento", d.child("complemento").getValue());

        return Local.mapToLocal(map);
    }
}
