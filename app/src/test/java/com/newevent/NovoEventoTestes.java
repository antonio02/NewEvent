package com.newevent;

import com.newevent.model.Evento;
import com.newevent.model.Local;
import com.newevent.model.StatusEvento;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class NovoEventoTestes {

    private Date dataValida;
    private Date dataInvalida;
    private Local localValido;

    @Before
    public void setup(){
        Calendar data = Calendar.getInstance();
        long dataValida = data.getTimeInMillis() + 172800000;
        long dataInvalida = data.getTimeInMillis() - 172800000;

        this.dataValida = new Date(dataValida);
        this.dataInvalida = new Date(dataInvalida);
        this.localValido = new Local("Teresina", "Piaui",
                "Avenida Miguel Rosa", "5051");
    }

    private Evento criarEventoValido(){
        return new Evento("Nome: Teste",
                "Tipo: Palestra", localValido, dataValida);
    }

    private Evento testarCriacao(String nome, String tipo, Local local, Date dataInicio){
        return new Evento(nome, tipo, local, dataInicio);
    }

    @Test
    public void deve_aceitar_a_criacao_de_um_novo_evento_valido(){
        try {
            Evento evento = criarEventoValido();
            assertEquals(StatusEvento.NOVO, evento.getStatus());
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
}