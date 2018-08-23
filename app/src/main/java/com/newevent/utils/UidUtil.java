package com.newevent.utils;

import com.newevent.model.Evento;

public class UidUtil {

    public static boolean isValido(String uid){
        if(uid == null){
            return false;
        }

        if(uid.contains(" ")){
            return false;
        }

        return !uid.isEmpty();
    }

    public static boolean eventoTemUid(Evento evento){
        return evento != null && isValido(evento.getUid());
    }
}
