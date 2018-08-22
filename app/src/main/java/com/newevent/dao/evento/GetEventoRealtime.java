package com.newevent.dao.evento;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.utils.DataSnapshotToEvento;

public class GetEventoRealtime implements ValueEventListener{

    private GetEventoRealtimeListener listener;
    private DatabaseReference eventoBD;

    public GetEventoRealtime(String eventoUid, GetEventoRealtimeListener listener){
        eventoBD = FirebaseDatabase.getInstance().getReference("eventos").child(eventoUid);
        eventoBD.addValueEventListener(this);
        this.listener = listener;
    }

    public void stop(){
        eventoBD.removeEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()){
            listener.onUpdate(DataSnapshotToEvento.get(dataSnapshot));
        } else {
            listener.onDelete();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
