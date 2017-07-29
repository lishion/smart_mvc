package com.smart.framework.aop;


import com.smart.framework.bean.ClassContainer;
import com.smart.framework.config.FrameworkConfig;
import com.smart.framework.core.SmartMVC;
import com.smart.framework.utils.ReflectionKit;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/7/19.
 */
public class InterceptorChain {

    private  Map< Method , Class<?extends Interceptor>[]> invocationMap;

    public InterceptorChain(){
        invocationMap = new ConcurrentHashMap<>();
    }

    private void doCache(Class<?> clazz, Method method){
        AopAnnotationUtil aopAnnotationUtil = new AopAnnotationUtil(clazz,method);
        List<Class<? extends Interceptor>> chain = aopAnnotationUtil.getAopClass();

        if(chain.size()<1){return;}
        checkChain(chain);
        Class[] interceptors = new Class[chain.size()];
        chain.toArray(interceptors);
        invocationMap.put( method , interceptors);

    }

    public void load(ClassContainer classContainer){
       Set<Class<?>> classes =  classContainer.getBeanClass();
       classes.forEach(clazz->{
           Method[] methods = clazz.getDeclaredMethods();
           for(Method method:methods){
               doCache(clazz,method);
           }
       });
    }

    public  boolean needProxy(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods ){
            if( invocationMap.get(method)!=null){
                return true;
            }
        }
        return false;
    }

    public  Class[] get(Method method){
        return invocationMap.get(method);
    }

    private static void checkChain(List<Class<? extends Interceptor>> interceptorClass){
        FrameworkConfig frameWorkConfig = SmartMVC.frameWorkConfig;
        Interceptors interceptors = frameWorkConfig.getInterceptors();
        interceptorClass.forEach(clazz->{
            if(interceptors.get(clazz)==null){
                Object o =null;
                try {
                    o = ReflectionKit.getObject(clazz);
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
        return "InterceptorChain{" +
                "invocationMap=" + invocationMap +
                '}';
    }
}
