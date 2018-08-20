package com.newevent.utils;

import com.google.firebase.database.DataSnapshot;
import com.newevent.model.Evento;
import com.newevent.model.Local;

import java.util.Date;

public class DataSnapshotTo {

    public static Evento evento(DataSnapshot d){
        String uid = d.getKey();
        String donoUid = (String) d.child("dono_uid").getValue();
        String nome = (String) d.child("nome").getValue();
        String tipo = (String) d.child("tipo").getValue();
        Date dataInicio = new Date((Long) d.child("data_inicio").getValue());
        Local local = local(d.child("local"));
        Evento evento = new Evento(nome, tipo, local, dataInicio);
        evento.setUid(uid);
        evento.setDonoUid(donoUid);
        return evento;
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
