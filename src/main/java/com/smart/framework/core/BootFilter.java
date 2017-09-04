package com.smart.framework.core;

import com.smart.framework.config.ServerType;
import com.smart.framework.config.Theme;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Lishion on 2017/9/2.
 */
public class BootFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ServletContext context = filterConfig.getServletContext();
        FrameContext frameContext = FrameContext.getInstance();
        frameContext.run();
        ServletRegistration defaultServlet = context.getServletRegistration("default");

        ServletRegistration jspServlet = context.getServletRegistration("jsp");

        defaultServlet.addMapping("*.ico");
        defaultServlet.addMapping("*.js");
        defaultServlet.addMapping("*.html");
        defaultServlet.addMapping("*.css");
        jspServlet.addMapping("*.jsp");
        Theme theme = frameContext.getTheme();
        theme.getAssets().forEach(s -> defaultServlet.addMapping("*"+s));

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
