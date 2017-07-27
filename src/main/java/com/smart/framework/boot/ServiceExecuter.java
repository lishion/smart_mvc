package com.smart.framework.boot;

/**
 * Created by Lishion on 2017/7/21.
 */
public interface ServiceExecuter {
    void excuteAll() throws Exception;
    void addService(Service service);
}
