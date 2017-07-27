package com.smart.framework.config;

import com.smart.framework.boot.Service;
import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.utils.ClassUtil;
import com.smart.framework.utils.ReflectionUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Created by Lishion on 2017/7/23.
 */
public class ConfigClassService implements Service {
    ServletContext context = null;
    public ConfigClassService(ServletContext context){
        this.context = context;
    }

    @Override
    public void start() throws Exception {
        //得到用于配置的类文件
       String className =  context.getInitParameter("smartConfig");
       if(className==null) {
           throw new Exception("can't not find config parameter!");
       }
       //加载类
       Class configClass =  ClassUtil.loadClass(className,false);
       Object config = ReflectionUtil.getObject(configClass);
       if(!(config instanceof SmartConfig)){
           throw new Exception("the config object is not the sub class of SmartConfig");
       }
       SmartConfig smartConfig = (SmartConfig)config;

       //获取缓存文件
       FrameWorkConfig frameWorkConfig   =  (FrameWorkConfig)DataPool.need(DataPoolItem.frameworkConfig);

       ObjectConfigLoader configLoader = new ObjectConfigLoader(frameWorkConfig,smartConfig);
       configLoader.toConfigData();
    }
}
