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

    @Test
    public void deve_aceitar_a_criacao_de_um_novo_evento_valido(){
        try{
            Evento evento = new Evento("Nome: Evento Teste",
                    "Tipo: Palestra",
                    localValido,
                    dataValida);
            assertEquals(StatusEvento.NOVO, evento.getStatus());
            assertTrue(true);
        } catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void deve_recusar_a_criacao_de_um_novo_evento_se_algum_parametro_for_nulo(){
        //Nome Nulo
        try{
            Evento evento = new Evento(null,
                    "Tipo: Palestra",
                    localValido,
                    dataValida);
            assertTrue(false);
        } catch (Exception e){
            assertTrue(true);
        }
        //Tipo Nulo
        try{
            Evento evento = new Evento("Nome: Evento Teste",
                    null,
                    localValido,
                    dataValida);
            assertTrue(false);
        } catch (Exception e){
            assertTrue(true);
        }
        //Local Nulo
        try{
            Evento evento = new Evento(null,
                    "Tipo: Palestra",
                    null,
                    dataValida);
            assertTrue(false);
        } catch (Exception e){
            assertTrue(true);
        }
        //Data Nula
        try{
            Evento evento = new Evento(null,
                    "Tipo: Palestra",
                    localValido,
                    null);
            assertTrue(false);
        } catch (Exception e){
            assertTrue(true);
        }
    }
}