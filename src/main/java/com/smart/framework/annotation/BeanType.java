package com.smart.framework.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;

/**
 * Created by Lishion on 2017/7/21.
 */
public enum BeanType {
    Controller,
    Service,
    Model,
    Component;
    public static boolean isBean(Class<?> element){
        return element.isAnnotationPresent(Bean.class)&&
                !Modifier.isAbstract(element.getModifiers()) &&
                !Modifier.isInterface(element.getModifiers());
    }
    private static boolean isType(Class<?> element,BeanType beanType ){
        
        if(isBean(element)){
            Bean bean = element.getAnnotation(Bean.class);
            if(bean.value() == beanType){
                return true;
            }
        }
        return false;
    }
    public static boolean isController(Class<?> element){
        return isType(element,Controller);
    }
    public static boolean isService(Class<?> element){
        return isType(element,Service);
    }
    public static boolean isModel(Class<?> element){
        return isType(element,Model);
    }
}
