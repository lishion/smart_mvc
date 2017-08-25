package com.smart.framework.layerc;

import com.smart.framework.exception.MultiHandlerException;
import com.smart.framework.exception.NoSuchHandlerException;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AnnotationHandlerMatcher implements RequestHandlerMatcher {
    @Override
    public RequestHandler get(String requestUrl,String method , List<RequestHandler> handlers) throws NoSuchHandlerException, MultiHandlerException {
        Predicate<RequestHandler> filter = handler->{
            String reqUrl = removeBracketIfNecessary(requestUrl);
            String targetUrl = removeBracketIfNecessary(handler.getUrl());
            return reqUrl.equals(targetUrl);
        };

        //首先找到url匹配的
        List<RequestHandler> urlMatchItem = handlers.stream().filter(filter).collect(Collectors.toList());
        List<RequestHandler> allMatchItem = null;

        if(urlMatchItem.size()==1){
            return urlMatchItem.get(0);
        }else if(urlMatchItem.size()==0){
            throw new NoSuchHandlerException(requestUrl,method);
        }else if(urlMatchItem.size()>1){ //如果不只一个匹配 则继续寻找方法匹配
             allMatchItem =  urlMatchItem
                            .stream()
                            .filter(handler->handler.getRequestMethod().equals(method))
                            .collect(Collectors.toList());
        }

        if(allMatchItem.size()==0){
            throw new NoSuchHandlerException(requestUrl,method);
        }
        if(allMatchItem.size()>1){
            throw new MultiHandlerException(requestUrl,method);
        }
        return allMatchItem.get(0);
    }

    /**
     * 去掉restful表达式中的括号部分
     */
    private String removeBracketIfNecessary(String s){
        int endIndex = 0;
        if(s.contains("{")){
            endIndex = s.indexOf("{");
        }
        return s.substring(0,endIndex);
    }

}
