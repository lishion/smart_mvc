package com.smart.framework.aop;

/**
 * Created by Lishion on 2017/7/19.
 */
public interface Interceptor  {

    public void intercept(Invocation iv) throws Throwable;

}
