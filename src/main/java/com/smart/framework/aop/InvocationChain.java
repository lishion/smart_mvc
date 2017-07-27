package com.smart.framework.aop;

import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.config.FrameWorkConfig;
import com.smart.framework.utils.ReflectionUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/7/19.
 */
public class InvocationChain {

    private  Map< Class<?> , List< Class<? extends Interceptor> >> invocationMap;

    public InvocationChain(){
        invocationMap = new ConcurrentHashMap<>();
    }

    public int create(Class<?> clazz, Method method){
        AopAnnotationUtil aopAnnotationUtil = new AopAnnotationUtil(clazz,method);
        List<Class<? extends Interceptor>> chain = aopAnnotationUtil.getAopClass();
        cheakChain(chain);
        invocationMap.put( method.getClass() , chain);
        return chain.size();
    }

    public  List< Class<? extends Interceptor> > get(Class<?> clazz){
        return invocationMap.get(clazz);
    }

    private static void cheakChain(List<Class<? extends Interceptor>> interceptorClass){
        FrameWorkConfig frameWorkConfig = (FrameWorkConfig) DataPool.need(DataPoolItem.frameworkConfig);
        Interceptors interceptors = frameWorkConfig.getInterceptors();
        interceptorClass.forEach(clazz->{
            if(interceptors.get(clazz)==null){
                Object o =null;
                try {
                    o = ReflectionUtil.getObject(clazz);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(o instanceof Interceptor){
                    interceptors.set((Interceptor)o);
                }
            }
        });
    }

    @Override
    public String toString() {
        return "InvocationChain{" +
                "invocationMap=" + invocationMap +
                '}';
    }
}
