package com.newevent.model;

import java.util.Date;

public class Evento {

    private String nome;
    private String tipo;
    private Local local;
    private Date dataInicio;
    private StatusEvento status;

    public Evento(String nome, String tipo, Local local, Date dataInicio) {
        verificarCriacao(nome, tipo, local, dataInicio);
        this.nome = nome;
        this.tipo = tipo;
        this.local = local;
        this.dataInicio = dataInicio;
        this.status = StatusEvento.NOVO;
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

    public StatusEvento getStatus() {
        return status;
    }

    private void verificarCriacao(String nome, String tipo, Local local, Date dataInicio) {
        if(nome == null){
            throw new IllegalArgumentException("Nome nulo");
        }

        if(tipo == null){
            throw new IllegalArgumentException("Tipo do evento nulo");
        }

        if(local == null){
            throw new IllegalArgumentException("Local do evento nulo");
        }

        if(dataInicio == null){
            throw new IllegalArgumentException("Data de inicio nula");
        }
    }
}
