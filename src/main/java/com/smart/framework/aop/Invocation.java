package com.smart.framework.aop;

import com.smart.framework.bean.BeanContext;
import com.smart.framework.bean.IBeanFactory;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * Created by Lishion on 2017/7/19.
 */

public class Invocation {

    private Object object;
    private MethodProxy proxyMethod;
    private Object[] params;
    private Method method;
    private Class clazz;
    private int index = 0;
    private Object result;
    private Interceptor[] interceptorChain;

    public Invocation(Interceptor[] interceptorChainCache){

        this.interceptorChain = interceptorChainCache;
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

    Object getResult() {
        return result;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    void setParams(Object[] params) {
        this.params = params;
    }
    void setProxyMethod(MethodProxy proxyMethod) {
        this.proxyMethod = proxyMethod;
    }

    @SuppressWarnings("unchecked")

    public void invoke() throws Throwable {

        if(index < interceptorChain.length ){

            interceptorChain[index++].intercept(this);
        }
        else{
            result = proxyMethod.invokeSuper(object,params);
        }

    }

    
    public void finishInvoke(){

        index = interceptorChain.length;
    }


}
