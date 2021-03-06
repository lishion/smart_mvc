package com.smart.framework.core.request;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lishion on 2017/8/28.
 */
public class DefaultRequestWrapperResolver implements RequestWrapperResolver {


     protected Map<String,String> paramMap = new HashMap<>();
     protected Map<String,String> pathMap = new HashMap<>();
     protected List<MultipartFile> fileList = new LinkedList<>();
     protected String path ;
     protected String method ;
     protected HttpServletRequest request ;

     //you have to call super.resolve() when you override the method!!!
     @Override
     public RequestWrapperResolver resolve(HttpServletRequest request) throws Exception {
          this.path = getRequestUrl(request);
          this.method = request.getMethod();
          this.request = request;
          return this;
     }

     @Override
     public final RequestWrapper build(){
          return new RequestWrapper(paramMap,pathMap,fileList,request,path,method);
     }

     private String getRequestUrl(HttpServletRequest request){
          String host = request.getHeader("Host");
          String contextPath = request.getContextPath();
          String prefix = host + contextPath;
          String requestUrl =  request.getRequestURL().toString();
          return requestUrl.substring(requestUrl.indexOf(prefix)+prefix.length(),requestUrl.length());
     }

     private void resolvePathVariable(){

     }

}
