package com.smart.framework.bean;

import com.smart.framework.boot.Service;
import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.config.FrameWorkConfig;
import com.smart.framework.utils.ClassUtil;

import java.util.Set;

/**
 * Created by Lishion on 2017/7/23.
 */
@SuppressWarnings("unchecked")
public class ClassService implements Service{
    @Override
    public void start() throws Exception {
        FrameWorkConfig frameWorkConfig = (FrameWorkConfig) DataPool.need(DataPoolItem.frameworkConfig);
        Set<Class<?>> classSet = null;
        try {
            classSet = ClassUtil.getClassAt(frameWorkConfig.getPackageToScan());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("start class service error!!");
        }
        Set<Class<?>> classSetCache = ( Set<Class<?>> )DataPool.need(DataPoolItem.beanClass);
        classSetCache.addAll(classSet);
    }
}
