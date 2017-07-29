package com.smart.framework.config;

import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.Interceptors;

import java.util.*;

/**
 * Created by Lishion on 2017/7/10.
 */
public class FrameworkConfig {


    private List<String> packageToScan = new LinkedList<>();
    private List<String> resource = new LinkedList<>();
    private Interceptors interceptors = new Interceptors();
    private NotFindPage notFindPage  = new NotFindPage();

    public void load(SmartConfig config){
        config.setNotFindPage(notFindPage);
        config.setResource(resource);
        config.setGlobalInterceptor(interceptors);
        config.setPackageToScan(packageToScan);
    }

    public Interceptors getInterceptors() {
        return interceptors;
    }
    public List<String> getResource() {
        return resource;
    }
    public List<String> getPackageToScan() {
        return packageToScan;
    }

    public String getNotFindPage() {
        return notFindPage.get();
    }

    public FrameworkConfig(){
    }

    @Override
    public String toString() {
        return "FrameworkConfig{" +
                "packageToScan=" + packageToScan +
                ", resource=" + resource +
                ", interceptors=" + interceptors +
                ", notFindPage='" + notFindPage + '\'' +
                '}';
    }
}
