## Swagger

### 简介

 Swagger 是一个规范且完整的框架，用于生成、描述、调用和可视化 Restful 风格的 Web 服务。 自动生成html文档。官网： https://swagger.io/ 

### 优势

1. 支持 API 自动生成同步的在线文档 。API文档和API定义同步更新
2. 提供 Web 页面在线测试 API ，不需要再下载postman使用
3. 支持多种语言



### 使用步骤

#### 导入依赖

```xml
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.9.2</version>
  </dependency>
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.9.2</version>
  </dependency>
```

#### 主程序类上加上**@EnableSwagger2**注解

```java
@SpringBootApplication
@EnableSwagger2 //添加Swagger配置类后这里可删除
public class SwaggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }
}
```



#### 访问API在线文档

 http://localhost:8080/swagger-ui.html#/ 

![](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584629864865.png)

为什么访问上面的页面，可能很多人存在疑问。一切都是根据源码来的。在Swagger-UI的包下

![1584631279057](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584631279057.png)

以上是Swagger的最简单用法，但是在API在线文档页面我们想修改一些配置，如分组信息，Swagger信息，需要配置Swagger



#### 配置Swagger

**Swagger 的Docket的bean实例**

我们可以通过Swagger的源码理解上图中的Swagger信息部分是如何出来的，同时我们知道如何修改。

**Docket对象**

![1584630527430](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584630527430.png)

**ApiInfo对象**

![1584630706863](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584630706863.png)



##### 配置Swagger的默认信息

```java
  private ApiInfo apiInfo() {
        Contact contact = new Contact("小K", "https://github.com/kong0827", "1351882069@qq.com");
        return new ApiInfo("小K的Swagger接口文档",
                "每天学习一丢丢，每天进步一点点",  // 描述
                "1.0",
                "https://github.com/kong0827",
                contact,                                   // 作者信息
                "Apache 2.0",
                "ttps://github.com/kong0827",
                new ArrayList()
        );
    }
```

##### 配置Swagger的Docket的bean实例

```java
 @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("开发小组名称")
                .select()
            	// 配置要扫描接口的方式
                .apis(RequestHandlerSelectors.any())
                // 过滤什么路径 过滤只含有kong下面的请求
                // .paths(PathSelectors.ant("/kong/**"))
                .build();
    }
```

![1584632244932](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584632244932.png)



##### 配置swagger是否启动

默认启动

![1584632853556](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584632853556.png)

```java
  @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("开发小组名称")
                .enable(true)
                .select()
                // 配置要扫描接口的方式
                .apis(RequestHandlerSelectors.any())
                // 过滤什么路径 过滤只含有kong下面的请求
                .paths(PathSelectors.ant("/kong/**"))
                .build();
    }
```

*问题：Swagger在生产环境使用，在发布的时候不使用*

1. 通过判断当前的环境

       @Bean
       public Docket docket(Environment environment) {
           // 设置要显示的Swagger环境
           Profiles profiles = Profiles.of("dev", "test");
           // 通过Environment.acceptsProfiles 判断是否处在自己设定的环境中
           boolean enable = environment.acceptsProfiles(profiles);
       
           return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .groupName("开发小组名称")
                   .enable(enable)
                   .select()
                   // 配置要扫描接口的方式
                   .apis(RequestHandlerSelectors.any())
                   // 过滤什么路径 过滤只含有kong下面的请求
                   .paths(PathSelectors.ant("/kong/**"))
                   .build();
       }

2. 采用属性注入

   ![1584634123648](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584634123648.png)

   ```java
   	/**
        * 设置是否显示接口文档
        */
       @Value("${swagger.enable}")
       private boolean enable;
   ```

   

