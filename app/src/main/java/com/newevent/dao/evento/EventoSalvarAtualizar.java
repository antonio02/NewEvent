package com.newevent.dao.evento;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.newevent.model.Evento;

public class EventoSalvarAtualizar {

    private DatabaseReference eventosBD;

    public EventoSalvarAtualizar(){
        eventosBD = FirebaseDatabase.getInstance().getReference("eventos");
    }

    public void put(Evento evento){
        if(evento.getUid() == null){
            String uid = eventosBD.push().getKey();
            evento.setUid(uid);
            assert uid != null;
            eventosBD.child(uid).updateChildren(evento.toMap());

        } else {
            eventosBD.child(evento.getUid()).updateChildren(evento.toMap());
        }

    }
}
