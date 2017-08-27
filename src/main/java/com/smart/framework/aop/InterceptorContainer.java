package com.smart.framework.aop;

import com.smart.framework.config.ConfigData;
import com.smart.framework.config.SmartConfig;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/8/26.
 */
public class InterceptorContainer implements ConfigData {
    private Map<Method,Interceptor[]> interceptorChainCache ;
    private Map< Class<? extends Interceptor> , Interceptor > interceptorInstanceCache ;
    private GlobalInterceptors globalInterceptors;

    public InterceptorContainer() {

        this.interceptorChainCache = new ConcurrentHashMap<>();
        this.interceptorInstanceCache = new ConcurrentHashMap<>();
        this.globalInterceptors = new GlobalInterceptors();
    }
    public void cacheInterceptorChain(Method method,Interceptor[] interceptors){
        interceptorChainCache.put(method,interceptors);
    }
    public void cacheIntercepotorInstance(Class<? extends Interceptor> clazz,Interceptor interceptor){
        interceptorInstanceCache.put(clazz,interceptor);
    }
    public Interceptor[] getInterceptorChain(Method method){
        return interceptorChainCache.get(method);
    }

    public Map<Method, Interceptor[]> getInterceptorChainCache() {
        return interceptorChainCache;
    }

    public Map<Class<? extends Interceptor>, Interceptor> getInterceptorInstanceCache() {

        return interceptorInstanceCache;
    }

    public Interceptor getInterceptor(Class<? extends Interceptor> clazz){
        return interceptorInstanceCache.get(clazz);
    }

    public GlobalInterceptors getGlobalInterceptors() {
        return globalInterceptors;
    }

    @Override
    public void fromConfig(SmartConfig smartConfig) {
        smartConfig.addInterceptor(globalInterceptors);
        interceptorInstanceCache.putAll(globalInterceptors.getOnController());
        interceptorInstanceCache.putAll(globalInterceptors.getOnService());
    }
}
