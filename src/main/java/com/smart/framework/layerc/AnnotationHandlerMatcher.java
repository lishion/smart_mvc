package com.smart.framework.layerc;

import com.smart.framework.annotation.RequestType;
import com.smart.framework.exception.MultiHandlerException;
import com.smart.framework.exception.NoSuchHandlerException;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Handler;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AnnotationHandlerMatcher implements RequestHandlerMatcher {
    @Override
    public RequestHandler get(String requestUrl,String method , List<RequestHandler> handlers) throws NoSuchHandlerException, MultiHandlerException {
     
        //首先找到url匹配的
        List<RequestHandler> urlMatchItem = handlers.stream()
                                                    .filter(handler->requestUrl.equals(handler.getUrl()))
                                                    .collect(Collectors.toList());
        List<RequestHandler> allMatchItem = null;

        //只有一种匹配结果的情况
        if(urlMatchItem.size()==1){
            RequestHandler one = urlMatchItem.get(0);
            if(one.getRequestMethod().equals(RequestType.DEFAULT)){//如果未声明请求方式，则可匹配所有方式
                return urlMatchItem.get(0);
            }else{//或者必须匹配声明的请求方式
                if(one.getRequestMethod().equals(method)){
                    return one;
                }else {
                    throw new NoSuchHandlerException(requestUrl,method);
                }

            }

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


}
