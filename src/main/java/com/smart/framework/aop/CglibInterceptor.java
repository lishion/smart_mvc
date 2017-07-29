package com.smart.framework.aop;


import com.smart.framework.config.FrameworkConfig;
import com.smart.framework.core.SmartMVC;
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

        InterceptorChain interceptorChain = SmartMVC.interceptorChain;
        FrameworkConfig frameWorkConfig = SmartMVC.frameWorkConfig;

        Invocation invocation = new Invocation(frameWorkConfig.getInterceptors());
        invocation.setInterceptorChain( interceptorChain.get(method) );
        invocation.setObject(o);
        invocation.setClazz(method.getDeclaringClass());
        invocation.setProxyMethod(methodProxy);
        invocation.setMethod(method);
        invocation.setObjects(objects);
        invocation.invoke();
        return invocation.getResult();
    }
}
