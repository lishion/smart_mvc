package com.smart.framework.config;

/**
 * Created by Lishion on 2017/7/24.
 */
public interface ConfigElement<T> {
    void set(T t);
    T get();
}
