package com.smart.framework.annotation;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by Lishion on 2017/7/21.
 */
public enum BeanType {
    Controler,
    Service,
    Model,
    Component;
    public static boolean isBean(AnnotatedElement element){
        return element.isAnnotationPresent(Bean.class);
    }
    private static boolean isType(AnnotatedElement element,BeanType beanType ){
        if(isBean(element)){
            Bean bean = element.getAnnotation(Bean.class);
            if(bean.value() == beanType){
                return true;
            }
        }
        return false;
    }
    public static boolean isControler(AnnotatedElement element){
        return isType(element,Controler);
    }
    public static boolean isService(AnnotatedElement element){
        return isType(element,Service);
    }
    public static boolean isModel(AnnotatedElement element){
        return isType(element,Model);
    }
}
