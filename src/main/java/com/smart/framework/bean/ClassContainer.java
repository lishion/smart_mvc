package com.smart.framework.bean;

import com.smart.framework.config.FrameworkConfig;
import com.smart.framework.utils.ClassKit;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lishion on 2017/7/28.
 */
public class ClassContainer  {

    private Set<Class<?>> beanClass = new HashSet<>();

    public Set<Class<?>> getBeanClass() {
        return beanClass;
    }

    public void load(FrameworkConfig config) throws Exception {

        try {
             beanClass.addAll(ClassKit.getClassAt(config.getPackageToScan()));
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("load class error!!!");
        }

    }
}
