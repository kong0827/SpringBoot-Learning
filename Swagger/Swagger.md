## Swagger

### 简介

 Swagger 是一个规范且完整的框架，用于生成、描述、调用和可视化 Restful 风格的 Web 服务。 自动生成html文档。官网： https://swagger.io/ 

### 优势

1. 支持 API 自动生成同步的在线文档 。API文档和API定义同步更新
2. 提供 Web 页面在线测试 API ，不需要再下载postman使用
3. 支持多种语言



### 源码位置 

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

   ```java
   @Bean
   public Docket docket(Environment environment) {
       // 设置要显示的Swagger环境
       Profiles profiles = Profiles.of("dev", "test");
       // 通过Environment.acceptsProfiles 判断是否处在自己设定的环境中
       boolean enable = environment.acceptsProfiles(profiles);
   
       return new Docket(DocumentationType.SWAGGER_2)
               .apiInfo(apiInfo())
               .enable(enable)
               .select()
               // 配置要扫描接口的方式
               .apis(RequestHandlerSelectors.any())
               // 过滤什么路径 过滤只含有kong下面的请求
               .paths(PathSelectors.ant("/kong/**"))
               .build();
   }
   ```
   
2. 采用属性注入

   ![1584634123648](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584634123648.png)

   ```java
   	/**
        * 设置是否显示接口文档
        */
       @Value("${swagger.enable}")
       private boolean enable;
   ```

   

##### 分组注释接口

*分组信息同样在源码中可以查看，默认分组为 default*

![1584801012449](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584801012449.png)

设置分组名称

```java
@Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("小K的分组")
                .enable(true)
                .select()
                // 配置要扫描接口的方式
                .apis(RequestHandlerSelectors.any())
                .build();
    }
```

![1584801220708](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584801220708.png)



**在团队协同开发的过程中，可以为自己配置分组，同时设定扫描某个指定包。通过切换分组查看指定的接口**

```java
 @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("小K的分组2")
                .enable(true)
                .select()
                // 配置要扫描接口的方式
                .apis(RequestHandlerSelectors.basePackage("com.kxj"))
                .build();
    }

    @Bean
    public Docket docket3() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("小K的分组3")
                .enable(true)
                .select()
                // 配置要扫描接口的方式
                .apis(RequestHandlerSelectors.basePackage("com.kxj"))
                .build();
    }
```

![1584801406067](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584801406067.png)





##### 实体类配置

```java
public class User {
    public String username;
    public String password;
}
```

```java
// 只要接口返回值存在对象，他就会被扫描Swagger的实体类中
@GetMapping
    public User getUser() {
        return new User();
    }
```

![1584802513797](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584802513797.png)



如果我们想把生成的文档的实体类添加相应的注释，则需要借助Swagger相关的注解

```java
@ApiModel("用户实体类")
public class User {

    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("密码")
    public String password;

}
```

![1584802735957](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584802735957.png)



##### 接口注解配置

*通过在接口上配置相关注解，我们也可以生成在在线文档上，便于我们查看*

```java
@RequestMapping("user")
@RestController
@Api(tags = "用户信息")
public class UserController {

    @GetMapping
    public User getUser() {
        return new User();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public void deleteUser(@ApiParam("用户ID") @PathVariable("id")Integer id) {

    }
}
```

![1584803672720](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584803672720.png)





##### 常用注解

注解在Swagger的注解包下

![1584802989581](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584802989581.png)

###### 1、@Api

说明每个controller层控制类的作用，即每个请求类
 属性：tags：描述该类的作用

###### 2、@ApiOperation

作用在控制类中的每个方法上，说明该类的作用
 属性：value：说明该类的作用
 以上这几个注解在代码中的使用例子如下：



```kotlin
//说明该类的作用
@Api(tags = "学生信息")
//指明该类为控制器
@RestController
//设置请求路径url
@RequestMapping("/student")
public class StudentController {
   /**
     * 根据id查询学生信息
     * @param id 学生id
     * @return
    */
    @ApiOperation(value = "根据id查询学生信息")
    @GetMapping("/query/{id}")
    private List<Student> queryById( @ApiParam(value = "学生id", required = true) @PathVariable("id") Long id) {
        List<Student> studentList = studentService.queryById(id);
        return studentList;
    }
}
```

swagger-ui展示结果如下：



![img](https:////upload-images.jianshu.io/upload_images/15937175-fb652b3d833d6983.png?imageMogr2/auto-orient/strip|imageView2/2/w/919/format/webp)

图1

###### 3、@ApiParam

@ApiParam作用于请求方法上，定义api参数的注解，属性有：
 name：api参数的英文名
 value：api参数的描述
 required：true表示参数必输，false标识参数非必输，默认为非必输
 此注解通常与@RequestParam或者@PathVariable集合使用，因为它的作用只是定义每个参数（因此可以不使用，但是为了方便测试，加上效果更好），如果要获取前端的参数还需要通过@RequestParam或者@PathVariable来获取

###### 4、 @ApiImplicitParams、@ApiImplicitParam

定义参数的注解除了@ApiParam之外，这两个注解也可以定义参数
 ①、@ApiImplicitParams定义一组参数
 ②、@ApiImplicitParam写在@ApiImplicitParams中，定义每个参数的信息，属性为：
 name：参数英文名称
 value：参数中文名称
 paramType：调用的url 参数形式，“query”为问号"?"后面的拼接参数，“path”为绑定的参数



##### 测试

*通过Swagger在线文档我们无需借助postman等工具，可以在线测试*

测试不在详述，用法同postman等

![1584803824703](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584803824703.png)

![1584803838803](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584803838803.png)



### 总结

1. 可以给实体类和接口添加注释信息
2. 接口文档实时更新
3. 在线测试