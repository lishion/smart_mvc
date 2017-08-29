package com.test;

import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.config.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Lishion on 2017/8/20.
 */
public class MyConfig  implements SmartConfig {


    @Override
    public void setNotFindPage(NotFindPage page) {
        page.set("page/not_find.html");
    }

    @Override
    public void setDefaultValue(Map<Class<?>, Object> defaultValue) {

    }

    @Override
    public void setDevMode(DevMode dev) {
        dev.set(true);
    }

    @Override
    public void addAssets(List<String> postFix) {

    }

    @Override
    public void addInterceptor(GlobalInterceptors i) {
        i.setOnController(new Inter());
    }

    @Override
    public void setServerType(ServeTypeItem type) {
        type.set(ServerType.JETTY);
    }


}
