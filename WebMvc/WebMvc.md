## Spring MVC 自动配置

**下面图片是Spring Boot 官网关于Spring MVC自动配置的描述**

![](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585748859329.png)

### 简述

Spring Boot为Spring MVC提供了自动配置，可与大多数应用程序完美配合。

自动配置在Spring的默认值之上添加了以下功能：

- 包含`ContentNegotiatingViewResolver`和`BeanNameViewResolver`。
- 支持服务静态资源，包括对WebJars的支持（
- 自动注册`Converter`，`GenericConverter`和`Formatter`  bean类。
- 支持`HttpMessageConverters`
- 自动注册`MessageCodesResolver`
- 静态`index.html`支持。
- 定制`Favicon`支持
- 自动使用`ConfigurableWebBindingInitializer`bean

如果您想保留Spring Boot MVC功能并想要添加其他[MVC配置](https://docs.spring.io/spring/docs/5.1.14.RELEASE/spring-framework-reference/web.html#mvc)（拦截器，格式化程序，视图控制器和其他功能），则可以添加自己`@Configuration`的type类，`WebMvcConfigurer`但**不添加** `@EnableWebMvc`。如果您希望提供，或的自定义实例`RequestMappingHandlerMapping`，则可以声明一个实例来提供此类组件。`RequestMappingHandlerAdapter``ExceptionHandlerExceptionResolver``WebMvcRegistrationsAdapter`

如果您想完全控制Spring MVC，则可以添加自己的`@Configuration`注释`@EnableWebMvc`。



下面对上面进行讲解

### `ContentNegotiatingViewResolver`

1. 包含`ContentNegotiatingViewResolver`和`BeanNameViewResolver`

   - 自动配置了`ViewResolver` 视图解析器（根据方法的返回值得到视图对象View），视图对象决定如何渲染（转发或者重定向）

   - `ContentNegotiatingViewResolver`：组合所有的视图解析器

     在`WebMvcConfiguration`类中，我们查看源码，可以找到相关的配置

     ![1585749477055](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585749477055.png)

     点击进入`ContentNegotiatingViewResolver`，作为视图解析器，肯定有解析视图的方法，找到解析视图的方法`resolveViewName`。 可以看到spring mvc允许注册多个viewResolver 

     ![1585750918486](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585750918486.png)

     进入`getCandidateViews`方法，查看具体做了什么操作

     ![1585751119060](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585751119060.png)

     那么`getCandidateViews`类中的`viewResolvers`是从哪里定义的呢？在类`ContentNegotiatingViewResolver`中已经定义了视图解析器集合，然后对其赋值

     ![1585751317996](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585751317996.png)

     

   - 如何自定义视图解析器

     1. 定义视图解析器类

        ```java
        public class MyCustomViewResolver implements ViewResolver {
        
            @Override
            public View resolveViewName(String viewName, Locale locale) throws Exception {
                return null;
            }
        }
        ```

     2. 定义配置类，将自定义视图解析器注入到容器中

        ```java
        @Configuration
        public class CustomViewResolverConfig {
        
            @Bean
            public MyCustomViewResolver getCustomViewResolver() {
                return new MyCustomViewResolver();
            }
        }
        
        ```

        访问本项目下任意路径，会进入到`DispatcherServlet`类中 *doDispatch* 方法中，通过打断点可以查看到我们已经成功自定义的视图解析器

        ![1585753839594](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585753839594.png)

         ![1585753706284](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585753706284.png) 

     

### WebJars

- 查看 **WebMvcAutoConfiguration** 类

  - 

  ```java
  @Override
  		public void addResourceHandlers(ResourceHandlerRegistry registry) {
  			if (!this.resourceProperties.isAddMappings()) {
  				logger.debug("Default resource handling disabled");
  				return;
  			}
  			Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
  			CacheControl cacheControl = this.resourceProperties.getCache()
  					.getCachecontrol().toHttpCacheControl();
  			if (!registry.hasMappingForPattern("/webjars/**")) {
  				customizeResourceHandlerRegistration(registry
  						.addResourceHandler("/webjars/**")
  						.addResourceLocations("classpath:/META-INF/resources/webjars/")
  						.setCachePeriod(getSeconds(cachePeriod))
  						.setCacheControl(cacheControl));
  			}
  			String staticPathPattern = this.mvcProperties.getStaticPathPattern();
  			if (!registry.hasMappingForPattern(staticPathPattern)) {
  				customizeResourceHandlerRegistration(
  						registry.addResourceHandler(staticPathPattern)
  								.addResourceLocations(getResourceLocations(
  										this.resourceProperties.getStaticLocations()))
  								.setCachePeriod(getSeconds(cachePeriod))
  								.setCacheControl(cacheControl));
  			}
  		}
  ```

  1. ​	所有的/webjars/**，都去classpath:/META-INF/resources/webjars/找资源
     - webjars：以jar包的方式引入静态资源



### 静态资源

-  /**  访问当前项目的任何位置(静态资源的文件夹)

  - 

  ![1585237033325](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585237033325.png)

  通过查看源码可以定位到静态资源位置

  ![1585237086290](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585237086290.png)

  ![1585237177880](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585237177880.png)

  即以下路径

  ```xml
  "classpath:/META-INF/resources/", 
  "classpath:/resources/",
  "classpath:/static/", 
  "classpath:/public/" 
  "/"：当前项目的根路径
  ```



### `Converter`，`GenericConverter`和`Formatter`

- **自动注册了  `Converter`, `GenericConverter`, `Formatter` beans**

- `Converter `  转换器，  Converter 接口只支持从一个原类型转换为一个目标类型 

- `GenericConverter`   转换器，GenericConverter 接口支持在多个不同的原类型和目标类型之间进行转换 

- `Formatter`   格式化器

  以`Converter `  转换器为例进行讲解

  - 首先查看Converter接口的定义

  ```java
  public interface Converter<S, T> {   
      T convert(S source);
  }
  ```

   我们可以看到这个接口是使用了泛型的，第一个类型表示原类型，第二个类型表示目标类型，然后里面定义了一个 convert 方法，将原类型对象作为参数传入进行转换之后返回目标类型对象。当我们需要建立自己的 converter 的时候就可以实现该接口。

  - 在`WebMvcAutoConfiguration`类中，可以看到自动注册了Converter

    ![1585754968153](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585754968153.png)

    由上面的方法可以知道，我们可以自定义转换器，然后把它添加容器中即可

  - 自定义转换器

    ```java
    @Component
    public class DateConverter implements Converter<String, Date> {
    
        @Override
        public Date convert(String source) {
    
            return null;
        }
    }
    ```

  - 配置转换器

    ```java
    @Configuration
    public class CustomConverterConfig implements WebMvcConfigurer {
    
        @Autowired
        private DateConverter dateConverter;
    
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(dateConverter);
        }
    }
    ```



### HttpMessageConverters

Spring MVC使用该`HttpMessageConverter`接口来转换HTTP请求和响应。开箱即用中包默认设置。例如，可以将对象自动转换为JSON（通过使用Jackson库）或XML（通过使用Jackson XML扩展（如果可用），或者通过使用JAXB（如果Jackson XML扩展不可用））。默认情况下，字符串以编码`UTF-8`。

如果需要添加或自定义转换器，则可以使用Spring Boot的`HttpMessageConverters`类：

```java
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.*;

@Configuration
public class MyConfiguration {

	@Bean
	public HttpMessageConverters customConverters() {
		HttpMessageConverter<?> additional = ...
		HttpMessageConverter<?> another = ...
		return new HttpMessageConverters(additional, another);
	}

}
```

`HttpMessageConverter`上下文中存在的任何Bean都将添加到转换器列表中。您也可以用相同的方法覆盖默认转换器。



### MessageCodesResolver

**定义错误代码生成规则**

 Spring MVC的具有产生错误代码从绑定错误的渲染错误消息的策略：`MessageCodesResolver`。如果您设置`spring.mvc.message-codes-resolver.format`属性`PREFIX_ERROR_CODE`或`POSTFIX_ERROR_CODE`，Spring Boot会为您创建一个（请参阅中的枚举[`DefaultMessageCodesResolver.Format`](https://docs.spring.io/spring/docs/5.1.14.RELEASE/javadoc-api/org/springframework/validation/DefaultMessageCodesResolver.Format.html)）。 



### 欢迎页

```java
private Optional<Resource> getWelcomePage() {
    // 去获取静态资源位置
    String[] locations = getResourceLocations(
        this.resourceProperties.getStaticLocations());
    return Arrays.stream(locations).map(this::getIndexHtml)
        .filter(this::isReadable).findFirst();
}
```

![1585237584838](.\src\main\resources\images\1585237584838.png)



### 图标

![1585237975765](E:\githubResp\SpringBoot-Demo\WebMvc\src\main\resources\images\1585237975765.png)



### 总结

- ​	1）、WebMvcAutoConfiguration是SpringMVC的自动配置类

  ​	2）、在做其他自动配置时会导入；@Import(**EnableWebMvcConfiguration**.class)

  ```java
      @Configuration
  	public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration {
        private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();
  
  	 //从容器中获取所有的WebMvcConfigurer
        @Autowired(required = false)
        public void setConfigurers(List<WebMvcConfigurer> configurers) {
            if (!CollectionUtils.isEmpty(configurers)) {
                this.configurers.addWebMvcConfigurers(configurers);
              	//一个参考实现；将所有的WebMvcConfigurer相关配置都来一起调用；  
              	@Override
               // public void addViewControllers(ViewControllerRegistry registry) {
                //    for (WebMvcConfigurer delegate : this.delegates) {
                 //       delegate.addViewControllers(registry);
                 //   }
                }
            }
  	}
  ```

  ​	3）、容器中所有的WebMvcConfigurer都会一起起作用；

  ​	4）、我们的配置类也会被调用；

  ​	效果：SpringMVC的自动配置和我们的扩展配置都会起作用；

  

## 扩展Spring MVC

### 添加视图映射

