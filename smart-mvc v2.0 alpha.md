[TOC]  
## 快速开始
一个典型的smart-mvc控制器代码如下:
```java
@Bean(value = BeanType.Controller)
public class Action {
    @Inject
    Service service;

    @Route("/index")
    public ModelAndView aaa(ModelAndView mv){
        mv.putData("key","hello world");
        return mv.setView("page/index.jsp");
    }
    
}
```
这段代码的作用是匹配路径为 **/index** 的请求，返回数据并跳转到页面**page/index.jsp**。
在浏览器输入**localhost/index**，得到效果如下:  

![image](http://www.z4a.net/images/2017/08/29/helloworld.png)

而要完成以上功能，你不需要一行配置文件，smart-mvc大部分功能是基于注解和COC的，在后续的介绍中你也能体会到。
## 深入smart-mvc
### 项目配置
开发一个标准的spring项目，一般需要对**包扫描，编码方式，静态资源**等内容进行配置，使用起来非常繁琐。而在smart-mvc中，你只需要实现**SmartConfig**这个接口便可以对项目进行配置，如下:
```java
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
    
    @Override //设置web容器 默认tomcat
    public void setServerType(ServeTypeItem type) {
        type.set(ServerType.TOMCAT);
    }
    
    @Override //设置前端数据编码方式
    public void setEncoding(Encoding e) {
        e.set("utf-8");
    }
    
}
```
可以看出smart已经对常用的设置进行了一些默认配置，因此在不使用设置文件的时候也可以进行开发。    
如果你需要自己的设置，请实现**SmartConfig**接口，并将实现类放入**classpath**。当然其他的位置也可以，但是将类放在**classpath**可以保证smart-mvc以最快速度找到配置文件并开始项目的初始化。   
需要注意的一点事，smart并不支持多个配置文件，如果你定义了多个配置，smart在找到第一个配置类后，剩余的配置类将被忽略。
### bean
bean的概念在smart中与spring中相似。使用 **@Bean(BEAN-TYPE)** 将表示该类为一个bean。其中BEAN-TYPE可以有以下几种取值:    

`Controller   Service    Model    Component`

如我要将一个类声明为控制器方式如下:
```java
@Bean(value = BeanType.Controller)
public class Action {
   

}
```
在smart中对bean有两种管理方式:**单例和原型**,通过 **@Bean**  注解中的**singleton**进行配置。如:   

`@Bean(value = BeanType.Controller,singleton = false)`  

对于单例模式，smart会在项目启动阶段生成其实例，并对于每一个请求都使用该实例。   
对于原型模式，smart不会再启动阶段对其实例进行缓存，并对每一个请求都生成新的实例。  
smart建议你使用单例模式进行开发，因为这样会省去大量实例生成/回收的时间。同时，使用单例模式进行开发，请尽量保证你设计的类是一个**无状态，即线程安全的类**。因此你不能在你的控制器，或是业务层使用成员变量。如果实在有这样的需求，建议你使用**ThreadLocal**进行隔离。

*在smart中，无需对包扫描进行配置，spring会自动扫描有@Bean注解的类*

### 控制器
控制器无疑是一个mvc框架中最重要的部分，控制器主要有**路由，数据绑定，数据渲染**这三部分内容。
#### 路由(请求路径)
smart中使用 **@Route** 对路由进行配置，**@Routh** 可以注解在类和方法上。因此一个方法的完整路由为:   
 >ip+端口+context path + 类路由 + 方法路由   
 
其中context path在eclipse中默认是项目路径，idea中可以自己配置。  
同时,@Route支持对方法进行匹配。如下:
```java
@Route(value = "/index",method = RequestType.GET)//只匹配/index，GET方法

@Route(value = "/index",method = RequestType.POST)//只匹配/index，POST方法

@Route(value = "/index")//同时匹配GET和POST方法(如果有更精确的匹配，执行更精确的匹配)
```
#### 数据绑定
以下类型可以进行绑定:
* 内置类型如:String、int、Double等
* 有@Model注解的用户自定义的类
* HttpServletRequest
* ModelAndView(只创建空对象供用户使用)
* MultipartFile(支持文件上传)

##### 简单类型绑定
简单类型需要使用 **@Var("name")** 进行注解，其中name表示前端回传的数据的 **key**。例如:
```java
 //url:localhost/test?query="hello world"
 @Route(value = "/test")
    public ModelAndView test(@Var("query") String s){
       
      System.out.print(s); 
    }
 //out:hello world
```
当然，使用post方法提交的数据任然能够绑定。
##### 自定义类型绑定
如果需要对自定义类型进行，请确保它有@Model注解。复杂类型数据绑定如下:
```java
    //Class User:
        //private String name;
        //private String sex;
        //private int age;
    
    //post-data:{
      //"name": "lishon",
      //"sex": "男",
      //"age": 15
    }
      
    //Method：
    @Route(value = "/test")
    public User test(User s){
        return s;
    }
    
    //out:{
      //"name": "lishon",
      //"sex": "男",
      //"age": 15
    }

```
当然，在某些时候你需要一次绑定两个对象如**User,Student**，并且他们都有**id**这个属性，如何确定绑定正确呢?这时候你需要再次使用 **@Var("")**。如:
```java
    //Class User:
        //private String name;
        //private String sex;
        //private int age;
    
    //post-data:{
      //"user.name": "lishon",//注意 这里加入了前缀user. !!!
      //"user.sex": "男",
      //"user.age": 15
    }
      
    //Method：
    @Route(value = "/test")
    public User test(@Var("user") User s){
        return s;
    }
    
    //out:{
      //"name": "lishon",
      //"sex": "男",
      //"age": 15
    }

```
因此，如果一次需要绑定不同的对象，在有属性名冲突的情况下，你可以使用@Var表示前缀以避免冲突(简单类型不能使用此功能)。
##### HttpServletRequest
如果需要使用**HttpServletRequest**,请直接在控制器对应的方法中添加该参数即可:
```java
      @Route(value = "/test")
    public void test(HttpServletRequest req){
         
    }
```
##### 文件上传
smart使用**MultipartFile**对文件进行上传，如下:
```java
@Route(value = "/file")
    public void file(@FileVar("smart-file") MultipartFile files){

         
    }
```
注意，@FileVar("file")注解是无法省略的。"smart-file"对应前端:
```html
    <input type="file" name="smart-file">
```
同时，smart也支持多文件上传:
```java
@Route(value = "/file")
    public void file(@FileVar("smart-file") MultipartFile[] files){

         
    }
```
在接受的时候，你可以选择使用**数组或List**。同时，保证前端对应的名字相同:
```html

    <input type="file" name="smart-file">
    <input type="file" name="smart-file">
    <input type="file" name="smart-file">
    ...
    <input type="file" name="smart-file">
```
在得到文件后，你可以使用 **writeTo()** 保存文件，如:
```java
@Route(value = "/file")
    public void file(@FileVar("file") MultipartFile files){
        File file =new File("hello world.text");
        try {
            files.writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
```

##### restful风格的数据绑定(未完成)
##### 前端json数据绑定(未完成)
##### 默认值(完善中):
在数据绑定的过程中，如果某项值为null，你可以针对某一个类型配置默认值。如配置:
>String.class : "null"   

那么在绑定的过程中，某个类型为String的变量从前端接受到的值为null,smart将会自动将其转化为字符串  "null"。但是smart并不推荐你使用此功能，从某种角度来说，该功能可能导致潜在的错误白忽略。
#### 数据渲染
smart可以使用**ModelAndView**进行数据返回。如:
```java
 @Route(value = "/index",method = RequestType.GET)
    public ModelAndView aaa(ModelAndView mv){
        mv.putData("key","hello world");
        return mv.setView("page/index.jsp");
    }
```
除此之外，所有的不是**ModelAndViw**的对象都会被解析为json格式，并以application/json方式返回到前端。    
目前还不支持 控制器-控制器 的跳转以及页面重定向。

### IOC
使用IOC也非常简单，只需要在变量上注解@Inject，该变量就会被自动注入。目前smart只支持按类型注入。
### AOP
spring的AOP非常繁琐，使用起来非常不友善。smart对AOP进行了简化，用户只需要关注:  
* 拦截器  
* @Before  
* @Clear/@ClearAll  

这几个内容。其中拦截器表示对目标方法进行增强，@Befor表示拦击器调用的位置，@Clear/@ClearAll表示对上层拦截器进行清除。自定义一个拦截器需要实现**Interceptor**接口:
```java
public class Inter implements Interceptor {

    @Override
    public void intercept(Invocation iv) throws Throwable {
        System.out.println("la");//目标方法执行前
        iv.invoke();//调用剩余的拦截器
        System.out.println("da");//目标方法执行后
    }
}
```
该拦截器表示在方法运行前打印 la,方法结束后打印 da。
如果需要使用该拦截器，只需要在类/方法上使用@Befor注解:
```java
 @Route(value = "/test")
    @Before(Inter.class)
    public User test(User s){

        return s;
    }
```
那么该方法执行前就会先调用拦截器。拦截器有以下几个等级:
> 全局 > 类 > 方法

全局拦截器分为 **控制层和业务层**，全局拦截器将作用所有controller和service。类拦截器将作用于类中的所有方法。    
如果需要对某一层的拦截器进行清除，可以使用@Clear/@ClearAll注解。其中@Clear表示清除指定的拦截器，@ClearAll表示清除上层所有的拦截器。如:    
在类上面注解@ClearAll可以清除全局拦截器，在方法上注解@ClearAll可以清除全局和类层面的拦截器。
