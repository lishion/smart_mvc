package com.smart.framework.layerc;

/**
 * Created by Lishion on 2017/7/8.
 */
public class RequestRoute {
    private String url;
    private String method;
    public RequestRoute(String url,String method){
        this.url = url;
        this.method  = method;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public int hashCode() {
        return url.hashCode() + method.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if( o==this ){
            return true;
        }
        if( !(o instanceof RequestRoute )){return false;};
        RequestRoute oo = (RequestRoute)o;
        if(oo.getMethod().equals(this.getMethod())&&oo.getUrl().equals(this.getUrl())){
            return true;
        }
        return false;

    }
    @Override
    public String toString() {
        return "RequestRoute{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
