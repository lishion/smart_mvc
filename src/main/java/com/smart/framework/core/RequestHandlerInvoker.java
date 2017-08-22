package com.smart.framework.core;

import com.smart.framework.layerc.RequestHandler;
import com.smart.framework.layerc.RequestHandlerMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lishion on 2017/8/20.
 */
public class RequestHandlerInvoker {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public RequestHandlerInvoker(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    public void invoke() throws Exception {

        RequestHandlerMatcher matcher = new RequestHandlerMatcher(SmartMVC.requestMap,request);

        RequestHandler handler = matcher.getHanlder();

        if(handler==null){
            System.err.println("can't find handle method for url: "+ matcher.getRequestUrl() + " !");
            if(SmartMVC.frameWorkConfig.getNotFindPage()==null){
                System.err.println("can't find not find redirect page!");
                return;
            }
            request.getRequestDispatcher(SmartMVC.frameWorkConfig.getNotFindPage()).forward(request,response);
        }
        RequestHandlerProxy proxy = new RequestHandlerProxy(handler,request,response);
        proxy.invoke();
    }





}
