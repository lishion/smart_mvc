# smart mvc 手册

[TOC]

### 开始使用
#### 准备
在开始使用smart构建你的项目之前，需要引入smart及其依赖包:
>asm-5.2.jar
>cglib-3.2.5.jar
>jackson-annotations-2.4.0.jar
>jackson-core-2.4.4.jar
>jackson-databind-2.4.4.jar
>smart-framework-1.0.0.jar

在web.xml中配置smart对应的请求转发器

    <servlet>
        <servlet-name>smart</servlet-name>
        <servlet-class>com.smart.framework.core.DispatchServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>smart</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

#### 用smart搭建一个hello world
smart采用引导式配置，通过实现SmartConfig进行配置。例如，如果需要配置包扫描：
``` java
public class Config implements SmartConfig {
    @Override
    public void setPackageToScan(List<String> list) {
        list.add("com.test");
    }

    @Override
    public void setResource(List<String> list) {    }

    @Override
    public void setGlobalInterceptor(Interceptors interceptors) {}

    @Override
    public void setNotFindPage(NotFindPage notFindPage) {}
}
```
并将该类添加到web.xml作为参数:

``` xml
<context-param>
        <param-name>smartConfig</param-name>
        <param-value>com.test.Config</param-value>
</context-param>
```
对于一个mvc框架，最核心部分无疑是控制器，在smart中使用注解将一个类声明为控制器:

``` java 
@Bean(BeanType.Controler)
public class Action
```
同时，使用@Route
```java
@Route(value = "/test",method = RequestType.GET)
public ModelAndView test()
```

在smart中，可以使用ModelAndView返回数据并跳转到指定的页面:
```java
public ModelAndView test(){
        ModelAndView mv = new ModelAndView("index.jsp");
        mv.putData("Title","first page");
        mv.putData("END","hello world");
        return mv;
    }
```

其中以key-value形式添加的键值对可以在jsp页面以el表达式显示，对应的jsp页面代码为:

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>${Title}</title>
  </head>
  <body>
  ${END}
  </body>
</html>
```

整个控制器的代码为:

```java
@Bean(BeanType.Controler)
public class Action {

    @Route(value = "/test",method = RequestType.GET)
    public ModelAndView test(){
        ModelAndView mv = new ModelAndView("index.jsp");
        mv.putData("Title","first page");
        mv.putData("END","hello world");
        return mv;
    }
}
```

进行项目路径+/test的请求即可到达该控制器对应的方法。

同时，smart也支持对前端数返回的数据进行自动包装，只需要将包含这些数据的类使用@Bean()将该类注解为模型:

```java
@Bean(BeanType.Model)
public class Model
```

同时将该类作为参数放入到对应的控制器中：

```java
public ModelAndView test(Model model)
```

便可直接在方法中使用该数据。同时，如果想使用HttpServletRequest对象，也可以直接将其作为方法的参数。

如果需要绑定简单类型而非自定义的类，需要使用Var()对参数进行注解，例如:

```java
public ModelAndView test(Model model,@Var("x")String s)
```

其中的参数x表示前端传回的key-value类型数据的key。

### IOC

smart也支持依赖注入，用户无需自己新建对象，可以将对象交给smart管理。IOC同样使用注解完成:

```java
@Inject()
Service service = null;
```

smart会寻找与其类型相同的实例进行注入。同样可以指定类进行注入:

```java
@Inject(Service.class)
Service service = null;
```

**注:只能注入用户自定义的类型**

### AOP

在smart中使用AOP非常简单，无需切面，切点等内容。只需关注**拦截器**，@Before这两个内容。

一个简单的拦截器定义如下:

```java
public class ControlInter implements Interceptor {
    @Override
    public void intercept(Invocation invocation) throws Throwable {
        System.out.println("before!");
        invocation.invoke();//调用剩余的拦截器,本条语句不能缺少。
        System.out.println("after!");
    }
}
```

该拦截器作用是在所拦截的方法前后输出before 和 after。

如需使用该拦截器，只需要使用@before进行注解，例如:

```java
@Before(ControlInter.class)
```

该注解可以用在类上或方法上，使用在方法上则表示该方法被拦截，使用在类上表示该类中所有方法被拦截。同一个类\方法可以使用多个拦截器。

同时，用户可以在设置类中添加全局层面的拦截器。全局控制器拦截器将会拦截所有的控制器，全局服务层拦截器可以拦截所有服务层的类，如:

```java
@Override
public void setGlobalInterceptor(Interceptors interceptors) {
  interceptors.setControlInter(new ControlInter());
  interceptors.setServiceInter(new ControlInter());
}
```

同时，可以使用@Clear(xxx.class)清除**上层**的指定拦截器，或使用@ClearAll()清除所有**上层**拦截器。拦截器的层次为:

> 全局>类>方法

因此，如果在方法上使用@ClearAll()可以清除所有的**类，全局**层面的拦截器。

 