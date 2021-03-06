package com.newevent.model;

import com.newevent.utils.UidUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Atividade {

    private String uid;
    private String donoUid;
    private String eventoUid;

    private String nome;
    private String tipo;
    private Double valor;
    private Date dataInicio;
    private Date dataTermino;
    private int maxInscricoes;
    private int incricoesRealizadas;

    private Atividade(){};

    public Atividade(String eventoUid, String nome, String tipo, Double valor,
                     Date dataInicio, Date dataTermino, int maxIncricoes) {

        setEventoUid(eventoUid);
        setNome(nome);
        setTipo(tipo);
        setValor(valor);
        setDataInicio(dataInicio);
        setDataTermino(dataTermino);
        setMaxInscricoes(maxIncricoes);
    }

    public int getIncricoesRealizadas() {
        return incricoesRealizadas;
    }

    public boolean realizarIncricao(){
        if(incricoesRealizadas < maxInscricoes){
            incricoesRealizadas++;
            return true;
        }
        return false;
    }

    public String getUid() {
        return uid;
    }

    public String getDonoUid() {
        return donoUid;
    }

    public String getEventoUid() {
        return eventoUid;
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

    public int getMaxInscricoes() {
        return maxInscricoes;
    }

    public void setUid(String uid) {
        if(this.uid == null && UidUtil.isValido(uid)){
            this.uid = uid.trim();
        }
    }

    public void setDonoUid(String donoUid) {
        if(this.donoUid == null && UidUtil.isValido(donoUid)){
            this.donoUid = donoUid.trim();
        }
    }

    public void setEventoUid(String eventoUid) {
        if(this.eventoUid == null && UidUtil.isValido(eventoUid)){
            this.eventoUid = eventoUid.trim();
        }
    }

    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }

    public void setTipo(String tipo) {
        this.tipo = validarTipo(tipo);
    }

    public void setValor(Double valor) {
        this.valor = validarValor(valor + 0.0);
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = validarDataInicio(dataInicio);
    }

    public void setDataTermino(Date dataTermino) {
        if(dataTermino.getTime() < dataInicio.getTime()){
            throw new IllegalArgumentException("Data de termino menor que a de inicio");
        }
        this.dataTermino = validarDataTermino(dataTermino);
    }

    public void setMaxInscricoes(int maxInscricoes) {
        this.maxInscricoes = validarMaxInscricoes(maxInscricoes);
    }

    public static String validarNome(String nome){
        if(nome == null || nome.trim().length() < 4){
            throw new IllegalArgumentException("Nome nulo ou menor que 4 caracteres");
        }
        return nome;
    }

    public static String validarTipo(String tipo){
        if(tipo == null || tipo.trim().length() < 4){
            throw new IllegalArgumentException("Nome nulo ou menor que 4 caracteres");
        }
        return tipo;
    }

    public static double validarValor(Double valor){
        if(valor < 0){
            throw new IllegalArgumentException("Valor nulo ou menor que 0");
        }
        return valor;
    }

    public static Date validarDataInicio(Date data){
        if(data == null){
            throw new IllegalArgumentException("Data de Inicio nula");
        }
        return data;
    }

    public static Date validarDataTermino(Date data){
        if(data == null){
            throw new IllegalArgumentException("Data de Termino nula");
        }
        return data;
    }

    public static Evento validarEvento(Evento evento){
        if(evento == null){
            throw new IllegalArgumentException("Evento nulo");
        }
        return evento;
    }

    public static int validarMaxInscricoes(int maxInscricoes){
        if(maxInscricoes < 1){
            throw new IllegalArgumentException("Numero de inscrições menor que 1");
        }
        return maxInscricoes;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        map.put("dono_uid", donoUid);
        map.put("evento_uid", eventoUid);

        map.put("nome", nome);
        map.put("tipo", tipo);
        map.put("valor", valor);
        map.put("data_inicio", dataInicio.getTime());
        map.put("data_termino", dataTermino.getTime());
        map.put("max_inscricoes", maxInscricoes);
        map.put("inscricoes_realizadas", incricoesRealizadas);

        return map;
    }

    public static Atividade mapToAtividade(Map<String, Object> map){
        Atividade atividade = new Atividade();

        atividade.setUid((String) map.get("uid"));
        atividade.setDonoUid((String) map.get("dono_uid"));
        atividade.setEventoUid((String) map.get("evento_uid"));

        atividade.setNome((String) map.get("nome"));
        atividade.setTipo((String) map.get("tipo"));
        atividade.setValor(map.get("valor") instanceof Double ?
                (Double) map.get("valor") : ((Long) map.get("valor")).doubleValue());
        atividade.setDataInicio((Date) map.get("data_inicio"));
        atividade.setDataTermino((Date) map.get("data_termino"));
        atividade.setMaxInscricoes(((Long) map.get("max_inscricoes")).intValue());
        atividade.incricoesRealizadas = ((Long) map.get("inscricoes_realizadas")).intValue();

        return atividade;
    }
}
