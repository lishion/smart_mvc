package com.smart.framework.bean;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.aop.Interceptor;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;


/**
 * Created by Lishion on 2017/7/12.
 */
public class SmartBean {

    private Class<?> clazz = null;
    private Class<?> proxyClazz = null;
    private boolean isSingleton;

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    private boolean isProxy = false;
    private boolean isCache = false;
    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    private Object instance = null;
    private Interceptor[] interceptors = null;

    public Class<?> getProxyClazz() {
        return proxyClazz;
    }

    public void setProxyClazz(Class<?> proxyClazz) {
        this.proxyClazz = proxyClazz;
    }

    private BeanType beanType = null;

    public BeanType getBeanType() {
        return beanType;
    }

    public void setBeanType(BeanType beanType) {
        this.beanType = beanType;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    //根据class 来判定是不是同一个Beans
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if( o == null || o.getClass() != this.getClass()){ return false; }

        return true;

    }

    @Override
    public int hashCode() {
        return this.getClazz().hashCode();
    }


    /**
     * 遍历方法
     * @param consumer：函数式接口
     */
    public void visitMethod(BiConsumer<Class<?>,Method> consumer){

        for(Method method:clazz.getDeclaredMethods()){
            consumer.accept(clazz,method);
        }
    }

    /**
     * 遍历属性
     * @param consumer：函数式接口
     */
    public void visitField(BiConsumer<Class<?>,Field> consumer){
        for(Field field : clazz.getDeclaredFields()){
            consumer.accept(clazz,field);
        }
    }

    @Override
    public String toString() {
        return "SmartBean{" +
                ", clazz=" + clazz +
                ", instance=" + instance +
                ", beanType=" + beanType +
                '}';
    }

    public boolean isProxy() {
        return isProxy;
    }

    public void setProxy(boolean proxy) {
        isProxy = proxy;
    }

    /**
     * 通过未代理的方法得到经过代理的方法
     * @param method:未经代理的方法
     * @return 经过代理的方法
     */




}
