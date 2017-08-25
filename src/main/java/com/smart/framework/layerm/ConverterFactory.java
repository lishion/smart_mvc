package com.smart.framework.layerm;

public interface ConverterFactory {
    Converter get(Class<?> clazz);
}
