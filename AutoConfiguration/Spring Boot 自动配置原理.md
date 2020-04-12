Spring Boot 自动配置原理

 **自动配置**是一项功能，允许开发人员根据应用程序的不同条件在Spring上下文中自动配置Bean 。

 在讲解Spring Boot的自动原理之前。首先我们应该先了解 Spring的**@Conditional** 功能，所有的Spring Boot的 AutoConfiguration 都依赖于它。

### **@Conditional** 

​	在开发基于Spring 的应用程序时，我们可能会遇到有条件的注册bean的需求。Spring 4 引入了 **@Conditional**的概念。通过使用 **@Conditional**，可以根据任意条件有条件地注册bean。 



### 自动配置

Spring Boot自动配置的关键是 **@EnableAutoConfiguration** 注解，我们使用 **@SpringBootApplication**注释应用程序的启动类。

```java
@SpringBootApplication
public class AutoConfigurationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoConfigurationApplication.class, args);
    }
}
```

我们进入**@SpringBootApplication**注解中进一步探索自动配置的原理

![1586185500830](https://github.com/kong0827/SpringBoot-Demo/blob/master/AutoConfiguration/src/main/resources/images/1586185500830.png)

我们可以发现这个主要是三个注解

#### @SpringBootConfiguration

​	标注在某个类上，表示这是一个Spring Boot的配置类

​	我们点击进入查看这个注解的作用，发现此注解被**Configuration**标注，表明这是一个配置类，是容器中的一个组件；

![1586185846983](https://github.com/kong0827/SpringBoot-Demo/blob/master/AutoConfiguration/src/main/resources/images/1586185846983.png)

#### @ComponentScan

被Spring自动扫描并且装入bean容器中

#### @EnableAutoConfiguration

**开启自动配置的功能**，这个注解是自动配置的关键所在。

![1586186406656](https://github.com/kong0827/SpringBoot-Demo/blob/master/AutoConfiguration/src/main/resources/images/1586186406656.png)

- **@AutoConfigurationPackage**

  <img src="https://github.com/kong0827/SpringBoot-Demo/blob/master/AutoConfiguration/src/main/resources/images/1586186652929.png" alt="1586186652929" style="zoom:150%;" />

  ![1586187161934](https://github.com/kong0827/SpringBoot-Demo/blob/master/AutoConfiguration/src/main/resources/images/1586187161934.png)

  ![1586187393312](https://github.com/kong0827/SpringBoot-Demo/blob/master/AutoConfiguration/src/main/resources/images/1586187393312.png)



