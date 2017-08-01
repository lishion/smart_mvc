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

    /**
     * 缓存指定方法的拦截器
     * @param clazz:方法所在的类
     * @param method:方法
     */
    private void doCache(Class<?> clazz, Method method){
        AopAnnotationUtil aopAnnotationUtil = new AopAnnotationUtil(clazz,method);
        List<Class<? extends Interceptor>> chain = aopAnnotationUtil.getAopClass();

        if(chain.size()<1){return;}
        checkChain(chain);
        Class[] interceptors = new Class[chain.size()];
        chain.toArray(interceptors);
        invocationMap.put( method , interceptors);

    }

    /**
     * 从class容器中缓存所有方法的拦截器
     * @param classContainer
     */
    public void load(ClassContainer classContainer){
       Set<Class<?>> classes =  classContainer.getBeanClass();
       classes.forEach(clazz->{
           Method[] methods = clazz.getDeclaredMethods();
           for(Method method:methods){
               doCache(clazz,method);
           }
       });
    }

    /**
     *  判断指定类是否需要代理
     * @param clazz:需要判断的类
     * @return
     */
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

    /**
     * 将对应的Interceptor class缓存到Interceptor map中
     * @param interceptorClass
     */
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
