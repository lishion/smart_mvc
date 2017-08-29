package com.smart.framework.core.request;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Route;
import com.smart.framework.bean.BeanFactory;
import com.smart.framework.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lishion on 2017/7/21.
 */
public class RequestHandlers {



    private List<RequestHandler> handlers = new ArrayList<>(10);

    public List<RequestHandler> getHandlers() {
        return handlers;
    }

    public void cacheMap(BeanFactory beanFactory){
        beanFactory.getBeans().forEach(smartBean -> {
            smartBean.visitMethod((clazz, method) -> {
                if(!BeanType.isController(clazz)) return;
                if(method.isAnnotationPresent(Route.class)){
                    Route classRoute = null;
                    Route methodRoute = null;
                    StringBuilder url = new StringBuilder();
                    String submitMethod =Constants.EMPTY_STR;

                    if(clazz.isAnnotationPresent(Route.class)){
                        classRoute = clazz.getAnnotation(Route.class);
                        url.append("/");
                        url.append(urlTrim(classRoute.value()));
                        submitMethod = classRoute.method();
                    }
                    url.append("/");

                    if(method.isAnnotationPresent(Route.class)){
                        methodRoute = method.getAnnotation(Route.class);
                        url.append(urlTrim(methodRoute.value()));
                        submitMethod = methodRoute.method();
                    }
                    RequestHandler handler = new RequestHandler(url.toString(),submitMethod,method);
                    handlers.add(handler);
                }


            });

        });
    }

    
    private String urlTrim(String s){

        int startIndex = 0;
        int endIndex = s.length()-1;

        if(s.matches("([^/])+")){
            return Constants.EMPTY_STR;
        }

        if(s.replaceAll("/","").isEmpty()){
            return Constants.EMPTY_STR;
        }
        if(s.startsWith("/")){
            for(int i=1;i<s.length();i++){
                if(s.charAt(i)!='/'){
                    startIndex = i;
                    break;
                }
            }
        }
        if(s.endsWith("/")){
            for(int i = s.length()-2;i>=0;i--){
                if(s.charAt(i)!='/'){
                    endIndex = i;
                    break;
                }
            }
        }

        return s.substring(startIndex,endIndex+1);

    }


   
    

}
