package com.newevent.usecase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.dao.cupom.GetCupomCodigo;
import com.newevent.dao.cupom.interfaces.GetCupomCodigoListener;
import com.newevent.model.Atividade;
import com.newevent.model.Cupom;
import com.newevent.model.Evento;
import com.newevent.model.Inscricao;
import com.newevent.model.ItemAtividade;
import com.newevent.utils.DataSnapToAtividade;
import com.newevent.utils.DataUtil;
import com.newevent.utils.UsuarioUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RealizarInscricao implements GetCupomCodigoListener{

    public static final int EVENTO_INCRICAO_FECHADA = 1;
    public static final int ATIVIDADE_COM_INCRICAO_CHEIA = 2;
    public static final int CUPOM_VENCIDO = 3;
    public static final int CUPOM_NAO_EXISTE = 4;
    public static final int INSCRICAO_REALIZADA = 5;

    private List<Atividade> atividades;
    private List<ItemAtividade> itens;
    private Inscricao inscricao;
    private DatabaseReference atividadesBD;
    private DatabaseReference root;
    private String cupomCodigo;
    private UseCaseOnCompleteListener listener;
    private int requestCode;

    private GetCupomCodigo getCupom;

    public RealizarInscricao(int requestCode, UseCaseOnCompleteListener listener){
        getCupom = new GetCupomCodigo(this);
        this.requestCode = requestCode;
        this.listener = listener;
        this.atividadesBD = FirebaseDatabase.getInstance().getReference("atividades");
        this.root = FirebaseDatabase.getInstance().getReference();
        this.atividades = new ArrayList<>();
        this.itens = new ArrayList<>();
    }

    public void Inscrever(Evento evento, List<String> atividadesUid, String cupomCodigo){

        if(evento == null || !evento.isInscricoesAbertas()){
            listener.onComplete(EVENTO_INCRICAO_FECHADA, requestCode);
            return;
        }

        inscricao = new Inscricao(UsuarioUtils.getUid(), evento.getUid());
        inscricao.setUid(FirebaseDatabase.getInstance().getReference().push().getKey());
        this.cupomCodigo = cupomCodigo;
        getAtividades(atividadesUid);
    }

    private void getAtividades(List<String> atividadesUid) {
        atividadesBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    if(atividadesUid.contains(d.getKey())){
                        atividades.add(DataSnapToAtividade.get(d));
                    }
                }
                realizarIncricao();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void realizarIncricao() {
        for(Atividade a: atividades){
            if(a.realizarIncricao()){
                itens.add(new ItemAtividade(a.getUid(), UsuarioUtils.getUid(),
                        inscricao.getUid(), a.getValor(), new Date()));
            } else {
                listener.onComplete(ATIVIDADE_COM_INCRICAO_CHEIA, requestCode);
                return;
            }
        }

        getCupom.get(cupomCodigo, inscricao.getEventoUid());
    }

    private void calcularValorTotal(int porcentagem) {
        double soma = 0;

        for(Atividade a: atividades){
            soma += a.getValor();
        }

        soma = (soma) - (soma * (1/porcentagem));

        inscricao.setValorTotal(soma);
        if(cupomCodigo != null){
            inscricao.setCupomUid(cupomCodigo);
        }

        root.child("incricoes").setValue(inscricao.toMap());
        for(ItemAtividade i: itens){
            root.child("item_atividade").setValue(i.toMap());
        }

        listener.onComplete(INSCRICAO_REALIZADA, requestCode);

    }


    @Override
    public void onGetResult(Cupom cupom) {

        if(cupom == null){
            listener.onComplete(CUPOM_NAO_EXISTE, requestCode);
            return;
        }

        if(cupom.getDataValidade().getTime() < DataUtil.getAtual().getTime()){
            listener.onComplete(CUPOM_VENCIDO, requestCode);
            return;
        }

        calcularValorTotal(cupom.getPorcentagem());
    }
}
