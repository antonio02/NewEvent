package com.newevent;

import com.newevent.model.Local;

import org.junit.Test;

import static org.junit.Assert.*;

public class CriarLocalTestes {

    @Test
    public void deve_recusar_criacao_do_local_com_endereco_nulo_ou_vazio(){
        try {
            new Local(null, "centro", "teresina", "PI");
            assertTrue("Aceitou valor nulo", false);
            new Local("", "centro", "teresina", "PI");
            assertTrue("Aceitou valor vazio",false);
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_criacao_do_local_com_bairro_nulo_ou_vazio(){
        try {
            new Local("Rua 11", null, "teresina", "PI");
            assertTrue("Aceitou valor nulo", false);
            new Local("Rua 11", "", "teresina", "PI");
            assertTrue("Aceitou valor vazio",false);
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_criacao_do_local_com_cidade_nula_ou_vazia(){
        try {
            new Local("Rua 11", "centro", null, "PI");
            assertTrue("Aceitou valor nulo", false);
            new Local("Rua 11", "centro", "", "PI");
            assertTrue("Aceitou valor vazio",false);
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deve_recusar_criacao_do_local_com_UF_nula_ou_que_nao_tenha_apenas_dois_caracteres(){
        try {
            new Local("Rua 11", "centro", "teresina", null);
            assertTrue("Aceitou valor nulo", false);
            new Local("Rua 11", "centro", "teresina", "p");
            assertTrue("Aceitou valor diferente de 2 caracteres",false);
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }
}
