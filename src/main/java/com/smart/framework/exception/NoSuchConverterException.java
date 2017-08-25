package com.smart.framework.exception;

public class NoSuchConverterException extends Exception {
    public NoSuchConverterException(Class<?> fromType, Class<?> toType) {
        super("can't no find converter from "+fromType.getName() + " to "  + toType.getName());
    }
}
