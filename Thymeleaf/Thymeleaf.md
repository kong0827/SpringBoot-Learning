



### SpringBoot对静态资源的映射规则

#### webjars

**WebMvcAutoConfiguration**

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

#### /** 访问当前项目的任何位置(静态资源的文件夹)

![1585237033325](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1585237033325.png)

通过查看源码可以定位到静态资源位置

![1585237086290](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1585237086290.png)

![1585237177880](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1585237177880.png)

即以下路径

```xml
"classpath:/META-INF/resources/", 
"classpath:/resources/",
"classpath:/static/", 
"classpath:/public/" 
"/"：当前项目的根路径
```



#### 欢迎页

```java
private Optional<Resource> getWelcomePage() {
    // 去获取静态资源位置
    String[] locations = getResourceLocations(
        this.resourceProperties.getStaticLocations());
    return Arrays.stream(locations).map(this::getIndexHtml)
        .filter(this::isReadable).findFirst();
}
```

![1585237584838](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1585237584838.png)



#### 图标

![1585237975765](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1585237975765.png)