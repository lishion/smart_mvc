package com.smart.framework.layerc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParamWrapper {

    private Parameter parameter;
    private Method method;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    private Class<?> methodClazz;

  
    public Class<?> getMethodClazz() {
        return methodClazz;
    }

    public void setMethodClazz(Class<?> methodClazz) {
        this.methodClazz = methodClazz;
    }

    public <T extends Annotation>  T getParamAnnotation(Class<T > clazz){
        return parameter.getAnnotation(clazz);
    }

    public <T extends  Annotation> T getTypeAnnotation(Class<T > clazz){
        return parameter.getType().getAnnotation(clazz);
    }

    public <T extends Annotation> T getMethodAnnotation(Class<T > clazz){
        return method.getAnnotation(clazz);
    }

    public Class<?> getType(){
        return parameter.getType();
    }
 
}
