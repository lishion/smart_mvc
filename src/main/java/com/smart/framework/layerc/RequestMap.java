package com.smart.framework.layerc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lishion on 2017/7/21.
 */
public class RequestMap {

    private  Map<RequestRoute,RequestHandler> requestMap = new HashMap<>();

    public  Map<RequestRoute, RequestHandler> getRequestMap() {
        return requestMap;
    }

    public  RequestHandler getRequestHander(String url, String method){

        if(url==null || method==null) return null;
        RequestRoute route = new RequestRoute(url,method);
        for(RequestRoute requestRoute:requestMap.keySet()){
            if(requestRoute.equals(route)){
                return requestMap.get(requestRoute);
            }
        }
        return null;
    }

}
