package com.newevent.model;

import com.newevent.utils.UidUtil;

import java.util.HashMap;
import java.util.Map;

public class Inscricao {

    private String uid;
    private String donoUid;
    private String eventoUid;
    private String cupomUid;

    private double valorTotal;
    private boolean paga;

    private Inscricao(){}

    public Inscricao(String donoUid, String eventoUid) {
        if(donoUid == null){
            throw new IllegalArgumentException("Dono uid null em Inscricao");
        }

        if(eventoUid == null){
            throw new IllegalArgumentException("Evento uid null em Inscricao");
        }

        setDonoUid(donoUid);
        setEventoUid(eventoUid);
        this.valorTotal = 0;
        this.paga = false;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        if(this.uid == null && UidUtil.isValido(uid)){
            this.uid = uid;
        }
    }

    public String getDonoUid() {
        return donoUid;
    }

    public void setDonoUid(String donoUid) {
        if(this.donoUid == null && UidUtil.isValido(donoUid)){
            this.donoUid = donoUid;
        }
    }

    public String getEventoUid() {
        return eventoUid;
    }

    public void setEventoUid(String eventoUid) {
        if(this.eventoUid == null && UidUtil.isValido(eventoUid)){
            this.eventoUid = eventoUid;
        }
    }

    public String getCupomUid() {
        return cupomUid;
    }

    public void setCupomUid(String cupomUid) {
        if(this.cupomUid == null && UidUtil.isValido(cupomUid)){
            this.cupomUid = cupomUid;
        }
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal){
        if(valorTotal > 0){
            this.valorTotal = valorTotal;
        }
    }

    public boolean isPaga() {
        return paga;
    }

    public void pagar() {
        this.paga = true;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        map.put("dono_uid", donoUid);
        map.put("evento_uid", eventoUid);
        map.put("cupom_uid", cupomUid);

        map.put("valor_total", valorTotal);
        map.put("paga", paga);

        return map;
    }

    public static Inscricao mapToInscricao(Map<String, Object> map){

        Inscricao inscricao = new Inscricao();

        inscricao.setUid((String) map.get("uid"));
        inscricao.setDonoUid((String) map.get("dono_uid"));
        inscricao.setEventoUid((String) map.get("evento_uid"));

        inscricao.setValorTotal((Double) map.get("valor_total"));
        if ((Boolean) map.get("paga")) {
            inscricao.pagar();
        }

        return inscricao;
    }
}
