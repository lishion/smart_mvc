package com.smart.framework.core;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.aop.InterceptorBeanProcess;
import com.smart.framework.aop.InterceptorContainer;
import com.smart.framework.bean.*;
import com.smart.framework.config.ConfigManger;
import com.smart.framework.config.Theme;
import com.smart.framework.exception.GetBeanException;
import com.smart.framework.exception.GetInstanceException;
import com.smart.framework.config.SmartConfig;
import com.smart.framework.layerc.RequestHandler;
import com.smart.framework.layerc.RequestHandlers;
import com.smart.framework.layerm.StringConverters;
import com.smart.framework.utils.ClassKit;
import com.smart.framework.utils.ReflectionKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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

    private BeanFactory beanFactory = new BeanFactory();
    private RequestHandlers requestHandlers = new RequestHandlers();
    private StringConverters converters = new StringConverters();
    private InterceptorContainer interceptorContainer = new InterceptorContainer();
    private Theme theme = new Theme();


    public void run(){
        try {
                SmartConfig smartConfig = null;
                Set<Class<?>> classes = ClassKit.getProjectClass();

                for(Class<?> clazz:classes){
                    if(clazz.isAssignableFrom(SmartConfig.class)){
                        smartConfig = (SmartConfig) ReflectionKit.getObject(clazz);
                        break;
                    }
                }

                if(smartConfig!=null){
                    ConfigManger configManger = new ConfigManger(smartConfig);
                    configManger.register(converters)
                                .register(interceptorContainer)
                                .register(theme)
                                .config();
                }


                InterceptorBeanProcess beanProcess = new InterceptorBeanProcess(interceptorContainer);
                beanFactory.registePreCallback(beanProcess);


                for(Class<?> clazz:classes){
                    beanFactory.cacheBeans(clazz);
                    if(BeanType.isBean(clazz)){
                        beanFactory.get(clazz);
                    }
                }
                requestHandlers.cacheMap(beanFactory);

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

    public HttpServletRequest getRequest() {
        return request.get();
    }
    public HttpServletResponse getResponse() {
        return response.get();
    }
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
    public List<RequestHandler> getRequestHandlers() {return requestHandlers.getHandlers();}
    public StringConverters getConverters() { return  converters;  }

    public InterceptorContainer getInterceptorContainer() {  return interceptorContainer;   }
    public Theme getTheme() {    return theme;    }
}
