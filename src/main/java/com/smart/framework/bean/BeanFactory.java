package com.smart.framework.bean;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Inject;

import com.smart.framework.exception.GetInstanceException;
import com.smart.framework.exception.GetBeanException;
import com.smart.framework.exception.SetFieldException;
import com.smart.framework.utils.ReflectionKit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂类
 * Created by Lishion on 2017/7/22.
 */
public class BeanFactory implements IBeanFactory {

    private List<SmartBean> beans = new LinkedList<>();
    private Map< Class<?>,Object > beanInstanceCache = new ConcurrentHashMap<>();
    private List<BeanProcessPreCallback>  preCallbacks= new ArrayList<>(4);

    public List<SmartBean> getBeans() {
        return beans;
    }

    public void registerPreCallback(BeanProcessPreCallback preCallback){
           preCallbacks.add(preCallback);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) throws GetBeanException {
        BeanWrapper beanWrapper = new BeanWrapper(clazz);
        doGet(beanWrapper);
        return (T) beanWrapper.getInstance();
    }
    public void cacheBeans(Class<?> clazz){
        beans.add(analyzeBean(clazz));
    }

    private void doGet(BeanWrapper beanWrapper) throws GetBeanException {

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
            }else {
                beanInstanceCache.put(clazz,beanWrapper.getInstance());
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

        }catch (GetInstanceException e){
                e.printStackTrace();
                throw new GetBeanException("get instance of type: " + clazz.getName() + " error!");
        }catch (SetFieldException e){
                e.printStackTrace();
                throw new GetBeanException("set field of type: " + clazz.getName() + " error!");
        }catch (Exception e){
                e.printStackTrace();
                throw new GetBeanException(e);
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
