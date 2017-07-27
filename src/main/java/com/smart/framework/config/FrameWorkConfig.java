package com.smart.framework.config;

import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.Interceptors;

import java.util.*;

/**
 * Created by Lishion on 2017/7/10.
 */
public class FrameWorkConfig {


    private List<String> packageToScan = null;
    private List<String> resource = null;
    private Interceptors interceptors = new Interceptors();
    private String notFindPage = null;

    public String getNotFindPage() {
        return notFindPage;
    }

    public void setNotFindPage(String notFindPage) {
        this.notFindPage = notFindPage;
    }

    public Interceptors getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(Interceptors interceptors) {
        this.interceptors = interceptors;
    }

    public void setResource(List<String> resource) {
        this.resource = resource;
    }

    public void setPackageToScan(List<String> packageToScan) {
        this.packageToScan = packageToScan;
    }

    public List<String> getResource() {
        return resource;
    }

    public List<String> getPackageToScan() {
        return packageToScan;
    }

    public FrameWorkConfig(){

    }

    @Override
    public String toString() {
        return "FrameWorkConfig{" +
                "packageToScan=" + packageToScan +
                ", resource=" + resource +
                ", interceptors=" + interceptors +
                ", notFindPage='" + notFindPage + '\'' +
                '}';
    }
}
