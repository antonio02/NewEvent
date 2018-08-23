package com.newevent.usecase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.dao.evento.EventoSalvarAtualizar;
import com.newevent.model.Evento;
import com.newevent.utils.DataSnapToAtividade;
import com.newevent.utils.DataUtil;
import com.newevent.utils.UidUtil;

import java.util.Date;

public class PublicarEvento {

    public static final int EVENTO_INVALIDO = -1;

    public static final int EVENTO_SEM_ATIVIDADES = 1;
    public static final int DATA_DE_INICIO_MENOR_DATA_MINIMA_PUBLICACAO = 3;
    public static final int PUBLICADO = 4;

    private EventoSalvarAtualizar eventoSalvar;

    private UseCaseOnCompleteListener listener;
    private DatabaseReference atividadesBD;
    private int requestCode;

    public PublicarEvento(int requestCode, UseCaseOnCompleteListener listener) {
        this.requestCode = requestCode;
        this.listener = listener;
        this.eventoSalvar = new EventoSalvarAtualizar();
        this.atividadesBD = FirebaseDatabase.getInstance().getReference("atividades");
    }

    public void publicar(Evento evento) {

        if (!UidUtil.eventoTemUid(evento)) {
            listener.onComplete(EVENTO_INVALIDO, requestCode);
            return;
        }

        if (DataUtil.getMinimaPublicarEvento().getTime() > evento.getDataInicio().getTime()) {
            listener.onComplete(DATA_DE_INICIO_MENOR_DATA_MINIMA_PUBLICACAO, requestCode);
            return;
        }

        atividadesBD
                .orderByChild("evento_uid")
                .equalTo(evento.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    int soma = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            soma++;
                        }
                        if (soma > 0) {
                            evento.publicar();
                            eventoSalvar.put(evento);
                        } else {
                            listener.onComplete(EVENTO_SEM_ATIVIDADES, requestCode);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}