package com.smart.framework.aop;

import java.util.HashMap;
import java.util.Map;

public class GlobalInterceptors {
    Map< Class<? extends Interceptor> , Interceptor > onController;
    Map< Class<? extends Interceptor> , Interceptor > onService;

    public GlobalInterceptors() {
        this.onController = new HashMap<>(4);
        this.onService = new HashMap<>(4);
    }
    public void setOnController(Interceptor interceptor){
          onController.put(interceptor.getClass(),interceptor);
    }

    public Map<Class<? extends Interceptor>, Interceptor> getOnService() {
        return onService;
    }

    public Map<Class<? extends Interceptor>, Interceptor> getOnController() {

        return onController;
    }

    public void setOnService(Interceptor interceptor){
          onService.put(interceptor.getClass(),interceptor);

    }
    
}
