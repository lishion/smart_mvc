package com.smart.framework.config;

import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.aop.Interceptor;

import java.util.List;
import java.util.Map;

/**
 * Created by Lishion on 2017/7/23.
 */
public interface SmartConfig {

    void setNotFindPage(NotFindPage page);
    void setDefaultValue(Map<Class<?>,Object> defaultValue);
    void setDevMode(DevMode dev);
    void addAssets(List<String> postFix);
    void addInterceptor(GlobalInterceptors i);


}
