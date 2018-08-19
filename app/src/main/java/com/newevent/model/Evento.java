package com.newevent.model;

import java.util.Calendar;
import java.util.Date;

public class Evento {

    private String uid;
    private String donoUid;
    private String nome;
    private String tipo;
    private Local local;
    private Date dataInicio;
    private boolean publicado;

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
        if(this.uid == null){
            this.uid = uid;
        }
    }

    public String getDonoUid() {
        return donoUid;
    }

    public void setDonoUid(String donoUid) {
        if(this.donoUid == null){
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

    private String validarNome(String nome){
        if(nome == null || nome.length() < 6){
            throw new IllegalArgumentException("Nome nulo ou menos de 6 caracteres");
        }
        return nome;
    }

    private String validarTipo(String tipo){
        if(tipo == null || tipo.length() < 5){
            throw new IllegalArgumentException("Tipo do evento nulo ou menos de 5 caracteres");
        }
        return tipo;
    }

    private Local validarLocal(Local local){
        if(local == null){
            throw new IllegalArgumentException("Local do evento nulo");
        }
        return local;
    }

    private Date validarDataInicio(Date dataInicio){
        if(dataInicio == null ||
                dataInicio.getTime() < (Calendar.getInstance().getTimeInMillis() + 43200000)){
            throw new IllegalArgumentException("Data de inicio nula ou menor que 12 horas para o inicio do evento");
        }
        return dataInicio;
    }

}
