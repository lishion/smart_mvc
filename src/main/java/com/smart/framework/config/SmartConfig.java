package com.smart.framework.config;

import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.layerm.Converter;
import com.smart.framework.layerm.StringConverters;

import java.util.List;

/**
 * Created by Lishion on 2017/7/23.
 */
public interface SmartConfig {

    void setConverter(StringConverters converters);
    void setResource(List<String> ls);
    void setGlobalInterceptor(GlobalInterceptors i);
    void setNotFindPage(NotFindPage page);
    

}
