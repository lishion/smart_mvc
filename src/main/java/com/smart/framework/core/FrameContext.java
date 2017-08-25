package com.smart.framework.core;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Config;
import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.aop.InterceptorBeanProcess;
import com.smart.framework.bean.*;
import com.smart.framework.exception.GetBeanException;
import com.smart.framework.exception.GetInstanceException;
import com.smart.framework.config.SmartConfig;
import com.smart.framework.layerc.RequestHandlers;
import com.smart.framework.layerm.StringConverters;
import com.smart.framework.utils.ClassKit;
import com.smart.framework.utils.ReflectionKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Lishion on 2017/7/23.
 */
public class FrameContext {


    private ThreadLocal<HttpServletRequest> request;
    private ThreadLocal<HttpServletResponse> response;
    public void setWebContext(HttpServletRequest request, HttpServletResponse response){
        this.request.set(request);
        this.response.set(response);
    }

    public HttpServletRequest getRequest() {
        return request.get();
    }

    public HttpServletResponse getResponse() {
        return response.get();
    }

    public SmartConfig getSmartConfig() {
        return smartConfig;
    }

    
    private GlobalInterceptors globalInterceptors = new GlobalInterceptors();
    private BeanFactory beanFactory = new BeanFactory();
    private SmartConfig smartConfig = null;
    private StringConverters converters = new StringConverters();


    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public RequestHandlers getRequestMap() {
        return requestMap;
    }

    private RequestHandlers requestMap = new RequestHandlers();

    public StringConverters getConverters() {
        return converters;
    }

    public void run(){
        try {

                Set<Class<?>> classes = ClassKit.getProjectClass();

                for(Class<?> clazz:classes){
                    if(clazz.isAnnotationPresent(Config.class)&&clazz.isAssignableFrom(SmartConfig.class)){
                        smartConfig = (SmartConfig) ReflectionKit.getObject(clazz);
                           break;
                    }
                }

                if(smartConfig!=null){
                    smartConfig.setGlobalInterceptor(globalInterceptors);
                    smartConfig.setConverter(converters);

                }

                InterceptorBeanProcess beanProcess = new InterceptorBeanProcess(globalInterceptors);
                beanFactory.registePreCallback(beanProcess);

                for(Class<?> clazz:classes){
                    beanFactory.cacheBeans(clazz);
                    if(BeanType.isBean(clazz)){
                        beanFactory.get(clazz);
                    }
                }
                requestMap.cacheMap(beanFactory);

        }catch (IOException e){
            e.printStackTrace();

        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }catch (GetBeanException e){
            e.printStackTrace();
            
        }catch (GetInstanceException e){
              e.printStackTrace();
        }
    }
    

}
