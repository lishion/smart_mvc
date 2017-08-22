package com.test;

import com.smart.framework.aop.Interceptors;
import com.smart.framework.config.NotFindPage;
import com.smart.framework.config.SmartConfig;

import java.util.List;

/**
 * Created by Lishion on 2017/8/20.
 */
public class Config implements SmartConfig {
    @Override
    public void setPackageToScan(List<String> ls) {
        ls.add("com.test");
    }

    @Override
    public void setResource(List<String> ls) {

    }

    @Override
    public void setGlobalInterceptor(Interceptors i) {

    }

    @Override
    public void setNotFindPage(NotFindPage page) {

    }
}
