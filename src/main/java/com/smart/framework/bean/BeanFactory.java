package com.smart.framework.bean;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;
import com.smart.framework.aop.CglibInterceptor;
import com.smart.framework.aop.InterceptorChain;

import com.smart.framework.core.SmartMVC;
import com.smart.framework.utils.ReflectionKit;

import java.lang.reflect.Method;

/**
 * Created by Lishion on 2017/7/22.
 */
public class BeanFactory {

    public  static SmartBean build(Class<?> clazz) throws Exception {

        BeanType beanType = BeanType.Component;
        if(clazz.isAnnotationPresent(Bean.class)){
            beanType = clazz.getAnnotation(Bean.class).value();
        }
        Object instance = createInstance(clazz);
        SmartBean smartBean = new SmartBean();
        smartBean.setInstance( instance );
        smartBean.setClazz(clazz);
        if(instance.getClass() != clazz){//如果得到的class和原来的class不相等 则该类已经被代理
            smartBean.setProxyClazz(instance.getClass());
        }
        smartBean.setBeanType( beanType );
        return smartBean;
    }

    private static Object createInstance(Class<?> clazz) throws Exception {

        CglibInterceptor cglibInterceptor = new CglibInterceptor();
        InterceptorChain invocationChain = SmartMVC.interceptorChain;
        if(invocationChain.needProxy(clazz)){
            return cglibInterceptor.createProxy(clazz);
        }
        return ReflectionKit.getObject(clazz);

    }
}
