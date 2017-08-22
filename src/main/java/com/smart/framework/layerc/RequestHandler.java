package com.smart.framework.layerc;

import com.smart.framework.bean.BeanFactory;
import com.smart.framework.bean.SmartBean;
import com.smart.framework.config.SmartConfig;
import com.smart.framework.core.SmartMVC;
import com.smart.framework.utils.IOCKit;
import com.smart.framework.utils.ReflectionKit;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 包含控制器和对应方法
 * Created by Lishion on 2017/7/8.
 */
public class RequestHandler {

    private Object handlerObject;
    private Class<?> handleClass;
    private Method handlerMethod;
    private Parameter[] parameters;
    public Parameter[] getParameters() {
        return parameters;
    }
    
    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public RequestHandler(Class<?> handleClass, Method handlerMethod){
        this.handleClass = handleClass;
        this.handlerMethod = handlerMethod;
    }

    private void resolveHandlerObject() throws Exception {
        SmartBean bean  =  SmartMVC.beansContainer.getBean(handleClass);
        if(bean.isSingleton()){
            handlerObject = bean.getInstance();//如果是单例 则直接从缓存中获取bean对象
        }else{
            try {
                SmartBean newBean = BeanFactory.build(bean.getClazz());//否则每一次请求都会生成新的Bean
                IOCKit.inject(newBean);
                handlerObject = newBean.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("get handle object error!!");
            }
        }

    }

    public Object handl(Object ...args) throws Exception {

        Object o = null;
        resolveHandlerObject();
        try {
            o = ReflectionKit.invokeMethod(handlerObject,handlerMethod,args);
        }catch (Exception e){
            e.printStackTrace();
            throw  new Exception("handle request error!!!");
        }
        return o;

    }

    public Object getHanderClass() {
        return handlerObject;
    }

    public void setHanderClass(Class<?> handlerClass) {
        this.handlerObject = handlerClass;
    }

    public Method getHanderMethod() {
        return handlerMethod;
    }

    public void setHanderMethod(Method handlerMethod) {
        this.handlerMethod = handlerMethod;
    }
}
