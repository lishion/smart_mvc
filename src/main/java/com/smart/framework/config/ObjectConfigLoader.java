package com.smart.framework.config;

import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.Interceptors;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lishion on 2017/7/23.
 */
public class ObjectConfigLoader {

    FrameWorkConfig frameWorkConfig = null;
    SmartConfig config = null;

    public ObjectConfigLoader(FrameWorkConfig frameWorkConfig,SmartConfig smartConfig) {
        this.frameWorkConfig = frameWorkConfig;
        this.config = smartConfig;
    }

    public void toConfigData(){
        getPackageToScan();
        getResource();
        getGlobalInterceptor();
        getNotFindPage();
    }

    private void getPackageToScan(){
        List<String> packages = new LinkedList<>();
        config.setPackageToScan(packages);
        frameWorkConfig.setPackageToScan(packages);
    }

    private void  getResource(){
        List<String> resources = new LinkedList<>();
        config.setResource(resources);
        frameWorkConfig.setResource(resources);
    }

    private void getGlobalInterceptor(){
        Interceptors interceptor = frameWorkConfig.getInterceptors();
        config.setGlobalInterceptor(interceptor);
        frameWorkConfig.setInterceptors(interceptor);
    }

    private void getNotFindPage(){
        NotFindPage notFindPage = new NotFindPage();
        config.setNotFindPage(notFindPage);
        frameWorkConfig.setNotFindPage(notFindPage.get());
    }


}
