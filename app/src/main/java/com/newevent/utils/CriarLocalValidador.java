
package com.newevent.utils;

import com.newevent.model.Local;

public class CriarLocalValidador {

    public static final int VALIDO = 0;
    public static final int ENDERECO_INVALIDO = 1;
    public static final int BAIRRO_INVALIDO = 2;
    public static final int CIDADE_INVALIDO = 3;
    public static final int UF_INVALIDO = 4;

    public static int validarLocal(Local local) {
        if (!isValidoEndereco(local.getEndereco())) {
            return ENDERECO_INVALIDO;
        }
        if (!isValidoBairro(local.getBairro())) {
            return BAIRRO_INVALIDO;
        }
        if (!isValidoCidade(local.getCidade())) {
            return CIDADE_INVALIDO;
        }
        if (!isValidoUf(local.getUf())) {
            return UF_INVALIDO;
        }
        return VALIDO;
    }

    public static boolean isValidoEndereco(String endereco) {
        return endereco != null && !endereco.trim().isEmpty();
    }

    public static boolean isValidoBairro(String bairro) {
        return bairro != null && !bairro.trim().isEmpty();
    }

    public static boolean isValidoCidade(String cidade) {
        return cidade != null && !cidade.trim().isEmpty();
    }

    public static boolean isValidoUf(String uf) {
        return uf != null && uf.trim().length() == 2;
    }

    public static boolean isValidoComplemento(String complemento) {
        return complemento != null && !complemento.trim().isEmpty();
    }

}
