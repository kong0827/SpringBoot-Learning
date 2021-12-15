## @Value无法注入静态属性

在工作中对于一些配置信息或者易变化的属性，我们通常会使用@Value进行属性注入。这样当属性变化时，我们可以直接修改配置文件，而不需要在代码中进行修改。



### 背景

最近接到的一个开发任务，就使用到了@Value注解对属性进行注入。

这次的开发内容主要是对外暴露一个图片路径，数据库中存放的是图片的相对路径，因此每次查询的时候都是调用封装的工具类进行域名拼接，而域名就是在工具类中通过@Value进行注入的

配置文件

```properties
image-domain-name.prefix=http:xxxx.com
```

工具类

```java
@Getter
@Component
@Slf4j
public class ImgUtil {
    @Value("image-domain-name.prefix:")
	private static String PREFIX;

    private ImgUtil() {}
    
    public static String addImgPre(String imgUrl) {
     // 方法体省略
    }
}
```



本来很简单的一个开发任务，结果一测试发现图片不显示，一看路径显示是

```
null/xxxx/b9a1aa85-9b40-4e18-8851-8965df2a88f8
```

一看到null，第一想法就是属性没有注入成功。

经过调试发现，`@Value`不支持直接给静态变量赋值，



### 未注入的原因

其实原因很简单，认真思考下就知道原因。

`@Value` 注解是依赖于属性的`set`方法进行注入的，而`static`修饰的属性是类属性，不存在`set`方法



### 解决方法

#### 1、set方法手动赋值

利用非静态`setter `方法注入静态变量, 会在`Spring`加载的时候进行属性注入

```java
@Getter
@Component
@Slf4j
public class ImgUtil {
    
	private static String PREFIX;

    private ImgUtil() {}
    
    @Value("image-domain-name.prefix:")
    public void setPreFix(String preFix) {
        ImgUtil.PREFIX = preFix;
    }

    public static String addImgPre(String imgUrl) {
     // 方法体省略
    }
}
```



#### 2、构造方法赋值

和`set`方法差不多

```java
@Getter
@Component
@Slf4j
public class ImgUtil {
    
	private static String PREFIX;

    private ImgUtil() {}
    
    @Value("image-domain-name.prefix:")
    public ImgUtil(String preFix) {
        ImgUtil.PREFIX = preFix;
    }

    public static String addImgPre(String imgUrl) {
     // 方法体省略
    }
}
```



#### 3、@PostConstruct

这个注解是**Common Annotations** API 的一部分，也是 JDK 模块的一部分`javax.annotation-api`。**Spring 只调用一次用\*@PostConstruct\*注释的方法，就在 bean 属性的初始化之后**。请记住，即使没有要初始化的内容，这些方法也会运行。

用*@PostConstruct*注释的方法 可以有任何访问级别，但不能是静态的

```java
@Getter
@Component
@Slf4j
public class ImgUtil {
    
	private static String PREFIX;

    private ImgUtil() {}
    
    @Value("image-domain-name.prefix:")
    private String PREFIX_TEMP;
    
    @PostConstruct
    public void init(){
        PREFIX = PREFIX_TEMP;
    }

    public static String addImgPre(String imgUrl) {
     // 方法体省略
    }
}
```



还有其他方法可以静态属性注入，例如利用`@ConfigurationProperties`



### 总结

`@Value`注解可以用来对`Spring`容器中的`bean`的属性进行注入，它能够被用在属性，构造方法，普通方法上。