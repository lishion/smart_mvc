package com.smart.framework.layerc;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Route;
import com.smart.framework.bean.BeansContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lishion on 2017/7/21.
 */
public class RequestMap {

    private  Map<RequestRoute,RequestHandler> requestMap = new HashMap<>();

    public  Map<RequestRoute, RequestHandler> getRequestMap() {
        return requestMap;
    }

    public  RequestHandler getRequestHander(String url, String method){

        if(url==null || method==null) return null;
        RequestRoute route = new RequestRoute(url,method);
        for(RequestRoute requestRoute:requestMap.keySet()){
            if(requestRoute.equals(route)){
                return requestMap.get(requestRoute);
            }
        }
        return null;
    }


    public void load(BeansContainer beansContainer){
        beansContainer.visit(smartBean -> {
            smartBean.visitMethod((clazz, method) -> {
                if(!BeanType.isControler(clazz)) return;
                Route classRoute = null;
                Route methodRoute = null;
                String url = "";
                String submitMethod ="";

                if(clazz.isAnnotationPresent(Route.class)){
                    classRoute = clazz.getAnnotation(Route.class);
                    url += classRoute.value();
                    submitMethod = classRoute.method();
                }

                if(method.isAnnotationPresent(Route.class)){
                    methodRoute = method.getAnnotation(Route.class);
                    url += methodRoute.value();
                    submitMethod = methodRoute.method();
                }

                RequestRoute route = new RequestRoute(url,submitMethod);
                RequestHandler handler = null;

                if(smartBean.getProxyClazz()!=null){
                    try {

                        handler = new  RequestHandler(smartBean.getProxyClazz(),smartBean.getProxyMethod(method));

                    }
                    catch (NoSuchMethodException e){
                        e.printStackTrace();
                    }
                }else{
                    handler = new RequestHandler(smartBean.getClazz(),method);
                }
                handler.setParameters(method.getParameters());
                requestMap.put(route,handler);
            });

        });
    }

}
