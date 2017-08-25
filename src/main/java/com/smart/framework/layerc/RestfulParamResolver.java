package com.smart.framework.layerc;

import com.smart.framework.annotation.PathVar;
import com.smart.framework.annotation.Route;
import com.smart.framework.layerm.ConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

public class RestfulParamResolver implements ParamResolver {

    @Override
    public boolean can(Parameter parameter) {
        return parameter.isAnnotationPresent(PathVar.class);
    }

    @Override
    public Object resolve(ParamWrapper paramWrapper, HttpServletRequest request, ConverterFactory factory) {
        Class<?> paramClazz = paramWrapper.getParameter().getType();
        Class<?> methodClazz = paramWrapper.getMethodClazz();
        Route route = methodClazz.getDeclaredAnnotation(Route.class);
        if(route==null)return null;
        String requestUrl = paramWrapper.getRequestUrl();
        String pattern =  "(\\S+?\\{\\S+?}\\S*)+?";
        if(!requestUrl.matches(pattern)){
            return null;
        }
        String value = getRestfulValue(requestUrl);
        return factory.get(paramClazz).convert(value);
    }

    
    private String getRestfulValue(String requestUrl){
        int startIndex = 0;
        int endIndex = 0;
        for(int i=0;i<requestUrl.length();i++){
            if(requestUrl.charAt(i)=='{'){
                startIndex = i;
            }else if(requestUrl.charAt(i)=='}'){
                endIndex = i;
            }
        }
        if(startIndex>=endIndex) {
            return null;
        }
        else{
            return requestUrl.substring(startIndex,endIndex+1);
        }
    }

    


}
