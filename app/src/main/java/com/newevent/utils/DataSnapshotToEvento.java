package com.newevent.utils;

import com.google.firebase.database.DataSnapshot;
import com.newevent.model.Evento;
import com.newevent.model.Local;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataSnapshotToEvento {

    public static Evento get(DataSnapshot d){
        Map<String, Object> map = new HashMap<>();

        map.put("uid", d.getKey());
        map.put("dono_uid", d.child("dono_uid").getValue());

        map.put("nome", d.child("nome").getValue());
        map.put("tipo", d.child("tipo").getValue());
        map.put("data_inicio", new Date((Long) d.child("data_inicio").getValue()));
        map.put("local", getlocal(d.child("local")));
        map.put("publicado", d.child("publicado").getValue());
        map.put("atividades", getAtividadesUid(d.child("atividades")));

        return Evento.mapToEvento(map);
    }

    private static Map<String, Object> getAtividadesUid(DataSnapshot d) {
        Map<String, Object> map = new HashMap<>();
        for (DataSnapshot key: d.getChildren()) {
            map.put(key.getKey(), true);
        }
        return map;
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