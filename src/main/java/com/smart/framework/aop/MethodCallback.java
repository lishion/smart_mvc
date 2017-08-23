package com.smart.framework.aop;

import com.smart.framework.bean.BeanFactory;
import com.smart.framework.config.FrameworkConfig;
import com.smart.framework.core.SmartMVC;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * Created by Lishion on 2017/7/21.
 */
public final class MethodCallback implements MethodInterceptor  {

    public MethodCallback(Invocation invocation) {
        this.invocation = invocation;
    }

    private Invocation invocation;

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
        
        invocation.setObject(o);
        invocation.setClazz( method.getDeclaringClass() );
        invocation.setProxyMethod(methodProxy);
        invocation.setMethod(method);
        invocation.setParams(objects);
        invocation.invoke();
        return invocation.getResult();
    }
}