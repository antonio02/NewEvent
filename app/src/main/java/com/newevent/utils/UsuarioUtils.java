package com.newevent.utils;

import com.google.firebase.auth.FirebaseAuth;

public class UsuarioUtils {

    public static boolean isLogado(){
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static String getUid(){
        if(isLogado()){
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return null;
    }
}
