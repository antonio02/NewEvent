package com.newevent.model;

import java.util.Date;

public class Evento {

    private String nome;
    private String tipo;
    private String local;
    private Date dataInicio;
    private StatusEvento status;

    public Evento(String nome, String tipo, String local, Date dataInicio) {
        verificarCriacao(nome, tipo, local, dataInicio);
        this.nome = nome;
        this.tipo = tipo;
        this.local = local;
        this.dataInicio = dataInicio;
        this.status = StatusEvento.NOVO;
    }

    private void verificarCriacao(String nome, String tipo, String local, Date dataInicio) {
        if(nome == null || nome.length() < 5){
            throw new IllegalArgumentException("Nome invalido (nulo ou menor que 5 caracteres");
        }
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getLocal() {
        return local;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public StatusEvento getStatus() {
        return status;
    }
}
