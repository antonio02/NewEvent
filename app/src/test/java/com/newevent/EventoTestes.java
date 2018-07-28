package com.newevent;

import com.newevent.model.Evento;
import com.newevent.model.StatusEvento;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class EventoTestes {

    private Date dataValida;
    private Date dataInvalida;

    @Before
    public void setup(){
        Calendar data = Calendar.getInstance();
        long dataValida = data.getTimeInMillis() + 172800000;
        long dataInvalida = data.getTimeInMillis() - 172800000;

        this.dataValida = new Date(dataValida);
        this.dataInvalida = new Date(dataInvalida);
    }

    @Test
    public void deve_aceitar_a_criacao_de_um_novo_evento_valido(){
        try{
            Evento evento = new Evento("Nome: Evento Teste",
                    "Tipo: Palestra",
                    "Local: Teresina/Pi",
                    dataValida);
            assertEquals(StatusEvento.NOVO, evento.getStatus());
            assertTrue(true);
        } catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void deve_recusar_as_criacao_de_um_novo_evento_com_nome_invalido(){
        //Nome nulo
        try{
            Evento evento = new Evento(null,
                    "Tipo: Palestra",
                    "Local: Teresina/Pi",
                    dataValida);
            assertTrue(false);
        } catch (Exception e){
            assertTrue(true);
        }

        //Nome menor que 5 caracteres
        try{
            Evento evento = new Evento("ab",
                    "Tipo: Palestra",
                    "Local: Teresina/Pi",
                    dataValida);
            assertTrue(false);
        } catch (Exception e){
            assertTrue(true);
        }
    }

}