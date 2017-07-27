package com.smart.framework.aop;

import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.config.FrameWorkConfig;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Lishion on 2017/7/21.
 */
public class CglibInterceptor implements MethodInterceptor {



    public <T> T createProxy(Class<T> clazz){
        return  (T)Enhancer.create(clazz,this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        InvocationChain invocationChain = (InvocationChain) DataPool.need(DataPoolItem.invocationChain);
        FrameWorkConfig frameWorkConfig = (FrameWorkConfig) DataPool.need(DataPoolItem.frameworkConfig);
        Invocation invocation = new Invocation(frameWorkConfig.getInterceptors());
        invocation.setInvocationChain(invocationChain.get(method.getClass()));
        invocation.setObject(o);
        invocation.setClazz(method.getDeclaringClass());
        invocation.setProxyMethod(methodProxy);
        invocation.setMethod(method);
        invocation.setObjects(objects);
        invocation.invoke();
        return invocation.getResult();
    }
}
