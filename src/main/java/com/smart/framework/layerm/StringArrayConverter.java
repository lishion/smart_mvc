package com.smart.framework.layerm;

public class StringArrayConverter implements StringConverter {

    @Override
    public Object convert(String t) {
        if(t==null){
            return null;
        }
        return t.split(",");
    }
}
