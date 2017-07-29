package com.smart.framework.test;

import com.smart.framework.aop.Interceptors;
import com.smart.framework.config.NotFindPage;
import com.smart.framework.config.SmartConfig;
import com.smart.framework.interptor.ControlerPrinter;

import java.util.List;

/**
 * Created by Lishion on 2017/7/23.
 */
public class Config implements SmartConfig {
    @Override
    public void setPackageToScan(List<String> ls) {
        ls.add("com.smart.framework.control");
    }

    @Override
    public void setResource(List<String> ls) {
        ls.add("*.jpg");
    }

    @Override
    public void setGlobalInterceptor(Interceptors i) {

        i.setControlInter(new ControlerPrinter());
    }

    @Override
    public void setNotFindPage(NotFindPage page) {
        page.set("/page/not_find.jsp");
    }
}
