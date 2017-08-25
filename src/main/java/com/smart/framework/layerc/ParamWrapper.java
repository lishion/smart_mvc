package com.smart.framework.layerc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParamWrapper {

    private Parameter parameter;
    private String requestUrl;
    private String requestMethod;
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

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
 
}
