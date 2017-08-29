package com.smart.framework.layerv;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by Lishion on 2017/7/15.
 */

public class ModelAndView {
    private String url;
    private Map<String,Object> dataMap = null;
    public ModelAndView(String s){
       url = s;
    }
    public ModelAndView(){

    }
    public ModelAndView setView(String url){
        this.url = url;
        return this;
    }
    public ModelAndView putData(String k,Object v){

        if(dataMap == null){
            dataMap = new HashMap<>();
        }
        dataMap.put(k,v);
        return this;
    }

    public String getUrl() {
        return url;
    }
    public Map<String, Object> getDataMap() {
        return dataMap;
    }
    public void visitData(BiConsumer<String,Object> consumer){
        if(dataMap==null) return;
        dataMap.forEach(consumer::accept);
    }
}
