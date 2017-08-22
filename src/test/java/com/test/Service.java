package com.test;

import com.smart.framework.annotation.Inject;
import com.smart.framework.core.DataBinder;

/**
 * Created by Lishion on 2017/8/20.
 */
public class Service {
    @Inject
    private Dao dao;

    @Override
    public String toString() {
        return "Service{" +
                "dao=" + dao +
                '}';
    }
}
