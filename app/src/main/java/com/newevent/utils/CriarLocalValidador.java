
package com.newevent.utils;

public class CriarLocalValidador {

    public static final int VALIDO = 0;
    public static final int ENDERECO_INVALIDO = 1;
    public static final int BAIRRO_INVALIDO = 2;
    public static final int CIDADE_INVALIDO = 3;
    public static final int UF_INVALIDO = 4;

    public static int validarLocal(String endereco, String bairro,
                                   String cidade, String uf) {
        if (!isValidoEndereco(endereco)) {
            return ENDERECO_INVALIDO;
        }
        if (!isvalidoBairro(bairro)) {
            return BAIRRO_INVALIDO;
        }
        if (!isvalidoCidade(cidade)) {
            return CIDADE_INVALIDO;
        }
        if (!isvalidoUf(uf)) {
            return UF_INVALIDO;
        }
        return VALIDO;
    }

    public static boolean isValidoEndereco(String endereco) {
        return endereco != null && !endereco.trim().isEmpty();
    }

    public static boolean isvalidoBairro(String bairro) {
        return bairro != null && !bairro.trim().isEmpty();
    }

    public static boolean isvalidoCidade(String cidade) {
        return cidade != null && !cidade.trim().isEmpty();
    }

    public static boolean isvalidoUf(String uf) {
        return uf != null && uf.trim().length() == 2;
    }

}
