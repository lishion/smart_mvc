package com.smart.framework.bean;

/**
 * Created by Lishion on 2017/8/20.
 */
public interface ObjectFactory {
    Object getObject(Class<?> clazz) throws Exception;
}
