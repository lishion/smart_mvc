package com.test;

import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.aop.Interceptor;
import com.smart.framework.config.DevMode;
import com.smart.framework.config.NotFindPage;
import com.smart.framework.config.SmartConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by Lishion on 2017/8/20.
 */
public class MyConfig  implements SmartConfig {


    @Override
    public void setNotFindPage(NotFindPage page) {

    }

    @Override
    public void setDefaultValue(Map<Class<?>, Object> defaultValue) {

    }

    @Override
    public void setDevMode(DevMode dev) {

    }

    @Override
    public void addAssets(List<String> postFix) {

    }

    @Override
    public void addInterceptor(GlobalInterceptors i) {

    }
}
