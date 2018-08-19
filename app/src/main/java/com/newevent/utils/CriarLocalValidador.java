
package com.newevent.utils;

import com.newevent.model.Local;

public class CriarLocalValidador {

    public static final int VALIDO = 0;
    public static final int ENDERECO_INVALIDO = 1;
    public static final int BAIRRO_INVALIDO = 2;
    public static final int CIDADE_INVALIDO = 3;
    public static final int UF_INVALIDO = 4;

    public static int validarCriarLocal(String endereco, String bairro, String cidade, String uf) {
        if (endereco == null || endereco.trim().isEmpty()) {
            return ENDERECO_INVALIDO;
        }
        if (bairro == null || bairro.trim().isEmpty()) {
            return BAIRRO_INVALIDO;
        }
        if (cidade == null || cidade.trim().isEmpty()) {
            return CIDADE_INVALIDO;
        }
        if (uf == null || uf.trim().length() != 2) {
            return UF_INVALIDO;
        }
        return VALIDO;
    }

}
