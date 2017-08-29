package com.smart.framework.config;

import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.aop.Interceptor;
import com.smart.framework.config.DevMode;
import com.smart.framework.config.NotFindPage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/8/26.
 */
public class Theme implements ConfigData{

    private NotFindPage notFindPage;
    private DevMode devMode;
    private List<String> assets;
    private ServeTypeItem serverType ;
    private Encoding encoding;
    public NotFindPage getNotFindPage() {
        return notFindPage;
    }

    public DevMode getDevMode() {
        return devMode;
    }

    public List<String> getAssets() {
        return assets;
    }
    public String getEncoding(){
        return encoding.get();
    }
    public ServerType getServerType() {
        return serverType.get();
    }

    public Theme(){
        notFindPage = new NotFindPage();
        devMode = new DevMode();
        assets = new ArrayList<>();
        serverType = new ServeTypeItem();
        encoding = new Encoding();
    }


    @Override
    public void fromConfig(SmartConfig smartConfig) {
        smartConfig.setNotFindPage(notFindPage);
        smartConfig.setDevMode(devMode);
        smartConfig.addAssets(assets);
        smartConfig.setServerType(serverType);
        smartConfig.setEncoding(encoding);
    }
}
