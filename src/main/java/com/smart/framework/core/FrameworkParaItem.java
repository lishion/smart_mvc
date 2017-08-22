package com.smart.framework.core;

import com.smart.framework.layerv.ModelAndView;
import com.smart.framework.utils.ReflectionKit;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lishion on 2017/8/20.
 */
public class FrameworkParaItem {

    private List< Class<?> > itmeList = new ArrayList<>();

    public void FrameworkParaItem(){

    }

    private void init(){
        itmeList.add(HttpServletRequest.class);
        itmeList.add(ModelAndView.class);
    }

    public boolean isParaItem(Class<?> clazz){
        return itmeList.contains(clazz);
    }

    public Object getInstance(Class<?> clazz) throws Exception {
        return ReflectionKit.getObject(clazz);
    }

}
