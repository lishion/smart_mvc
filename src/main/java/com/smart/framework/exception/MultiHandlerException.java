package com.smart.framework.exception;

public class MultiHandlerException extends Exception {
    public MultiHandlerException(String message,String method) {
        super("find more the one handler for url: "+message+" request method: "+method);
    }
}
