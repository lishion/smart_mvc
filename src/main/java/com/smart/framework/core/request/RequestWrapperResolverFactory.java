package com.smart.framework.core.request;

/**
 * Created by Lishion on 2017/8/28.
 */
public class RequestWrapperResolverFactory {
    public RequestWrapperResolver get(String s){

        if(s==null||s.isEmpty()){
            return new DefaultRequestWrapperResolver();
        }

        s = s.toLowerCase().trim();

        if(s.contains("multipart/form-data")){
            return  new MultipartRequestWrapperResolver();
        }else if(s.contains("application/x-www-form-urlencoded")){
            return new FormRequestWrapperResolver();
        }else{
            return new DefaultRequestWrapperResolver();
        }
    }
}
