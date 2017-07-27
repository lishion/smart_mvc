package com.smart.framework.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/7/19.
 */
public class Interceptors {

    private Map<Class<? extends Interceptor>,Interceptor> map;
    private Interceptor globalControlInterceptor = null;
    private Interceptor globalServiceInterceptor = null;
    private Class<? extends Interceptor> controlInterceptorClass = null;
    private Class<? extends Interceptor> serviceInterceptorClass = null;
    public Interceptors(){
        map = new ConcurrentHashMap<>();
    }
    public Class<? extends Interceptor> getControlInterceptorsClass() {
        return globalControlInterceptor==null ? null : globalControlInterceptor.getClass();
    }

    public Class<? extends Interceptor> getServiceInterceptorsClass() {
        return  globalServiceInterceptor==null ? null : globalServiceInterceptor.getClass();
    }

    public Map<Class<? extends Interceptor>, Interceptor> getMap() {
        return map;
    }

    public void setMap(Map<Class<? extends Interceptor>, Interceptor> map) {
        this.map = map;
    }

    public Interceptor getGlobalControlInterceptors() {
        return globalControlInterceptor;
    }

    public void setGlobalControlInterceptors(Interceptor globalControlInterceptors) {
       this.globalControlInterceptor = globalControlInterceptors;
    }

    public Interceptor getGlobalServiceInterceptors() {
        return globalServiceInterceptor;
    }

    public void setGlobalServiceInterceptors(Interceptor globalServiceInterceptors) {
        this.globalServiceInterceptor = globalServiceInterceptors;
    }


    public  Interceptor get(Class<? extends Interceptor> clazz){
          return map.get(clazz);
    }

    public  void set(Interceptor interceptor){
        map.put(interceptor.getClass(),interceptor);
    }
}
