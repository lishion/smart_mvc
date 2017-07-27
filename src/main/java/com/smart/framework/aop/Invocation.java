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

    private List<Class<? extends Interceptor>> invocationChain;

    private Object result;
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
    public void setInvocationChain(List<Class<? extends Interceptor>> invocationChain) {
        this.invocationChain = invocationChain;
    }

    public void invoke() throws Throwable {

        if(index < invocationChain.size()){
            Class<? extends Interceptor> clazz = invocationChain.get(index++);
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
