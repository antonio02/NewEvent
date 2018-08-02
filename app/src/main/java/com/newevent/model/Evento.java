package com.newevent.model;

import com.newevent.utils.CriarEventoValidador;

import java.util.Date;

public class Evento {

    private String uid;
    private String donoUid;
    private String nome;
    private String tipo;
    private Local local;
    private Date dataInicio;
    protected boolean publicado;

    public Evento(String nome, String tipo, Local local, Date dataInicio) {
        verificarCriacao(nome, tipo, local, dataInicio);
        this.nome = nome;
        this.tipo = tipo;
        this.local = local;
        this.dataInicio = dataInicio;
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
        validarNome(nome);
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        validarTipo(tipo);
        this.tipo = tipo;
    }

    public void setLocal(Local local) {
        validarLocal(local);
        this.local = local;
    }

    public void setDataInicio(Date dataInicio) {
        validarDataInicio(dataInicio);
        this.dataInicio = dataInicio;
    }

    private void validarNome(String nome){
        if(!CriarEventoValidador.isValidoNomeDoEvento(nome)){
            throw new IllegalArgumentException("Nome nulo ou com menos de 6 caracteres");
        }
    }

    private void validarTipo(String tipo){
        if(!CriarEventoValidador.isValidoTipoDoEvento(tipo)){
            throw new IllegalArgumentException("Tipo do evento nulo");
        }
    }

    private void validarLocal(Local local){
        if(!CriarEventoValidador.isValidoLocalDoEvento(local)){
            throw new IllegalArgumentException("Local do evento nulo");
        }
    }

    private void validarDataInicio(Date dataInicio){
        if(!CriarEventoValidador.isValidoDataInicioDoEvento(dataInicio)){
            throw new IllegalArgumentException("Data de inicio nula");
        }
    }

    private void verificarCriacao(String nome, String tipo, Local local, Date dataInicio) {
        validarNome(nome);
        validarTipo(tipo);
        validarLocal(local);
        validarDataInicio(dataInicio);
    }
}
