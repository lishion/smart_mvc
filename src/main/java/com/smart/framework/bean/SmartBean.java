package com.smart.framework.bean;

import com.smart.framework.annotation.BeanType;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;


/**
 * Created by Lishion on 2017/7/12.
 */
public class SmartBean {


    private Class<?> clazz = null;
    private Class<?> proxyClazz = null;
    private Object instance = null;

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

    /**
     * 通过未代理的方法得到经过代理的方法
     * @param method:未经代理的方法
     * @return 经过代理的方法
     */
    public Method getProxyMethod(Method method) throws NoSuchMethodException{
       return proxyClazz.getDeclaredMethod(method.getName(),method.getParameterTypes());
    }
}
