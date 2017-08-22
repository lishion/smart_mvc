package com.smart.framework.utils;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.Inject;
import com.smart.framework.bean.BeanFactory;
import com.smart.framework.bean.BeansContainer;
import com.smart.framework.bean.ObjectFactory;
import com.smart.framework.bean.SmartBean;
import com.smart.framework.utils.ReflectionKit;

import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Lishion on 2017/7/28.
 */
public class IOCKit {

    public static void inject(BeansContainer beansContainer){


        for(SmartBean bean : beansContainer.getSmartBeans()){
            injectSingleton(bean,beansContainer);
        }

    }

     public static void  inject(SmartBean smartBean)  {
         Class<?> clazz = smartBean.getClazz();//获取未被代理的Class对象(被代理后 该对象的属性和方法的注解会消失)
         Field[] fields = clazz.getDeclaredFields();
         for(Field field: fields){
            if(field.isAnnotationPresent(Inject.class)){
                Inject inject = field.getAnnotation(Inject.class);
                //获取需要注入的class
                Class<?> injectClass = inject.value() == Inject.class ? field.getType() : inject.value();
                try {

                    SmartBean fieldSmartBean = BeanFactory.build( injectClass );
                    inject(fieldSmartBean);
                    ReflectionKit.setFiled(smartBean.getInstance(),field,fieldSmartBean.getInstance());

                } catch (Exception e) {
                    System.err.println("inject bean: " + clazz.getSimpleName() + "!");
                    e.printStackTrace();
                }
            }
         }
    }


    /**
     * 单例bean注入
     * @param smartBean
     * @param beansContainer
     */
    private static void  injectSingleton(SmartBean smartBean, BeansContainer beansContainer)  {
        Class<?> clazz = smartBean.getClazz();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields){
            if(field.isAnnotationPresent(Inject.class)){
                Inject inject = field.getAnnotation(Inject.class);
                try {

                    Class<?> injectClass = inject.value() == Inject.class ? field.getType() : inject.value();
                    Object o = beansContainer.getBean(injectClass).getInstance();
                    ReflectionKit.setFiled(smartBean.getInstance(), field, o );

                } catch (Exception e) {
                    System.err.println("inject bean: " + clazz.getSimpleName() + " error !!");
                    e.printStackTrace();
                }
            }
        }
    }

}
