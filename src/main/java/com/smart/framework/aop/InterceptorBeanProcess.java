package com.smart.framework.aop;

import com.smart.framework.bean.BeanProcessPreCallback;
import com.smart.framework.bean.BeanWrapper;
import com.smart.framework.bean.IBeanFactory;
import net.sf.cglib.proxy.Enhancer;

public class InterceptorBeanProcess implements BeanProcessPreCallback {

    IBeanFactory beanFactory = null;

    public InterceptorBeanProcess(IBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }


    @Override
    public void process(BeanWrapper beanWrapper)  {

        

    }
}
