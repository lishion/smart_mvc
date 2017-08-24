package com.smart.framework.aop;

import com.smart.framework.bean.BeanProcessPreCallback;
import com.smart.framework.bean.BeanWrapper;
import com.smart.framework.bean.IBeanFactory;
import com.smart.framework.exception.GetInstanceException;
import com.smart.framework.utils.ReflectionKit;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InterceptorBeanProcess implements BeanProcessPreCallback {
    
    private Map<Method,Interceptor[]> interceptorChainCache ;
    private Map< Class<? extends Interceptor> , Interceptor > interceptorInstanceCache ;
    private GlobalInterceptors globalInterceptors;
    public InterceptorBeanProcess(GlobalInterceptors interceptors){

        this.interceptorChainCache = new ConcurrentHashMap<>();
        this.interceptorInstanceCache = new ConcurrentHashMap<>();
        this.globalInterceptors = interceptors;
        interceptorInstanceCache.putAll(globalInterceptors.getOnController());
        interceptorInstanceCache.putAll(globalInterceptors.getOnService());
    }

    @Override
    public void process(BeanWrapper beanWrapper)  {
        Class<?> clazz = beanWrapper.getClazz();
        Method[] methods = clazz.getDeclaredMethods();
        MethodCallback methodCallback = new MethodCallback(interceptorChainCache);
        for(Method method:methods){
            Interceptor[] interceptors = getInterceptors(clazz,method);
            interceptorChainCache.put(method,interceptors);
        }
        Object instance = Enhancer.create(clazz,methodCallback);
        beanWrapper.setInstance(instance);

    }

    private Interceptor[] getInterceptors(Class clazz , Method method){

        MethodAnnotationScanner scanner = new MethodAnnotationScanner(clazz,method,globalInterceptors);
        List<Class<? extends Interceptor>> interceptorClass =  scanner.scanInterceptor();
        Interceptor[] interceptors = new Interceptor[interceptorClass.size()];
        for(int i=0;i<interceptors.length;i++){
            Interceptor interceptor = interceptorInstanceCache.get(interceptorClass.get(i));
            if(interceptor==null){
               try {
                   interceptor = (Interceptor) ReflectionKit.getObject(interceptorClass.get(i));
               }catch (GetInstanceException e){
                   System.out.println("get instance of interceptor :" + interceptorClass.getClass().getName()+" error!");
                   e.printStackTrace();
               }
               interceptorInstanceCache.put(interceptor.getClass(),interceptor);
            }
            interceptors[i] = interceptor;
        }
        return interceptors;
    }
}
