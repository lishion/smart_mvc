package com.smart.framework.core;

import com.smart.framework.config.FrameworkConfig;
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
        String requestUrl =  req.getRequestURL().toString();
        String path =  requestUrl.substring(requestUrl.indexOf(contextPath)+contextPath.length(),requestUrl.length());


        String methodStr = req.getMethod().toUpperCase();//将请求方法转换为大写

        RequestMap requestMap = SmartMVC.requestMap;
        RequestHandler handler = requestMap.getRequestHander(path,methodStr);//通过路径和方法得到控制器
        FrameworkConfig config = SmartMVC.frameWorkConfig;

        if(handler==null){
            System.err.println("找不到对应请求:"+path+",方法为:"+methodStr+"的处理器。");
            if(config.getNotFindPage()==null){
                System.err.println("找不到失败重定向页面!");
                return;
            }
            req.getRequestDispatcher(config.getNotFindPage()).forward(req,resp);
        }
        try {
            RequestHandlerProxy proxy = new RequestHandlerProxy(handler,req,resp);
            proxy.invoke();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        ServletRegistration registration = getServletContext().getServletRegistration("default");
        registration.addMapping("*.js");
        registration.addMapping("*.html");
        registration.addMapping("*.css");
        SmartMVC.frameWorkConfig.getResource().forEach(registration::addMapping);
        ServletRegistration registration1 = getServletContext().getServletRegistration("jsp");
        registration1.addMapping("*.jsp");
        try {
            SmartMVC frameworkStarter = new SmartMVC(config,getServletContext());
            frameworkStarter.start();
        }catch (Exception e){
            e.printStackTrace();
            throw new ServletException("smart framework init error!!");
        }

    }
}
