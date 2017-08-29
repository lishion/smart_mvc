package com.smart.framework.config;

/**
 * Created by Lishion on 2017/8/27.
 */
public class ServeTypeItem implements ConfigElement<ServerType> {
    private ServerType serverType;
    @Override
    public void set(ServerType serverType) {
        this.serverType = serverType;
    }

    @Override
    public ServerType get() {
        return serverType;
    }
}
