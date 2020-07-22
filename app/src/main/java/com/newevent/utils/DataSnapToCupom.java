package com.newevent.utils;

import com.google.firebase.database.DataSnapshot;
import com.newevent.model.Cupom;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataSnapToCupom {

    public static Cupom get(DataSnapshot d){
        Map<String, Object> map = new HashMap<>();

        map.put("uid", d.getKey());
        map.put("dono_uid", d.child("dono_uid").getValue());
        map.put("evento_uid", d.child("evento_uid").getValue());

        map.put("codigo", d.child("codigo").getValue());
        map.put("porcentagem", d.child("porcentagem").getValue());
        map.put("quantidade", new Date((Long) d.child("quantidade").getValue()));
        map.put("data_validade", d.child("data_validade").getValue());

        return Cupom.mapToCupom(map);
    }
}
