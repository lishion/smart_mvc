package com.smart.framework.interptor;

import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.Invocation;

/**
 * Created by Lishion on 2017/7/23.
 */
public class GCInterptor implements Interceptor {
    @Override
    public void intercept(Invocation iv) throws Throwable {

        System.out.println("before lalal");
        iv.invoke();
        System.out.println("after lalal");

    }
}
