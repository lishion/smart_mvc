package com.smart.framework.aop;

import com.smart.framework.annotation.*;
import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.config.FrameWorkConfig;

import java.lang.reflect.AnnotatedElement;
import java.util.*;

import static com.smart.framework.aop.AopAnnotationUtil.State.*;

/**
 * Created by Lishion on 2017/7/19.
 */
public class AopAnnotationUtil {

    public enum State{
        GLOBAL_INTERCEPTOR,
        CLASS_INTERCEPTOR_CLEAN,
        CLASS_INTERCEPTOR,
        METHOD_INTERCEPTOR_CLEAN,
        METHOD_INTERCEPTOR,
        RETURN;
    }

    private AnnotatedElement clazz,method;
    private Class<? extends Interceptor> ci,si;
    private List<Class<? extends Interceptor>> classesGet,classesClean;

    public AopAnnotationUtil(AnnotatedElement clazz , AnnotatedElement method){
        FrameWorkConfig frameWorkConfig = (FrameWorkConfig) DataPool.need(DataPoolItem.frameworkConfig);
        this.clazz = clazz;
        this.method = method;
        ci = frameWorkConfig.getInterceptors().getControlInterceptorsClass();
        si = frameWorkConfig.getInterceptors().getServiceInterceptorsClass();
        classesGet = new LinkedList<>();
        classesClean = new LinkedList<>();
    }

    /**
     *
     * @return 该方法包含的所有拦截器class集合
     */
    public List<Class<? extends Interceptor>> getAopClass(){

        State action = GLOBAL_INTERCEPTOR;
        if(isAllClear(method)){
            action = METHOD_INTERCEPTOR;
        }else if(isAllClear(clazz)){
            action = CLASS_INTERCEPTOR;
        }
        while (true){
            switch (action) {
                case GLOBAL_INTERCEPTOR:
                    addGlobal();
                    action = CLASS_INTERCEPTOR_CLEAN;
                    break;
                case CLASS_INTERCEPTOR_CLEAN:
                    addClean(clazz);
                    action = CLASS_INTERCEPTOR;
                    break;
                case CLASS_INTERCEPTOR:
                    addGet(clazz);
                    action = METHOD_INTERCEPTOR_CLEAN;
                    break;
                case METHOD_INTERCEPTOR_CLEAN:
                    addClean(method);
                    action = METHOD_INTERCEPTOR;
                    break;
                case METHOD_INTERCEPTOR:
                    addGet(method);
                    action = RETURN;
                case RETURN:
                    classesGet.removeAll(classesClean);
                    return classesGet;
                default: ;
            }
        }
    }

    /**
     * 判断接口是否有cleanAll注解
     * @param element ：需要判断的接口
     * @return 判断结果
     */
    private boolean isAllClear(AnnotatedElement element){
        return element.isAnnotationPresent(CleanAll.class);
    }


    /**
     * 获得全局拦截器
     */
    private void addGlobal(){
        if(ci!=null && BeanType.isControler(clazz)){classesGet.add(ci);}
        if(si!=null && BeanType.isService(clazz)){classesGet.add(si);}
    }

    /**
     *
     * @param element:添加该接口上需要得到的拦截器到得到拦截器集合
     */
    private void addGet(AnnotatedElement element){
        if(element.isAnnotationPresent(Before.class)){
            Before before = element.getAnnotation(Before.class);
            classesGet.addAll(Arrays.asList(before.value()));
        }
    }

    /**
     *
     * @param element:添加该接口上需要清除的拦截器到清除拦截器集合
     */
    private void addClean(AnnotatedElement element){
        if(element.isAnnotationPresent(Clean.class)){
            Clean before = element.getAnnotation(Clean.class);
            classesClean.addAll(Arrays.asList(before.value()));
        }
    }

}
