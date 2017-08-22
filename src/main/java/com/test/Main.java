package com.test;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;
import com.smart.framework.aop.CglibInterceptor;
import com.smart.framework.aop.InterceptorChain;
import com.smart.framework.aop.Interceptors;
import com.smart.framework.bean.BeanFactory;
import com.smart.framework.bean.BeansContainer;
import com.smart.framework.bean.ClassContainer;
import com.smart.framework.bean.SmartBean;
import com.smart.framework.config.FrameworkConfig;
import com.smart.framework.config.NotFindPage;
import com.smart.framework.config.SmartConfig;
import com.smart.framework.core.SmartMVC;
import com.smart.framework.utils.IOCKit;
import com.smart.framework.utils.ReflectionKit;
import com.test.Action;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Lishion on 2017/7/29.
 */
@Bean(BeanType.Model)
public class Main {
    public static void main(String[] args) throws Exception {


        CglibInterceptor interceptor = new CglibInterceptor();
        Action action = interceptor.createProxy(Action.class);
        Field[] fields = action.getClass().getSuperclass().getDeclaredFields();

        ReflectionKit.setFiled(action,fields[0],new Service());

        System.out.println(action.toString());

        for(Field field:fields){
            System.out.println(field.getName());
        }
    }
}
