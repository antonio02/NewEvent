package com.newevent.model;

public class Local {

    private String endereco;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

    public Local(String endereco, String bairro, String cidade, String uf) {
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
