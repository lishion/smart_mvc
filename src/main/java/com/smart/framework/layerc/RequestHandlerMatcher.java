package com.smart.framework.layerc;

import com.smart.framework.utils.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lishion on 2017/8/20.
 */
public class RequestHandlerMatcher {
    private RequestMap requestMap;
    private HttpServletRequest request;

    public RequestHandlerMatcher(RequestMap requestMap, HttpServletRequest request) {

        this.requestMap = requestMap;
        this.request = request;

    }

    public String getRequestUrl(){

        String contextPath = request.getContextPath();//得到项目路径
        String requestUrl =  request.getRequestURL().toString();
        String path =  requestUrl.substring(requestUrl.indexOf(contextPath)+contextPath.length(),requestUrl.length());

        return path;

    }

    public RequestHandler getHanlder(){
        String method = request.getMethod().toUpperCase();//得到大写形式的请求方法
        String url = getRequestUrl();

        if(requestMap.getRequestHander(url,method)==null){ //首先严格按照请求方法匹配 如果无法找到 则只匹配url
            return requestMap.getRequestHander(url, Constants.EMPTY_STR);
        }
        return requestMap.getRequestHander(url,method);

    }
}
