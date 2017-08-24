package com.test;

import com.smart.framework.annotation.Config;
import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.config.NotFindPage;
import com.smart.framework.config.SmartConfig;
import com.smart.framework.layerm.StringConverters;

import java.util.List;

/**
 * Created by Lishion on 2017/8/20.
 */
@Config
public class MyConfig  implements SmartConfig {


    @Override
    public void setConverter(StringConverters converters) {
        
    }

    @Override
    public void setResource(List<String> ls) {

    }

    @Override
    public void setGlobalInterceptor(GlobalInterceptors i) {

    }

    @Override
    public void setNotFindPage(NotFindPage page) {

    }
}
