package com.smart.framework.config;

import sun.dc.pr.PRError;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lishion on 2017/8/26.
 */
public class ConfigManger  {
    private SmartConfig smartConfig = null;
    public ConfigManger(SmartConfig smartConfig){
        this.smartConfig = smartConfig;
    }
    private List<ConfigData> datas = new LinkedList<>();
    public ConfigManger register(ConfigData data){
        datas.add(data);
        return this;
    }

    public void config() {
        datas.forEach(data ->data.fromConfig(smartConfig));

    }
}
