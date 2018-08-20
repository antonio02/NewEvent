package com.newevent.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Evento {

    private String uid;
    private String donoUid;
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

    public void setUid(String uid) {
        if(this.uid == null && uid != null){
            this.uid = uid;
        }
    }

    public String getDonoUid() {
        return donoUid;
    }

    public void setDonoUid(String donoUid) {
        if(this.donoUid == null && donoUid != null){
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
        if(dataInicio == null ||
                dataInicio.getTime() < (Calendar.getInstance().getTimeInMillis() + 43200000)){
            throw new IllegalArgumentException("Data de inicio nula ou menor que 12 horas para o inicio do evento");
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

        return evento;
    }

}
