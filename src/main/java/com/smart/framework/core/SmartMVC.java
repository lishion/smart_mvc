package com.smart.framework.core;

import com.smart.framework.bean.BeansContainer;
import com.smart.framework.bean.ClassContainer;
import com.smart.framework.layerm.ConverterContainer;
import com.smart.framework.utils.IOCKit;
import com.smart.framework.config.FrameworkConfig;
import com.smart.framework.config.SmartConfig;
import com.smart.framework.layerc.RequestMap;
import com.smart.framework.utils.ClassKit;
import com.smart.framework.utils.ReflectionKit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Created by Lishion on 2017/7/23.
 */
public class SmartMVC {

    ServletConfig config = null;
    ServletContext context = null;
    public SmartMVC(ServletConfig config, ServletContext context){
        this.config = config;
        this.context = context;
    }


    public static final FrameworkConfig frameWorkConfig = new FrameworkConfig();
    public static final BeansContainer beansContainer = new BeansContainer();
    public static final ClassContainer classContainer = new ClassContainer();
    public static final RequestMap requestMap = new RequestMap();
    public static final ConverterContainer converterContainer = new ConverterContainer();

    /**
     * 从web.xml中得到配置类
     * @return 配置类实例
     * @throws Exception
     */
    public SmartConfig getConfig() throws Exception {
        //获取配置类
        String className =  context.getInitParameter("smartConfig");
        if(className==null) {
            throw new Exception("can't not find config parameter!");
        }
        //加载类
        Class configClass =  ClassKit.loadClass(className,false);
        Object config = ReflectionKit.getObject(configClass);
        if(!(config instanceof SmartConfig)){
            throw new Exception("the config object is not the sub class of SmartConfig");
        }
        SmartConfig smartConfig = (SmartConfig)config;
        return smartConfig;
    }

    //框架初始化
    public void start() throws Exception{

        frameWorkConfig.load(getConfig());//加载配置
        classContainer.load(frameWorkConfig);//从配置中加载class容器


        beansContainer.load(classContainer);//从class容器中加载bean容器
        IOCKit.inject(beansContainer);//依赖注入
        requestMap.load(beansContainer);//从bean容器中加载控制器映射
    }
    

}
