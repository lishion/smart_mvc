package com.smart.framework.core.request;

import com.smart.framework.bean.SmartBean;
import com.smart.framework.core.FrameContext;
import com.smart.framework.utils.ReflectionKit;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 包含控制器和对应方法
 * Created by Lishion on 2017/7/8.
 */
public class RequestHandler {

    private final String url;
    private final String requestMethod;
    private final Method handlerMethod;

    public RequestHandler(String url, String requestMethod, Method handlerMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.handlerMethod = handlerMethod;

    }

    public String getUrl() { return url; }
    public String getRequestMethod() { return requestMethod; }
    public Method getHandlerMethod() { return handlerMethod; }


    public Object handle(Object instance, Object[] params) throws Exception {

        Object o;
        try {
            o = ReflectionKit.invokeMethod(instance,handlerMethod,params);
        }catch (Exception e){
            e.printStackTrace();
            throw  new Exception("handle request error!!!");
        }
        return o;

    }
}
