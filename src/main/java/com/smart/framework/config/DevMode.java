package com.smart.framework.config;

import com.test.Inter;

/**
 * Created by Lishion on 2017/8/26.
 */
public class DevMode implements ConfigElement<Boolean> {
    private boolean isDev = true;
    @Override
    public void set(Boolean aBoolean) {
        isDev = aBoolean;
    }

    @Override
    public Boolean get() {
        return isDev;
    }
}
