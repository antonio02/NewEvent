package com.newevent;

import com.newevent.model.Evento;
import com.newevent.model.Local;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class NovoEventoTestes {

    private Date dataValida;
    private Local localValido;

    @Before
    public void setup(){
        Calendar data = Calendar.getInstance();
        long dataValida = data.getTimeInMillis() + 172800000;

        this.dataValida = new Date(dataValida);
        this.localValido = new Local("Teresina", "Piaui",
                "Avenida Miguel Rosa", "5051");
    }

    private Evento criarEventoValido(){
        return new Evento("Nome: Teste",
                "Tipo: Palestra", localValido, dataValida);
    }

    private void testarCriacao(String nome, String tipo, Local local, Date dataInicio){
        new Evento(nome, tipo, local, dataInicio);
    }

    @Test
    public void deve_aceitar_a_criacao_de_um_novo_evento_valido(){
        try {
            Evento evento = criarEventoValido();
            assertTrue(!evento.isPublicado());
        }catch (IllegalArgumentException e){
            assertTrue(false);
        }
    }

    @Test
    public void deve_recusar_a_criacao_caso_o_nome_seja_nulo_ou_menor_que_seis_caracteres(){
        try {
            testarCriacao(null, "Tipo: Palestra", localValido, dataValida);
            assertTrue("Aceitou valor nulo", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }

        try {
            testarCriacao("Test", "Tipo: Palestra", localValido, dataValida);
            assertTrue("Aceitou nome menor que 6 caracteres", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_o_setNome_caso_o_nome_seja_nulo_ou_menor_que_seis_caracteres(){
        try {
            Evento evento = criarEventoValido();
            evento.setNome(null);
            assertTrue("Aceitou valor nulo", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }

        try {
            Evento evento = criarEventoValido();
            evento.setNome("Abc");
            assertTrue("Aceitou nome menor que 6 caracteres", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_a_criacao_caso_o_tipo_seja_nulo_ou_menor_que_cinco_caracteres(){
        try {
            testarCriacao("Nome: Teste", null, localValido, dataValida);
            assertTrue("Aceitou valor nulo", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }

        try {
            testarCriacao("Nome: Teste", "Tipo", localValido, dataValida);
            assertTrue("Aceitou valor menor que 5 caracteres", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void deve_recusar_o_setTipo_caso_o_tipo_seja_nulo_ou_menor_que_cinco_caracteres(){
        try {
            Evento evento = criarEventoValido();
            evento.setTipo(null);
            assertTrue("Aceitou valor nulo", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }

        try {
            Evento evento = criarEventoValido();
            evento.setTipo("Abc");
            assertTrue("Aceitou nome menor que 5 caracteres", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_a_criacao_caso_o_local_seja_nulo(){
        try {
            testarCriacao("Nome: Teste", "Tipo: Palestra", null, dataValida);
            assertTrue("Aceitou valor nulo", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_o_setLocal_caso_o_local_seja_nulo(){
        try {
            Evento evento = criarEventoValido();
            evento.setLocal(null);
            assertTrue("Aceitou valor nulo", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_a_criacao_caso_a_data_de_inicio_seja_nula(){
        try {
            testarCriacao("Nome: Teste", "Tipo: Palestra", localValido, null);
            assertTrue("Aceitou valor nulo", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_o_setDataInicio_caso_a_data_seja_nula(){
        try {
            Evento evento = criarEventoValido();
            evento.setDataInicio(null);
            assertTrue("Aceitou valor nulo", false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_a_criacao_e_o_setDataInicio_caso_a_data_de_inicio_seja_menos_de_doze_horas_para_o_inicio_do_evento(){
        try {
            testarCriacao("Nome: Teste", "Tipo: Palestra",
                    localValido, new Date(Calendar.getInstance().getTimeInMillis()));
            assertTrue(
                    "Aceitou uma data com menos de 12 horas para o inicio do evento",
                    false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }

        try {
            Evento evento = criarEventoValido();
            evento.setDataInicio(new Date(Calendar.getInstance().getTimeInMillis()));
            assertTrue(
                    "Aceitou uma data com menos de 12 horas para o inicio do evento",
                    false);
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void deve_nao_alterar_o_uid_caso_o_setUid_seja_feito_mais_de_uma_vez(){
        Evento evento = criarEventoValido();
        evento.setUid("uid1");
        evento.setUid("uid2");
        assertEquals("uid1", evento.getUid());
    }

    @Test
    public void deve_nao_alterar_o_donoUid_caso_o_setDonoUid_seja_feito_mais_de_uma_vez(){
        Evento evento = criarEventoValido();
        evento.setDonoUid("uid1");
        evento.setDonoUid("uid2");
        assertEquals("uid1", evento.getDonoUid());
    }
}