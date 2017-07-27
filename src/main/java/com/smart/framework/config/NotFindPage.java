package com.smart.framework.config;

/**
 * Created by Lishion on 2017/7/24.
 */
public class NotFindPage implements ConfigElement<String> {
    String page = null;
    @Override
    public void set(String s) {
        page = s;
    }

    @Override
    public String get() {
        return page;
    }
}
