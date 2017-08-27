package com.smart.framework.aop;

import com.smart.framework.bean.BeanProcessPreCallback;
import com.smart.framework.bean.BeanWrapper;
import com.smart.framework.exception.GetInstanceException;
import com.smart.framework.utils.ReflectionKit;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Method;
import java.util.List;

public class InterceptorBeanProcess implements BeanProcessPreCallback {
    
    private InterceptorContainer container;

    public InterceptorBeanProcess(InterceptorContainer container) {
        this.container = container;
    }

    @Override
    public void process(BeanWrapper beanWrapper)  {

        Class<?> clazz = beanWrapper.getClazz();
        Method[] methods = clazz.getDeclaredMethods();

        boolean needProxy = false;
        for(Method method:methods){
            Interceptor[] interceptors = container.getInterceptorChain(method);
            if( interceptors==null ){//如果未缓存 则先进行缓存
                interceptors = getInterceptors(clazz,method);
                if(interceptors.length<1){
                    continue;
                }
                needProxy = true;
                container.cacheInterceptorChain(method,interceptors);
            }else{
                needProxy = true;
            }

        }
        if(!needProxy){
            return;
        }
        MethodCallback methodCallback = new MethodCallback(container.getInterceptorChainCache());
        Object instance = Enhancer.create(clazz,methodCallback);
        beanWrapper.setInstance(instance);

    }

    private Interceptor[] getInterceptors(Class clazz , Method method){

        MethodAnnotationScanner scanner = new MethodAnnotationScanner(clazz,method,container.getGlobalInterceptors());
        List<Class<? extends Interceptor>> interceptorClass =  scanner.scanInterceptor();
        Interceptor[] interceptors = new Interceptor[interceptorClass.size()];
        for(int i=0;i<interceptors.length;i++){
            Interceptor interceptor = container.getInterceptor(interceptorClass.get(i));
            if(interceptor==null){
               try {
                   interceptor = (Interceptor) ReflectionKit.getObject(interceptorClass.get(i));
               }catch (GetInstanceException e){
                   System.out.println("get instance of interceptor :" + interceptorClass.getClass().getName()+" error!");
                   e.printStackTrace();
               }
               container.cacheIntercepotorInstance(interceptor.getClass(),interceptor);
            }
            interceptors[i] = interceptor;
        }
        return interceptors;
    }
}
