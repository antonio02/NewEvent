package com.newevent.model;

import com.newevent.utils.UidUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Evento {

    private String uid;
    private String donoUid;

    private String nome;
    private String tipo;
    private Local local;
    private Date dataInicio;
    private Date dataTermino;
    private boolean publicado;
    private boolean inscricoesAbertas;

    private Evento(){}

    public Evento(String nome, String tipo, Local local, Date dataInicio) {
        this.nome = validarNome(nome);
        this.tipo = validarTipo(tipo);
        this.local = validarLocal(local);
        this.dataInicio = validarData(dataInicio);
        this.publicado = false;
        this.inscricoesAbertas = false;
    }

    public boolean publicar(){
        this.publicado = true;
        this.inscricoesAbertas = true;
        return true;
    }

    public boolean isInscricoesAbertas() {
        return inscricoesAbertas;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        if(dataTermino != null && dataTermino.getTime() > this.dataInicio.getTime()){
            this.dataTermino = dataTermino;
        }
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
        if(this.uid == null && UidUtil.isValido(uid)){
            this.uid = uid;
        }
    }

    public String getDonoUid() {
        return donoUid;
    }

    public void setDonoUid(String donoUid) {
        if(this.donoUid == null && UidUtil.isValido(donoUid)){
            this.donoUid = donoUid;
        }
    }

    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }

    public void setTipo(String tipo) {
        this.tipo = validarTipo(tipo);
    }

    public void setLocal(Local local) {
        this.local = validarLocal(local);
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = validarData(dataInicio);
    }

    public static String validarNome(String nome){
        if(nome == null || nome.trim().length() < 6){
            throw new IllegalArgumentException("Nome nulo ou menos de 6 caracteres");
        }
        return nome;
    }

    public static String validarTipo(String tipo){
        if(tipo == null || tipo.trim().length() < 5){
            throw new IllegalArgumentException("Tipo do evento nulo ou menos de 5 caracteres");
        }
        return tipo;
    }

    public static Local validarLocal(Local local){
        if(local == null){
            throw new IllegalArgumentException("Local do evento nulo");
        }
        return local;
    }

    public static Date validarData(Date data){
        if(data == null){
            throw new IllegalArgumentException("Data nula");
        }
        return data;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        map.put("dono_uid", donoUid);

        map.put("nome", nome);
        map.put("tipo", tipo);
        map.put("local", local.toMap());
        map.put("data_inicio", dataInicio.getTime());
        map.put("data_termino", dataTermino != null ? dataTermino.getTime() : null);
        map.put("publicado", publicado);
        map.put("inscricoes_abertas", inscricoesAbertas);

        return map;
    }

    public static Evento mapToEvento(Map<String, Object> map){
        Evento evento = new Evento();

        evento.setUid((String) map.get("uid"));
        evento.setDonoUid((String) map.get("dono_uid"));

        evento.setNome((String) map.get("nome"));
        evento.setTipo((String) map.get("tipo"));
        evento.setLocal((Local) map.get("local"));
        evento.setDataInicio((Date) map.get("data_inicio"));
        evento.setDataTermino((Date) map.get("data_termino"));
        evento.publicado = (boolean) map.get("publicado");
        evento.inscricoesAbertas = (boolean) map.get("inscricoes_abertas");

        return evento;
    }

}
