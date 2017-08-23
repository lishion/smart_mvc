package com.smart.framework.aop;

import com.smart.framework.bean.BeanContext;
import com.smart.framework.bean.IBeanFactory;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;


/**
 * Created by Lishion on 2017/7/19.
 */

public class Invocation extends BeanContext {

    private Object object;
    private MethodProxy proxyMethod;
    private Object[] params;
    private Method method;
    private Class clazz;
    private int index = 0;
    private Object result;
    private Class[] interceptors;

    public Invocation(Class[] interceptors, IBeanFactory factory){
        this.interceptors = interceptors;
        this.setFactory(factory);
    }


    public void setInterceptors(Class[] interceptors) {
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

        if(index < interceptors.length ){

             Class<? extends Interceptor> clazz = interceptors[ index++ ] ;
             Interceptor interceptor = getFactory().get(clazz);
             interceptor.intercept(this);
             
        }
        else{
            result = proxyMethod.invokeSuper(object,params);
        }

    }

    
    public void finishInvoke(){

        index = interceptors.length;
    }


}
