package com.smart.framework.bean;

import com.smart.framework.bean.IBeanFactory;

public class BeanContext {
    private IBeanFactory factory;
    protected void setFactory(IBeanFactory factory){
        this.factory = factory;
    }
    protected IBeanFactory getFactory(){
        return factory;
    }
}
