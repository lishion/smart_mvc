package com.smart.framework.aop;

import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.Interceptors;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Queue;

/**
 * Created by Lishion on 2017/7/19.
 */

public class Invocation {

    private Object object;
    private MethodProxy proxyMethod;
    private Object[] objects;
    private Method method;
    private Class clazz;
    private Interceptors interceptors;
    private int index;
    private Object result;
    private Class[] interceptorChain;


    public Invocation(Interceptors interceptors){
        this.interceptors = interceptors;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getResult() {
        return result;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() { return objects; }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public void setInterceptorChain(Class[] interceptorChain) {
        this.interceptorChain = interceptorChain;
    }


    @SuppressWarnings("unchecked")
    public void invoke() throws Throwable {

        if(index < interceptorChain.length){
             Class<? extends Interceptor> clazz = interceptorChain[index++];
             Interceptor interceptor = interceptors.get(clazz);
             interceptor.intercept(this);
        }
        else{
            result = proxyMethod.invokeSuper(object,objects);
        }

    }

    public void setProxyMethod(MethodProxy proxyMethod) {
        this.proxyMethod = proxyMethod;
    }
}
