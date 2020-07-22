package com.newevent.dao.atividade;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.newevent.model.Atividade;

public class AtividadeSalvarAtualizar {

    private DatabaseReference atividadesBD;

    public AtividadeSalvarAtualizar(){
        atividadesBD = FirebaseDatabase.getInstance().getReference("atividades");
    }

    public void put(Atividade atividade){
        if(atividade.getUid() == null){
            String uid = atividadesBD.push().getKey();
            atividade.setUid(uid);
            assert uid != null;
            atividadesBD.child(uid).updateChildren(atividade.toMap());

        } else {
            atividadesBD.child(atividade.getUid()).updateChildren(atividade.toMap());
        }

    }
}
