package com.smart.framework.cache;

/**
 * Created by Lishion on 2017/7/23.
 */
public enum DataPoolItem {
    beanClass("beanClass"),
    beanContainer("beanContainer"),
    frameworkConfig("frameworkConfig"),
    invocationChain("invocationChain"),
    requestMap("requestMap");

    DataPoolItem(String name){
        this.name = name;
    }
    private String name;
    public String getName(){
        return name;
    }
}
