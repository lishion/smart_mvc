package com.smart.framework.core;

import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.config.FrameWorkConfig;
import com.smart.framework.layerc.RequestHandler;
import com.smart.framework.layerc.RequestMap;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Lishion on 2017/7/8.
 */
@WebServlet("/")
public class DispatchServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String contextPath = req.getContextPath();//得到项目路径
        String path = req.getRequestURL().toString().split(contextPath)[1];//得到请求路径
        String methodStr = req.getMethod().toUpperCase();//将请求方法转换为大写
        RequestMap requestMap = (RequestMap) DataPool.need(DataPoolItem.requestMap);
        RequestHandler hander = requestMap.getRequestHander(path,methodStr);//通过路径和方法得到控制器
        FrameWorkConfig config = (FrameWorkConfig) DataPool.need(DataPoolItem.frameworkConfig);
        if(hander==null){
            System.err.println("找不到对应请求:"+path+",方法为:"+methodStr+"的处理器。");
            req.getRequestDispatcher(config.getNotFindPage()).forward(req,resp);
        }
        try {
            RequestHandlerProxy proxy = new RequestHandlerProxy(hander,req,resp);
            proxy.invoke();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        try {
            FrameworkStarter frameworkStarter = new FrameworkStarter(config,getServletContext());
            frameworkStarter.start();
        }catch (Exception e){
            e.printStackTrace();
            throw new ServletException("smart framework init error!!");
        }

    }
}
