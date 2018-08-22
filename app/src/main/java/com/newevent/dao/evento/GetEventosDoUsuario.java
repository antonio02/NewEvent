package com.newevent.dao.evento;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.model.Evento;
import com.newevent.utils.DataSnapshotToEvento;
import com.newevent.utils.UsuarioUtils;

import java.util.ArrayList;
import java.util.List;

public class GetEventosDoUsuario {

    private DatabaseReference eventosBD;
    private List<Evento> eventos;
    private GetEventosDoUsuarioListener listener;

    public GetEventosDoUsuario(GetEventosDoUsuarioListener listener){
        this.listener = listener;
        this.eventosBD = FirebaseDatabase.getInstance().getReference("eventos");
        this.eventos = new ArrayList<>();
    }

    public void get(){
        if(UsuarioUtils.isLogado()){
            this.eventosBD.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    eventos.clear();
                    for(DataSnapshot d: dataSnapshot.getChildren()){
                        if(UsuarioUtils.getUid().equals(d.child("dono_uid").getValue())){
                            eventos.add(DataSnapshotToEvento.get(d));
                        }
                    }
                    listener.onResult(eventos);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
