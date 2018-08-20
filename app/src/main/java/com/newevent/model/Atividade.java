package com.newevent.model;

import java.util.Date;

public class Atividade {

    private String uid;
    private String donoUid;
    private Evento evento;

    private String nome;
    private String tipo;
    private Double valor;
    private Date dataInicio;
    private Date dataTermino;
    private int maxIncricoes;
    private int incricoesRealizadas;
    private boolean incricoesAbertas;

    public Atividade(String nome, String tipo, Double valor, Date dataInicio, Date dataTermino, int maxIncricoes) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.maxIncricoes = maxIncricoes;
        this.incricoesRealizadas = 0;
        this.incricoesAbertas = false;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getValor() {
        return valor;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public int getMaxIncricoes() {
        return maxIncricoes;
    }

    public int getIncricoesRealizadas() {
        return incricoesRealizadas;
    }

    public boolean getIncricoesAbertas() {
        return incricoesAbertas;
    }
}
