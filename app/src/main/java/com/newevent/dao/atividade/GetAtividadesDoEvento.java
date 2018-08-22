package com.newevent.dao.atividade;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.dao.atividade.interfaces.GetAtividadesDoEventoListener;
import com.newevent.model.Atividade;
import com.newevent.utils.DataSnapToAtividade;
import com.newevent.utils.UidUtil;
import com.newevent.utils.UsuarioUtils;

import java.util.ArrayList;
import java.util.List;

public class GetAtividadesDoEvento {

    private DatabaseReference atividadeBD;
    private GetAtividadesDoEventoListener listener;
    private List<Atividade> atividades;
    private String eventoUid;

    public GetAtividadesDoEvento(String eventoUid, GetAtividadesDoEventoListener listener){
        this.listener = listener;
        this.atividadeBD = FirebaseDatabase.getInstance().getReference("atividades");
        this.atividades = new ArrayList<>();
        this.eventoUid = eventoUid;
        get();
    }

    public void get(){
        if(UsuarioUtils.isLogado() && UidUtil.isValido(eventoUid)){
            this.atividadeBD.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    atividades.clear();
                    for(DataSnapshot d: dataSnapshot.getChildren()){
                        if(d.child("evento").getValue().equals(eventoUid)){
                            atividades.add(DataSnapToAtividade.get(d));
                        }
                    }
                    listener.onResult(atividades);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


}
