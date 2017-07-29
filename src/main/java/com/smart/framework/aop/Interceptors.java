package com.smart.framework.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/7/19.
 */
public class Interceptors {

    private Map<Class<? extends Interceptor>,Interceptor> map;

    private Map<Class<? extends Interceptor>,Interceptor> controlInter = null;
    private Map<Class<? extends Interceptor>,Interceptor> serviceInter = null;

    public Map<Class<? extends Interceptor>, Interceptor> getControlInter() {
        return controlInter;
    }

    public Map<Class<? extends Interceptor>, Interceptor> getServiceInter() {
        return serviceInter;
    }

    public Interceptors(){

        map = new ConcurrentHashMap<>();
        controlInter = new ConcurrentHashMap<>();
        serviceInter = new ConcurrentHashMap<>();

    }

    public void setControlInter(Interceptor  i){
        if(i==null){
            throw new RuntimeException("the interceptor can not be null!!");
        }
        controlInter.put(i.getClass(),i);
        map.put(i.getClass(),i);
    }

    public void setServiceInter(Interceptor i){
        if(i==null){
            throw new RuntimeException("the interceptor can not be null!!");
        }
        serviceInter.put(i.getClass(),i);
        map.put(i.getClass(),i);
    }

    public  Interceptor get(Class<? extends Interceptor> clazz){
          return map.get(clazz);
    }

    public  void set(Interceptor interceptor){
        map.put(interceptor.getClass(),interceptor);
    }
}
