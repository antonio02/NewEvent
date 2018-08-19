package com.newevent.model;

import java.util.HashMap;
import java.util.Map;

public class Local {

    private String endereco;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

    public Local(String endereco, String bairro, String cidade, String uf) {
        this.endereco = validarEndereco(endereco);
        this.bairro = validarBairro(bairro);
        this.cidade = validarCidade(cidade);
        this.uf = validarUF(uf);
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
        if(complemento.trim().isEmpty()){
            this.complemento = null;
            return;
        }
        this.complemento = complemento;
    }

    private String validarEndereco(String endereco){
        if(endereco == null || endereco.trim().isEmpty()){
            throw new IllegalArgumentException("Endereço vazio");
        }
        return endereco;
    }

    private String validarBairro(String bairro){
        if(bairro == null || bairro.trim().isEmpty()){
            throw new IllegalArgumentException("Bairro vazio");
        }
        return bairro;
    }

    private String validarCidade(String cidade){
        if(cidade == null || cidade.trim().isEmpty()){
            throw new IllegalArgumentException("Cidade vazia");
        }
        return cidade;
    }

    private String validarUF(String uf){
        if(uf == null || uf.trim().contains(" ") || uf.trim().length() != 2){
            throw new IllegalArgumentException("UF vazia ou invalido");
        }
        return uf;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("endereco", endereco);
        map.put("bairro", bairro);
        map.put("cidade", cidade);
        map.put("uf", uf);
        map.put("complemento", complemento);
        return map;
    }
}
