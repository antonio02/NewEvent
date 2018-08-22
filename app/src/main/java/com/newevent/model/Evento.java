package com.newevent.model;

import com.newevent.utils.UidUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Evento {

    private String uid;
    private String donoUid;
    private Map<String, Object> atividadesUid;

    private String nome;
    private String tipo;
    private Local local;
    private Date dataInicio;
    private boolean publicado;

    private Evento(){}

    public Evento(String nome, String tipo, Local local, Date dataInicio) {
        this.nome = validarNome(nome);
        this.tipo = validarTipo(tipo);
        this.local = validarLocal(local);
        this.dataInicio = validarDataInicio(dataInicio);
        this.publicado = false;
        this.atividadesUid = new HashMap<>();
    }

    public Map<String, Object> getAtividadesUid() {
        return new HashMap<>(atividadesUid);
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Local getLocal() {
        return local;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public String getUid() {
        return uid;
    }

    public void addAtividadeUid(String uid){
        if(uid != null && !uid.isEmpty()){
            atividadesUid.put(uid, true);
        }
    }

    public void removerAtividade(String uid){
        atividadesUid.remove(uid);
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

    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }

    public void setTipo(String tipo) {
        this.tipo = validarTipo(tipo);
    }

    public void setLocal(Local local) {
        this.local = validarLocal(local);
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = validarDataInicio(dataInicio);
    }

    public static String validarNome(String nome){
        if(nome == null || nome.trim().length() < 6){
            throw new IllegalArgumentException("Nome nulo ou menos de 6 caracteres");
        }
        return nome;
    }

    public static String validarTipo(String tipo){
        if(tipo == null || tipo.trim().length() < 5){
            throw new IllegalArgumentException("Tipo do evento nulo ou menos de 5 caracteres");
        }
        return tipo;
    }

    public static Local validarLocal(Local local){
        if(local == null){
            throw new IllegalArgumentException("Local do evento nulo");
        }
        return local;
    }

    public static Date validarDataInicio(Date dataInicio){
        if(dataInicio == null){
            throw new IllegalArgumentException("Data de inicio nula");
        }
        return dataInicio;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        map.put("dono_uid", donoUid);

        map.put("nome", nome);
        map.put("tipo", tipo);
        map.put("local", local.toMap());
        map.put("data_inicio", dataInicio.getTime());
        map.put("publicado", publicado);
        map.put("atividades", atividadesUid);

        return map;
    }

    public static Evento mapToEvento(Map<String, Object> map){
        Evento evento = new Evento();

        evento.setUid((String) map.get("uid"));
        evento.setDonoUid((String) map.get("dono_uid"));

        evento.setNome((String) map.get("nome"));
        evento.setTipo((String) map.get("tipo"));
        evento.setLocal((Local) map.get("local"));
        evento.setDataInicio((Date) map.get("data_inicio"));
        evento.publicado = (boolean) map.get("publicado");
        Map<String, Object> atividades = (Map<String, Object>) map.get("atividades");
        evento.atividadesUid = atividades == null ? new HashMap<>() : atividades;

        return evento;
    }

}
