package com.smart.framework.core;




import javax.servlet.*;
import javax.servlet.annotation.WebListener;

/**
 * Created by Lishion on 2017/8/27.
 */
@WebListener
public class ServletBootListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        FilterRegistration.Dynamic dynamic = sce.getServletContext().addFilter("bootFilter",new BootFilter());
        dynamic.addMappingForUrlPatterns(null,false,"/");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
