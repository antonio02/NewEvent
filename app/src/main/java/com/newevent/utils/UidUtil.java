package com.newevent.utils;

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
}
