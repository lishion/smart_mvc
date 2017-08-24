package com.smart.framework.layerc;

import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Route;
import com.smart.framework.bean.BeanFactory;
import com.smart.framework.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lishion on 2017/7/21.
 */
public class RequestHandlers {



    public List<RequestHandler> handlers = new ArrayList<>(10);

    public void cacheMap(BeanFactory beanFactory){
        beanFactory.getBeans().forEach(smartBean -> {
            smartBean.visitMethod((clazz, method) -> {
                if(!BeanType.isController(clazz)) return;
                Route classRoute = null;
                Route methodRoute = null;
                StringBuilder url = new StringBuilder();
                String submitMethod =Constants.EMPTY_STR;

                if(clazz.isAnnotationPresent(Route.class)){
                    classRoute = clazz.getAnnotation(Route.class);
                    url.append("/");
                    url.append(urlTrim(classRoute.value()));
                    url.append("/");
                    submitMethod = classRoute.method();
                }

                if(method.isAnnotationPresent(Route.class)){
                    methodRoute = method.getAnnotation(Route.class);
                    url.append(urlTrim(methodRoute.value()));
                    url.append("/");
                    submitMethod = methodRoute.method();
                }
                RequestHandler handler = new RequestHandler(url.toString(),submitMethod,method);
                handlers.add(handler);
            });

        });
    }

    
    private String urlTrim(String s){

        int startIndex = 0;
        int endIndex = s.length()-1;
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