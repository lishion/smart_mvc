package com.smart.framework.layerc;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Clean;
import com.smart.framework.annotation.Route;
import com.smart.framework.bean.BeansContainer;
import com.smart.framework.boot.Service;
import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;

/**
 * 请求路径和控制器映射
 * Created by Lishion on 2017/7/8.
 */
public class RequestService implements Service{

    @Override
    public void start() {
        BeansContainer beansContainer = (BeansContainer) DataPool.need(DataPoolItem.beanContainer);
        RequestMap requestMap = (RequestMap)DataPool.need(DataPoolItem.requestMap);
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
                            handler = new  RequestHandler(smartBean.getInstance(),smartBean.getProxyMethod(method));
                        }
                        catch (NoSuchMethodException e){
                            e.printStackTrace();
                        }
                    }else{
                        handler = new RequestHandler(smartBean.getInstance(),method);
                    }
                    handler.setParameters(method.getParameters());
                    requestMap.getRequestMap().put(route,handler);

            });

        });
    }

}
