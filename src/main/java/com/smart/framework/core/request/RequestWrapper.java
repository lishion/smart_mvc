package com.smart.framework.layerc;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Lishion on 2017/8/28.
 */
public class RequestWrapper {

    public RequestWrapper(Map<String, String> formDataMap, List<MultiPartFile> fileItems, HttpServletRequest request, String requestPath, String requestMethod) {
        this.formDataMap = formDataMap;
        this.fileItems = fileItems;
        this.requestPath = requestPath;
        this.requestMethod = requestMethod;
        this.request = request;
    }

    private Map<String,String> formDataMap ;
    private List<MultiPartFile> fileItems ;
    private String requestPath;
    private String requestMethod;
    private HttpServletRequest request;


    public Map<String, String> getFormDataMap() {
        return formDataMap;
    }

    public List<MultiPartFile> getFileItems() {
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


    public MultiPartFile[] getFileArray(String name){
        return (MultiPartFile[])fileItems.stream()
                .filter(multiPartFile -> multiPartFile.getFileName().equals(name))
                .toArray();
    }

    public List<MultiPartFile> getFileList(String name){
        return   fileItems.stream()
                .filter(multiPartFile -> multiPartFile.getFileName().equals(name))
                .collect(Collectors.toList());
    }
}
