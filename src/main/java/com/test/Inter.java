package com.test;

import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.Invocation;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Lishion on 2017/8/20.
 */
public class Inter implements Interceptor {

    @Override
    public void intercept(Invocation iv) throws Throwable {
        System.out.println("la");
        iv.invoke();
        System.out.println("da");
    }
}
