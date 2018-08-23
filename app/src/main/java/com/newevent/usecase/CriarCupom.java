package com.newevent.usecase;


import com.newevent.dao.cupom.CupomSalvarAtualizar;
import com.newevent.model.Cupom;
import com.newevent.utils.DataUtil;
import com.newevent.utils.UidUtil;
import com.newevent.utils.UsuarioUtils;

import java.util.Date;

public class CriarCupom {

    public static final int CUPOM_CRIADO = 0;
    public static final int EVENTO_INVALIDO = 1;
    public static final int USUARIO_DESLOGADO = 2;
    public static final int CODIGO_INVALIDO = 3;
    public static final int PORCENTAGEM_INVALIDA = 4;
    public static final int DATA_INVALIDA = 5;
    public static final int QUANTIDADE_INVALIDA = 6;

    private UseCaseOnCompleteListener listener;
    private int requestCode;

    private CupomSalvarAtualizar cupomSalvar;

    public CriarCupom(int requestCode, UseCaseOnCompleteListener listener){
        this.listener = listener;
        this.requestCode = requestCode;
        this.cupomSalvar = new CupomSalvarAtualizar();
    }

    public void criar(String eventoUid, String donoUid,
                       String codigo, int porcetagem, Date dataValidade, int quantidade){
        if(!UidUtil.isValido(eventoUid)){
            listener.onComplete(EVENTO_INVALIDO, requestCode);
            return;
        }

        if(!UsuarioUtils.isLogado()){
            listener.onComplete(USUARIO_DESLOGADO, requestCode);
            return;
        }

        if(codigo == null || codigo.contains(" ") || codigo.length() < 1){
            listener.onComplete(CODIGO_INVALIDO, requestCode);
            return;
        }

        if(porcetagem > 100 || porcetagem < 1){
            listener.onComplete(PORCENTAGEM_INVALIDA, requestCode);
            return;
        }

        if(quantidade < 1){
            listener.onComplete(QUANTIDADE_INVALIDA, requestCode);
            return;
        }

        if(dataValidade == null || dataValidade.getTime() < DataUtil.getAtual().getTime()){
            listener.onComplete(DATA_INVALIDA, requestCode);
            return;
        }

        Cupom cupom = new Cupom(eventoUid, UsuarioUtils.getUid(), codigo, porcetagem, dataValidade, quantidade);
        cupomSalvar.put(cupom);
        listener.onComplete(CUPOM_CRIADO, requestCode);

    }

}
