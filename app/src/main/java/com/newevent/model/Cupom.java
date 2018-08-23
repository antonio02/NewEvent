package com.newevent.model;

import com.newevent.utils.UidUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cupom {

    private String uid;
    private String eventoUid;
    private String donoUid;

    private String codigo;
    private int porcentagem;
    private Date dataValidade;
    private int quantidade;

    private Cupom(){}

    public Cupom(String eventoUid, String donoUid, String codigo, int porcentagem, Date dataValidade, int quantidade) {

        if(eventoUid == null || !UidUtil.isValido(eventoUid)){
            throw new IllegalArgumentException("Evento uid nulo ou invalido no construtor de cupom");
        }

        if(donoUid == null || !UidUtil.isValido(donoUid)){
            throw new IllegalArgumentException("Dono uid nulo ou invalido no construtor de cupom");
        }

        if(codigo == null || codigo.contains(" ") || codigo.length() < 1){
            throw new IllegalArgumentException("Codigo nulo, com espaco ou invalido no construtor de cupom");
        }

        if(porcentagem < 1 || porcentagem > 100){
            throw new IllegalArgumentException("Porcentagem invalida no construtor de cupom");
        }

        if(dataValidade == null){
            throw new IllegalArgumentException("Validade nula no construtor de cupom");
        }

        if(quantidade < 1){
            throw new IllegalArgumentException("Quantidade invalida no construtor de cupom");
        }

        this.eventoUid = eventoUid;
        this.donoUid = donoUid;
        this.codigo = codigo.toUpperCase();
        this.porcentagem = porcentagem;
        this.dataValidade = dataValidade;
        this.quantidade = quantidade;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        if(this.uid == null && UidUtil.isValido(uid)) {
            this.uid = uid;
        }
    }

    public String getEventoUid() {
        return eventoUid;
    }

    public String getDonoUid() {
        return donoUid;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getPorcentagem() {
        return porcentagem;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        map.put("dono_uid", donoUid);
        map.put("evento_uid", eventoUid);

        map.put("codigo", codigo);
        map.put("porcentagem", porcentagem);
        map.put("data_validade", dataValidade.getTime());
        map.put("quantidade", quantidade);

        return map;
    }

    public static Cupom mapToCupom(Map<String, Object> map){
        Cupom cupom = new Cupom();

        cupom.uid = (String) map.get("uid");
        cupom.donoUid = (String) map.get("dono_uid");
        cupom.eventoUid = (String) map.get("evento_uid");

        cupom.codigo = (String) map.get("codigo");
        cupom.porcentagem = ((Long) map.get("porcentagem")).intValue();
        cupom.dataValidade = (Date) map.get("data_validade");
        cupom.quantidade = ((Long) map.get("quantidade")).intValue();

        return cupom;
    }
}
