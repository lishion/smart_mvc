package com.smart.framework.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lishion on 2017/8/20.
 */
public class RequestHandlerInvoker {
    private FrameContext context;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public RequestHandlerInvoker(FrameContext context) {
        this.context = context;
        this.request = context.getRequest();
        this.response = context.getResponse();
    }

    public void invoke(){
        //获取方法
        //绑定参数
        //获取返回值
        //请求转发
    }
}
