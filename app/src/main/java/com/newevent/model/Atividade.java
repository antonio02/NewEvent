package com.newevent.model;

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
    private boolean inscricoesAbertas;

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
        this.inscricoesAbertas = false;
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

    public boolean isInscricoesAbertas() {
        return inscricoesAbertas;
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

    public boolean getInscricoesAbertas() {
        return inscricoesAbertas;
    }

    public void setUid(String uid) {
        if(this.uid == null && uid != null && !uid.trim().isEmpty()){
            this.uid = uid.trim();
        }
    }

    public void setDonoUid(String donoUid) {
        if(this.donoUid == null && donoUid != null && !donoUid.trim().isEmpty()){
            this.donoUid = donoUid.trim();
        }
    }

    public void setEventoUid(String eventoUid) {
        if(this.eventoUid == null && eventoUid != null && !eventoUid.trim().isEmpty()){
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
        this.valor = validarValor(valor);
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

    public void setIncricoesAbertas(boolean incricoesAbertas) {
        this.inscricoesAbertas = incricoesAbertas;
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
        if(valor == null || valor < 0){
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
        map.put("evento_uid", (new HashMap<String, Object>()).put(eventoUid, true));

        map.put("nome", nome);
        map.put("tipo", tipo);
        map.put("valor", valor);
        map.put("data_inicio", dataInicio.getTime());
        map.put("data_termino", dataTermino.getTime());
        map.put("max_inscricoes", maxInscricoes);
        map.put("inscricoes_abertas", inscricoesAbertas);

        return map;
    }

    public static Atividade mapToAtividade(Map<String, Object> map){
        Atividade atividade = new Atividade();

        atividade.setUid((String) map.get("uid"));
        atividade.setEventoUid((String) map.get("evento_uid"));

        atividade.setNome((String) map.get("nome"));
        atividade.setTipo((String) map.get("tipo"));
        atividade.setValor((double) map.get("valor"));
        atividade.setDataInicio((Date) map.get("data_inicio"));
        atividade.setDataTermino((Date) map.get("data_termino"));
        atividade.setMaxInscricoes((int) map.get("max_inscricoes"));
        atividade.setIncricoesAbertas((boolean) map.get("inscricoes_abertas"));

        return atividade;
    }
}
