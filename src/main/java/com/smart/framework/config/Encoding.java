package com.smart.framework.config;

/**
 * Created by Lishion on 2017/8/28.
 */
public class Encoding implements ConfigElement<String> {
    String s;
    @Override
    public void set(String s) {
        this.s = s;
    }

    @Override
    public String get() {
        return s;
    }
}
