package com.smart.framework.bean;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.aop.ProxyCreator;

/**
 * Created by Lishion on 2017/7/22.
 */
public class BeanFactory {
    public  static SmartBean build(Class<?> clazz, BeanType beanType){
        SmartBean smartBean = new SmartBean();
        smartBean.setClazz(clazz);
        Object instance = ProxyCreator.createProxy(clazz);
        smartBean.setInstance( instance );
        if(instance.getClass() != clazz){//如果得到的class和原来的class不相等 则该类已经被代理
            smartBean.setProxyClazz(instance.getClass());
        }
        smartBean.setClazz(clazz);
        smartBean.setBeanType( beanType );
        return smartBean;
    }
}
