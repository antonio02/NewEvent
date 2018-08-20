package com.newevent.model;

import java.util.HashMap;
import java.util.Map;

public class Local {

    private String endereco;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

    private Local(){};

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

    public void setEndereco(String endereco) {
        this.endereco = validarEndereco(endereco);
    }

    public void setBairro(String bairro) {
        this.bairro = validarBairro(bairro);
    }

    public void setCidade(String cidade) {
        this.cidade = validarCidade(cidade);
    }

    public void setUf(String uf) {
        this.uf = validarUF(uf).toUpperCase();
    }

    public void setComplemento(String complemento) {
        if(complemento == null || complemento.trim().isEmpty()){
            this.complemento = null;
            return;
        }
        this.complemento = complemento;
    }

    public static String validarEndereco(String endereco){
        if(endereco == null || endereco.trim().isEmpty()){
            throw new IllegalArgumentException("Endereço vazio");
        }
        return endereco;
    }

    public static String validarBairro(String bairro){
        if(bairro == null || bairro.trim().isEmpty()){
            throw new IllegalArgumentException("Bairro vazio");
        }
        return bairro;
    }

    public static String validarCidade(String cidade){
        if(cidade == null || cidade.trim().isEmpty()){
            throw new IllegalArgumentException("Cidade vazia");
        }
        return cidade;
    }

    public static String validarUF(String uf){
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

    public static Local mapToLocal(Map<String, Object> map){
        Local local = new Local();

        local.endereco = validarEndereco((String) map.get("endereco"));
        local.bairro = validarBairro((String) map.get("bairro"));
        local.cidade = validarCidade((String) map.get("cidade"));
        local.uf = validarCidade((String) map.get("uf"));
        local.complemento = (String) map.get("complemento");

        return local;
    }
}
