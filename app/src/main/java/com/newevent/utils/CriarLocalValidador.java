
package com.newevent.utils;

import com.newevent.model.Local;

public class CriarLocalValidador {

    public static final int VALIDO = 0;
    public static final int ENDERECO_INVALIDO = 1;
    public static final int BAIRRO_INVALIDO = 2;
    public static final int CIDADE_INVALIDO = 3;
    public static final int UF_INVALIDO = 4;

    public static int validarCriarLocal(String endereco, String bairro, String cidade, String uf) {

        try {
            Local.validarEndereco(endereco);
        } catch (IllegalArgumentException e){
            return ENDERECO_INVALIDO;
        }

        try {
            Local.validarBairro(bairro);
        } catch (IllegalArgumentException e){
            return BAIRRO_INVALIDO;
        }

        try {
            Local.validarCidade(cidade);
        } catch (IllegalArgumentException e){
            return CIDADE_INVALIDO;
        }

        try {
            Local.validarUF(uf);
        } catch (IllegalArgumentException e){
            return UF_INVALIDO;
        }

        return VALIDO;
    }

}
