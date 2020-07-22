package com.newevent.dao.evento;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.model.Evento;

import java.util.Date;

public class EventoSalvarAtualizar {

    private DatabaseReference eventosBD;
    private DatabaseReference atividadesBD;

    public EventoSalvarAtualizar(){
        this.eventosBD = FirebaseDatabase.getInstance().getReference("eventos");
        this.atividadesBD = FirebaseDatabase.getInstance().getReference("atividades");
    }

    public void put(Evento evento){
        if(evento.isPublicado()){
            atualizarPublicado(evento);
            return;
        }
        if(evento.getUid() == null){
            String uid = eventosBD.push().getKey();
            evento.setUid(uid);
            assert uid != null;
            eventosBD.child(uid).updateChildren(evento.toMap());

        } else {
            eventosBD.child(evento.getUid()).updateChildren(evento.toMap());
        }

    }

    private void atualizarPublicado(Evento evento) {
        atividadesBD
                .orderByChild("evento_uid")
                .equalTo(evento.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long maior = 0;
                        long atual = 0;
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            atual = (Long) d.child("data_termino").getValue();
                            if(atual > maior){
                                maior = atual;
                            }
                        }
                        evento.setDataTermino(new Date(maior));
                        eventosBD.child(evento.getUid()).updateChildren(evento.toMap());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
