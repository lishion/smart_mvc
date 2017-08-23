package com.smart.framework.bean;

public interface IBeanFactory {
    <T> T  get(Class<T> clazz);//通过clazz得到bean
}
