package com.smart.framework.layerm;

import java.util.HashMap;
import java.util.Map;

public class StringMapConverter implements StringConverter{
    
    @Override
    public Object convert(String t) {
        if(t==null){
            return null;
        }
        String[] kvs = t.split(",");
        Map<String,Object> map = new HashMap<>();
        for (String kv : kvs) {
            String[] kva = kv.split(":");
            if(kva.length!=2){
                continue;
            }
            map.put(kva[0],kva[1]);
        }
        return map;
    }
}
