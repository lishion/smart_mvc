package com.smart.framework.exception;

public class NoSuchHandlerException extends Exception {
    public NoSuchHandlerException(String url,String method) {
        super("can't find handler for url: " + url + " ，request method: " + method );
    }
}
