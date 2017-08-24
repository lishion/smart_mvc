package com.smart.framework.layerc;

import com.smart.framework.exception.MultiHandlerException;
import com.smart.framework.exception.NoSuchHandlerException;

import java.util.List;
import java.util.function.Predicate;

public class AnnotationHandlerMatcher implements RequestHandlerMatcher {
    @Override
    public RequestHandler get(String requestUrl,String method , List<RequestHandler> handlers) throws NoSuchHandlerException, MultiHandlerException {
        Predicate<RequestHandler> filter = handler->{
            String reqUrl = removeBracketIfNessary(requestUrl);
            String targetUrl = removeBracketIfNessary(handler.getUrl());
            if(reqUrl.equals(targetUrl)){
                return true;
            }
            return false;
        };
        int count = 0;
        RequestHandler requestHandler = null;
        for(RequestHandler handler:handlers){
            if(filter.test(handler)){
                requestHandler = handler;
                count++;
            }
        }
        if(count==0){
            throw new NoSuchHandlerException(requestUrl,method);
        }else if(count>1){
            throw new MultiHandlerException(requestUrl,method);
        }

        return requestHandler;
    }

    /**
     *
     */
    private String removeBracketIfNessary(String s){
        int endIndex = 0;
        if(s.contains("{")){
            endIndex = s.indexOf("{");
        }
        return s.substring(0,endIndex);
    }

}
