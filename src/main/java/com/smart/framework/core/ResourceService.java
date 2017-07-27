package com.smart.framework.core;

import com.smart.framework.boot.Service;
import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.config.FrameWorkConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by Lishion on 2017/7/22.
 */
public class ResourceService implements Service {
    ServletConfig config;
    public  ResourceService(ServletConfig config){
     this.config = config;
    }
    @Override
    public void start() {
        ServletContext servletContext = config.getServletContext();
        ServletRegistration registration = servletContext.getServletRegistration("default");
        registration.addMapping("*.js");
        registration.addMapping("*.html");
        registration.addMapping("*.css");
        FrameWorkConfig frameWorkConfig = (FrameWorkConfig) DataPool.need(DataPoolItem.frameworkConfig);
        frameWorkConfig.getResource().forEach(registration::addMapping);
        ServletRegistration registration1 = servletContext.getServletRegistration("jsp");
        registration1.addMapping("*.jsp");
    }
}
