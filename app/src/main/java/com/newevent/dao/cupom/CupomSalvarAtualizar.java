package com.newevent.dao.cupom;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.newevent.model.Cupom;

public class CupomSalvarAtualizar {

    private DatabaseReference cupomBD;

    public CupomSalvarAtualizar(){
        cupomBD = FirebaseDatabase.getInstance().getReference("cupons");
    }

    public void put(Cupom cupom){
        if(cupom.getUid() == null){
            String uid = cupomBD.push().getKey();
            cupom.setUid(uid);
            assert uid != null;
            cupomBD.child(uid).updateChildren(cupom.toMap());

        } else {
            cupomBD.child(cupom.getUid()).updateChildren(cupom.toMap());
        }
    }
}
