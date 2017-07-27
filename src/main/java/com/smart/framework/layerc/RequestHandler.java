package com.smart.framework.layerc;

import com.smart.framework.utils.ReflectionUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 包含控制器和对应方法
 * Created by Lishion on 2017/7/8.
 */
public class RequestHandler {
    private Object handerObject;
    private Method handerMethod;
    private Parameter[] parameters;

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public RequestHandler(Object handerObject, Method handerMethod){
        this.handerObject = handerObject;
        this.handerMethod = handerMethod;
    }

    public Object handl(Object ...args) throws Exception {

        Object o = null;
        try {
            o = ReflectionUtil.invokeMethod(handerObject,handerMethod,args);
        }catch (Exception e){
            e.printStackTrace();
            throw  new Exception("handle request error!!!");
        }
        return o;

    }

    public Object getHanderClass() {
        return handerObject;
    }

    public void setHanderClass(Class<?> handerClass) {
        this.handerObject = handerClass;
    }

    public Method getHanderMethod() {
        return handerMethod;
    }

    public void setHanderMethod(Method handerMethod) {
        this.handerMethod = handerMethod;
    }
}
