package com.smart.framework.aop;

import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.utils.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * Created by Lishion on 2017/7/21.
 */
public class ProxyCreator {

    private static CglibInterceptor cgLibProxy = new CglibInterceptor();

    public static Object createProxy(Class<?> clazz){
        InvocationChain invocationChain = (InvocationChain) DataPool.need(DataPoolItem.invocationChain);
        Method[] methods = clazz.getDeclaredMethods();
        boolean isProxy = false;
        for(Method method:methods){
           if( invocationChain.create(clazz,method)>=1){
               isProxy = true;
           }
        }
        Object o = null;
        if(isProxy){
            return cgLibProxy.createProxy(clazz);
        }
        try {
            o = ReflectionUtil.getObject(clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
       return o;
    }
}
