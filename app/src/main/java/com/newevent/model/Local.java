package com.newevent.model;

public class Local {

    private String cidade;
    private String estado;
    private String logradouro;
    private String numero;
    private String complemento;

    public Local(String cidade, String estado, String logradouro, String numero) {
        this.cidade = cidade;
        this.estado = estado;
        this.logradouro = logradouro;
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
