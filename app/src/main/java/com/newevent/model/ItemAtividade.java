package com.newevent.model;

import android.accessibilityservice.GestureDescription;

import com.newevent.utils.UidUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ItemAtividade {

    private String uid;
    private String atividadeUid;
    private String donoUid;
    private String inscricaoUid;

    private double valor;
    private Date valorEm;

    private ItemAtividade(){}

    public ItemAtividade(String atividadeUid, String donoUid, String inscricaoUid, double valor, Date valorEm) {

        if(atividadeUid == null || !UidUtil.isValido(atividadeUid)){
            throw new IllegalArgumentException("AtividadeUid nulo no construtor de ItemAtividade");
        }

        if(donoUid == null || !UidUtil.isValido(donoUid)){
            throw new IllegalArgumentException("DonoUid nulo no construtor de ItemAtividade");
        }

        if(inscricaoUid == null || !UidUtil.isValido(inscricaoUid)){
            throw new IllegalArgumentException("InscricaoUid nulo no construtor de ItemAtividade");
        }

        if(valor < 0){
            throw new IllegalArgumentException("Valor negativo no construtor de ItemAtividade");
        }

        if(valorEm == null){
            throw new IllegalArgumentException("Data do valor nulo no construtor de ItemAtividade");
        }

        this.atividadeUid = atividadeUid;
        this.donoUid = donoUid;
        this.inscricaoUid = inscricaoUid;
        this.valor = valor;
        this.valorEm = valorEm;
    }

    public String getUid() {
        return uid;
    }

    public String getAtividadeUid() {
        return atividadeUid;
    }

    public String getDonoUid() {
        return donoUid;
    }

    public String getInscricaoUid() {
        return inscricaoUid;
    }

    public double getValor() {
        return valor;
    }

    public Date getValorEm() {
        return valorEm;
    }

    public void setUid(String uid) {
        if(this.uid == null && UidUtil.isValido(uid)){
            this.uid = uid;
        }
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        map.put("dono_uid", donoUid);
        map.put("atividade_uid", atividadeUid);
        map.put("inscricao_uid", inscricaoUid);

        map.put("valor", valor);
        map.put("valor_em", valorEm.getTime());

        return map;
    }

    public ItemAtividade mapToItemAtividade(Map<String, Object> map){
        ItemAtividade item = new ItemAtividade();

        item.uid = (String) map.get("uid");
        item.atividadeUid = (String) map.get("atividade_uid");
        item.inscricaoUid = (String) map.get("inscricao_uid");

        item.valor = (Double) map.get("valor");
        item.valorEm = (Date) map.get("valor_em");

        return item;
    }

}
