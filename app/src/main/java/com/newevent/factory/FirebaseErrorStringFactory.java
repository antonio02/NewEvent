package com.newevent.factory;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class FirebaseErrorStringFactory {

    public static String getErro(Exception e){
        if(e instanceof FirebaseAuthInvalidUserException){
            return "Email não cadastrado";
        }

        if(e instanceof FirebaseAuthInvalidCredentialsException){
            return "Senha invalida";
        }
        return e.toString();
    }
}
