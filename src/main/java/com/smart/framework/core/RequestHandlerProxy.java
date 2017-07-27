package com.smart.framework.core;

import com.smart.framework.layerc.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Lishion on 2017/7/21.
 */
public class RequestHandlerProxy {
    private RequestHandler handler = null;
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    private Object[] args = null;
    private Object result = null;

    public RequestHandlerProxy(RequestHandler handler, HttpServletRequest request, HttpServletResponse response) {
        this.handler = handler;
        this.request = request;
        this.response = response;
    }

    private void before() throws Exception {
        DataBinder dataBinder = new DataBinder(handler.getParameters(),request);
        try {
            args = dataBinder.bind();
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("bind data for request handler error!!!");
        }
    }

    private void after() throws Exception {
        Dispatcher dispatcher = new Dispatcher(request,response);
        dispatcher.dispatch(result);
    }

    public void invoke() throws Exception {
        before();
        result = handler.handl(args);
        after();
    }
}
