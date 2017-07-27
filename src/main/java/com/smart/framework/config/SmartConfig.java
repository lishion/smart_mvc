package com.smart.framework.config;

import com.smart.framework.aop.Interceptor;
import com.smart.framework.aop.Interceptors;

import java.util.List;

/**
 * Created by Lishion on 2017/7/23.
 */
public interface SmartConfig {
    void setPackageToScan(List<String> ls);
    void setResource(List<String> ls);
    void setGlobalInterceptor(Interceptors i);
    void setNotFindPage(NotFindPage page);

}
