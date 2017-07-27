package com.smart.framework.cache;

import jdk.internal.dynalink.beans.StaticClass;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lishion on 2017/7/23.
 */
public class DataPool {
    private static Map<String,Object> map = new ConcurrentHashMap<>();
    public  static Object need(DataPoolItem item){
        return map.get(item.getName());
    }
    public static  void put(DataPoolItem item,Object o){
        map.put(item.getName(),o);
    }
}
