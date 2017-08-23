package com.smart.framework.bean;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Inject;
import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.InterceptorChain;

import com.smart.framework.core.SmartMVC;
import com.smart.framework.utils.ReflectionKit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * Bean工厂类
 * Created by Lishion on 2017/7/22.
 */
public class BeanFactory implements IBeanFactory {


    private Map< Class<?>,Object > beanInstanceCache = new ConcurrentHashMap<>();
    private List<BeanProcessPreCallback>  preCallbacks= new ArrayList<>(4);


    public void registePreCallback(BeanProcessPreCallback preCallback){
           preCallbacks.add(preCallback);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        BeanWrapper beanWrapper = new BeanWrapper(clazz);
        doGet(beanWrapper);
        return (T) beanWrapper.getInstance();
    }

    private void doGet(BeanWrapper beanWrapper) {


        Class<?> clazz = beanWrapper.getClazz();

        for(BeanProcessPreCallback callback : preCallbacks){
            callback.process(beanWrapper);
        }

        try {
            //该类无需代理
            if(beanWrapper.getInstance()==null){
                Object instance = null;
                //如果是单例模式
                if ( analyzeBean(clazz).isSingleton() ){

                    instance = beanInstanceCache.get(clazz);
                    if( instance == null){//如果未缓存 则先进行缓存
                        instance = ReflectionKit.getObject(clazz);
                        beanInstanceCache.put( clazz,instance );
                    }

                } //如果不是单例模式 则直接添加新类
                else{
                    instance = ReflectionKit.getObject(clazz);
                }
                beanWrapper.setInstance(instance);
            }

            for(Field field : clazz.getDeclaredFields()){
                if(field.isAnnotationPresent(Inject.class)){
                    Inject inject = field.getDeclaredAnnotation(Inject.class);
                    Class<?> injectClass = inject.value() == Inject.class ? field.getType() : inject.value();
                    BeanWrapper fieldWrapper = new BeanWrapper(injectClass);
                    doGet(fieldWrapper);
                    ReflectionKit.setFiled(beanWrapper.getInstance(),field,fieldWrapper.getInstance());
                }

            }

        }catch (Exception e){
                e.printStackTrace();
        }
        
    }

    private SmartBean analyzeBean(Class<?> clazz){
        BeanType beanType = BeanType.Component;
        boolean isSingleton = true;
        if(clazz.isAnnotationPresent(Bean.class)){
            beanType = clazz.getAnnotation(Bean.class).value();
            isSingleton = clazz.getAnnotation(Bean.class).singleton();
        }
        
        SmartBean smartBean = new SmartBean();
        smartBean.setClazz(clazz);
        smartBean.setSingleton(isSingleton);
        smartBean.setBeanType( beanType );

        return smartBean;
    }






    


}
