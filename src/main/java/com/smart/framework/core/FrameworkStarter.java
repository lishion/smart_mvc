package com.smart.framework.core;

import com.smart.framework.aop.InvocationChain;
import com.smart.framework.bean.BeanService;
import com.smart.framework.bean.BeansContainer;
import com.smart.framework.bean.ClassService;
import com.smart.framework.bean.IOCService;
import com.smart.framework.boot.BootServiceExecutor;
import com.smart.framework.boot.Service;
import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.config.ConfigClassService;
import com.smart.framework.config.FrameWorkConfig;
import com.smart.framework.layerc.RequestMap;
import com.smart.framework.layerc.RequestService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lishion on 2017/7/23.
 */
public class FrameworkStarter {

    ServletConfig config = null;
    ServletContext context = null;
    public FrameworkStarter(ServletConfig config,ServletContext context){
        this.config = config;
        this.context = context;
    }

    private void cacheInti(){

        InvocationChain invocationChain = new InvocationChain();
        FrameWorkConfig frameWorkConfig = new FrameWorkConfig();
        BeansContainer beansContainer = new BeansContainer();
        Set<Class<?>> beanClass = new HashSet<>();
        RequestMap requestMap = new RequestMap();

        DataPool.put(DataPoolItem.invocationChain,invocationChain);
        DataPool.put(DataPoolItem.beanContainer,beansContainer);
        DataPool.put(DataPoolItem.frameworkConfig,frameWorkConfig);
        DataPool.put(DataPoolItem.beanClass,beanClass);
        DataPool.put(DataPoolItem.requestMap,requestMap);

    }

    private void serviceInit() throws Exception{
        BootServiceExecutor bootServiceExecutor = new BootServiceExecutor();

        bootServiceExecutor.addService(new ConfigClassService(context));
        bootServiceExecutor.addService(new ResourceService(config));
        bootServiceExecutor.addService(new ClassService());
        bootServiceExecutor.addService(new BeanService());
        bootServiceExecutor.addService(new IOCService());
        bootServiceExecutor.addService(new RequestService());
        bootServiceExecutor.excuteAll();
    }

    public void start() throws Exception{
        cacheInti();
        serviceInit();
    }



}
