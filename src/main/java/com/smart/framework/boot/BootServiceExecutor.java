package com.smart.framework.boot;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Lishion on 2017/7/21.
 */
public class BootServiceExecutor implements ServiceExecuter {

    List<Service> services = new LinkedList<>();

    @Override
    public void excuteAll() throws Exception {

         try {
             for(Service service:services){
                 service.start();
             }
         }catch (Exception e){
             //System.err.println(e.getMessage());
             e.printStackTrace();
             throw new Exception("meet error when init the frame");
         }
    }

    @Override
    public void addService(Service service) {
        services.add(service);
    }
}
