package com.smart.framework.exception;

import java.lang.reflect.Field;

public class SetFieldException extends Exception {


    public SetFieldException(Field field,Object o){
         super(" try to inject " +o.toString() + " to " + " field " + field.getType() + "." + field.getName());

    }
    public SetFieldException(String message) {
        super(message);
    }
}
