import com.smart.framework.aop.GlobalInterceptors;
import com.smart.framework.config.*;
import com.test.Inter;

import java.util.List;
import java.util.Map;

/**
 * Created by Lishion on 2017/8/20.
 */
public class MyConfig  implements SmartConfig {

    @Override //配置404重定向页面
    public void setNotFindPage(NotFindPage page) {
        page.set("page/not_find.html");
    }
    @Override //设置数据绑定默认值
    public void setDefaultValue(Map<Class<?>, Object> defaultValue) {}
    @Override //设置是否为开发模式 默认是
    public void setDevMode(DevMode dev) {
        dev.set(true);
    }
    @Override //设置静态资源后缀 默认已经添加.css .js .html
    public void addAssets(List<String> postFix) {postFix.add(".jpg");}
    @Override //设置全局拦截器
    public void addInterceptor(GlobalInterceptors i) {
        i.setOnController(new Inter());
    }
    @Override //设置web容器 tomcat
    public void setServerType(ServeTypeItem type) {
        type.set(ServerType.TOMCAT);
    }
    @Override //设置前端数据编码方式
    public void setEncoding(Encoding e) {
        e.set("utf-8");
    }

}
