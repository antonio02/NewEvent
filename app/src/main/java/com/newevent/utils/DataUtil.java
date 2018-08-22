package com.newevent.utils;

import java.util.Date;

public class DataUtil {

    //Data atual + 6 horas
    public static Date getMinimaEvento(){
        Date data = new Date();
        data.setTime(data.getTime() + 21600000);
        return data;
    }

    public static Date getAtual(){
        return new Date();
    }
}
