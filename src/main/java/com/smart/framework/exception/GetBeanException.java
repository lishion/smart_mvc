package com.smart.framework.exception;

public class GetBeanException extends Exception {
    public GetBeanException(String message) {
        super(message);
    }
    public GetBeanException(Exception e){
        super(e);
    }
}
