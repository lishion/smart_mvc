package com.smart.framework.core;

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
        


    }



    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        ServletRegistration registration = getServletContext().getServletRegistration("default");
        registration.addMapping("*.js");
        registration.addMapping("*.html");
        registration.addMapping("*.css");

        ServletRegistration registration1 = getServletContext().getServletRegistration("jsp");
        registration1.addMapping("*.jsp");

        
        try {

        }catch (Exception e){
            e.printStackTrace();
            throw new ServletException("smart framework init error!!");
        }

    }
}
