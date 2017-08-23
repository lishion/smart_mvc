package com.test;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;
import com.smart.framework.annotation.Inject;

/**
 * Created by Lishion on 2017/8/20.
 */
@Bean(value = BeanType.Service,singleton = true)
public class Service {
    @Inject
    private Dao dao;

    @Override
    public String toString() {
        return "Service{" +
                "dao=" + dao +
                '}'+this.hashCode();
    }
}
