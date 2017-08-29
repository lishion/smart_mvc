package com.smart.framework.core.request;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Lishion on 2017/8/28.
 */
public class RequestWrapper {

    public RequestWrapper(Map<String, String> formDataMap, List<MultipartFile> fileItems, HttpServletRequest request, String requestPath, String requestMethod) {
        this.formDataMap = formDataMap;
        this.fileItems = fileItems;
        this.requestPath = requestPath;
        this.requestMethod = requestMethod;
        this.request = request;
    }

    private Map<String,String> formDataMap ;
    private List<MultipartFile> fileItems ;
    private String requestPath;
    private String requestMethod;
    private HttpServletRequest request;


    public Map<String, String> getFormDataMap() {
        return formDataMap;
    }

    public List<MultipartFile> getFileItems() {
        return fileItems;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
    public String getParam(String s){
        return formDataMap.get(s);
    }


    public MultipartFile[] getFileArray(String name){

         List<MultipartFile> files = getFileList(name);
         MultipartFile[] multipartFiles = new MultipartFile[files.size()];
         files.toArray(multipartFiles);
         return multipartFiles;
    }

    public List<MultipartFile> getFileList(String name){
        return   fileItems.stream()
                .filter(multiPartFile -> multiPartFile.getFieldName().equals(name))
                .collect(Collectors.toList());
    }
}
