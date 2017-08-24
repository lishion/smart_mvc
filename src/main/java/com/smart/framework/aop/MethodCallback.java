package com.smart.framework.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Lishion on 2017/7/21.
 */
public final class MethodCallback implements MethodInterceptor  {

    private Map<Method,Interceptor[]> interceptorChainCache;

    public MethodCallback(Map<Method, Interceptor[]> interceptorChainCache) {
        this.interceptorChainCache = interceptorChainCache;
    }

    /**
     * cglib方法拦截器
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Invocation invocation = new Invocation(interceptorChainCache.get(method));
        invocation.setObject(o);
        invocation.setClazz( method.getDeclaringClass() );
        invocation.setProxyMethod(methodProxy);
        invocation.setMethod(method);
        invocation.setParams(objects);
        invocation.invoke();
        return invocation.getResult();
    }
}
