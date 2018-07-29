package com.newevent.utils;

import android.util.Patterns;

public class CadastroContaValidador {

    public static final int VALIDO = 0;
    public static final int EMAIL_INVALIDO = 1;
    public static final int SENHA_COM_ESPACO = 2;
    public static final int SENHA_CURTA = 3;
    public static final int SENHAS_NAO_SAO_IGUAIS = 4;

    private CadastroContaValidador(){}

    public int validar(String email, String senha, String senhaConfirmacao){
        if(email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return EMAIL_INVALIDO;
        }

        if(senha.length() < 6){
            return SENHA_CURTA;
        }

        if(senha.contains(" ")){
            return SENHA_COM_ESPACO;
        }

        if(!senha.equals(senhaConfirmacao)){
            return SENHAS_NAO_SAO_IGUAIS;
        }

        return VALIDO;
    }
}
