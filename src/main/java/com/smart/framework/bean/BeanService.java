package com.smart.framework.bean;

import com.smart.framework.boot.Service;
import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;

import java.util.Set;

/**
 * Created by Lishion on 2017/7/23.
 */
public class BeanService implements Service {

    @Override
    public void start(){
        BeansContainer beansContainer = (BeansContainer) DataPool.need(DataPoolItem.beanContainer);
        Set<Class<?>> beanClazz = (Set<Class<?>>)DataPool.need(DataPoolItem.beanClass);
        beansContainer.fromClassess(beanClazz);
    }
}
