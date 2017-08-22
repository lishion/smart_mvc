package com.smart.framework.bean;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;
import com.smart.framework.aop.CglibInterceptor;
import com.smart.framework.aop.InterceptorChain;

import com.smart.framework.core.SmartMVC;
import com.smart.framework.utils.ReflectionKit;

import java.lang.reflect.Method;

/**
 * Bean工厂类
 * Created by Lishion on 2017/7/22.
 */
public class BeanFactory {

    /**
     * 根据class产生对应的bean实例
     * @param clazz
     * @return bean实例
     * @throws Exception
     */
    public  static SmartBean build(Class<?> clazz) throws Exception {

        BeanType beanType = BeanType.Component;
        boolean isSigleton = true;
        if(clazz.isAnnotationPresent(Bean.class)){
            beanType = clazz.getAnnotation(Bean.class).value();
            isSigleton = clazz.getAnnotation(Bean.class).singleton();
        }
        Object instance = createInstance(clazz);
        SmartBean smartBean = new SmartBean();
        smartBean.setInstance( instance );
        smartBean.setClazz(clazz);
        smartBean.setSingleton(isSigleton);
        if(instance.getClass() != clazz){//如果得到的class和原来的class不相等 则该类已经被代理
            smartBean.setProxyClazz(instance.getClass());
            smartBean.setProxy(true);
        }
        smartBean.setBeanType( beanType );
        return smartBean;
    }

    /**
     * 根据class 产生对应的实例
     * @param clazz
     * @return
     * @throws Exception
     */
    private static Object createInstance(Class<?> clazz) throws Exception {

        CglibInterceptor cglibInterceptor = new CglibInterceptor();
        InterceptorChain invocationChain = SmartMVC.interceptorChain;
        if(invocationChain.needProxy(clazz)){
            return cglibInterceptor.createProxy(clazz);
        }
        return ReflectionKit.getObject(clazz);

    }
}
