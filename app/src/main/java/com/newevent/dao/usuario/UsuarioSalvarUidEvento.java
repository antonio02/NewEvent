package com.newevent.dao.usuario;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.newevent.dao.evento.GetEventoRealtime;
import com.newevent.dao.evento.GetEventoRealtimeListener;
import com.newevent.model.Evento;
import com.newevent.utils.UsuarioUtils;

public class UsuarioSalvarUidEvento {

    private DatabaseReference usuarioBD;
    private GetEventoRealtime getEvento;

    public UsuarioSalvarUidEvento(){
        usuarioBD = FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public void salvar(String uid){
        GetEventoRealtimeListener listener = new GetEventoRealtimeListener() {
            @Override
            public void onUpdate(Evento evento) {
                if(UsuarioUtils.isLogado()){
                    usuarioBD.child(UsuarioUtils.getUid()).child("eventos").child(uid).setValue(true);
                }
                getEvento.stop();
            }

            @Override
            public void onDelete() {

            }
        };
        getEvento = new GetEventoRealtime(uid, listener);
    }

}
