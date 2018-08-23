package com.newevent.dao.cupom;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.dao.cupom.interfaces.GetCupomCodigoListener;
import com.newevent.utils.DataSnapToCupom;

public class GetCupomCodigo {

    private GetCupomCodigoListener listener;
    private DatabaseReference cuponsBD;

    public GetCupomCodigo(GetCupomCodigoListener listener){
        this.listener = listener;
        this.cuponsBD = FirebaseDatabase.getInstance().getReference("cupons");
    }

    public void get(String codigo, String eventoUid){
        cuponsBD
                .orderByChild("evento_uid")
                .equalTo(eventoUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.getChildren()){
                            if(codigo.equals(d.child("codigo").getValue())){
                                listener.onGetResult(DataSnapToCupom.get(d));
                            }
                        }
                        listener.onGetResult(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
