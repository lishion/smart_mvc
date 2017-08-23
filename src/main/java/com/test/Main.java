package com.test;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;
import com.smart.framework.aop.MethodCallback;
import com.smart.framework.bean.BeanFactory;
import com.smart.framework.bean.IBeanFactory;
import com.smart.framework.utils.ReflectionKit;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Lishion on 2017/7/29.
 */
@Bean(BeanType.Model)
public class Main {
    public static void main(String[] args) throws Exception {
        IBeanFactory beanFactory = new BeanFactory();

        System.out.println(beanFactory.get(Action.class));
        System.out.println(beanFactory.get(Action.class));
        System.out.println(beanFactory.get(Action.class));

    }
}
