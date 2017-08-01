package com.smart.framework.core;

import com.smart.framework.aop.Interceptors;
import com.smart.framework.config.NotFindPage;
import com.smart.framework.config.SmartConfig;

import java.util.List;

/**
 * Created by Lishion on 2017/7/29.
 */
public class Config implements SmartConfig {
    @Override
    public void setPackageToScan(List<String> ls) {

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
