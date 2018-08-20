package com.newevent.utils;

import com.google.firebase.database.DataSnapshot;
import com.newevent.model.Evento;
import com.newevent.model.Local;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataSnapshotTo {

    public static Evento evento(DataSnapshot d){
        Map<String, Object> map = new HashMap<>();
        map.put("uid", d.getKey());
        map.put("dono_uid", d.child("dono_uid").getValue());

        map.put("nome", d.child("nome").getValue());
        map.put("tipo", d.child("tipo").getValue());
        map.put("data_inicio", new Date((Long) d.child("data_inicio").getValue()));
        map.put("local", local(d.child("local")));
        map.put("publicado", d.child("publicado").getValue());

        return Evento.mapToEvento(map);
    }

    private static Local local(DataSnapshot d) {
        String endereco = (String) d.child("endereco").getValue();
        String bairro = (String) d.child("bairro").getValue();
        String cidade = (String) d.child("cidade").getValue();
        String complemento = (String) d.child("complemento").getValue();
        String uf = (String) d.child("uf").getValue();
        Local local = new Local(endereco, bairro, cidade, uf);
        local.setComplemento(complemento);
        return local;
    }
}
