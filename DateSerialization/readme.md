



## DateTimeFormat

1、作用于Post请求中的LocalDate，LocalDateTime无效。作用于Date类型有效

2、`@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")`

​	请求参数为` 2021-01-27T13:54:42.471 `

作用于POST请求可以格式化，GET请求不可格式化，必须设置为

`@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")`

3、`DateTimeFormat`多作用于Get请求用于接收日期类型参数，POST请求不加同样可以接收，GET请求不加则会转化异常。 @DatetimeFormat是将String转换成Date，一般前台给后台传值时用 





## JsonFormat

  一般后台传值给前台时 

### 单个字段格式化

格式化出参日期类型，`LocalDate`、`LocalDateTime` ，`Date`类型都可以格式化成功

### 全局格式化配置

```properties
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=GMT+8
spring.jackson.serialization.write-dates-as-timestamps=false
```

**只适用于Date日期类型，LocalDate类型不起作用**

实际上在Jackson序列化的时候，会根据不同类型调用不同的`Serializer`进行序列化，如果没有默认的`Serializer`时，调用的是类的`toString()`。而Date类型序列化时会使用`DateSerializer`，里面会使用`spring.jackson.date-format`中的配置，而LocalDateTime则是通过`LocalDateTimeSerializer`，`LocalDateTimeSerializer`默认使用的是



`LocalDate`、`LocalDateTime`类型格式化

```java
@Configuration
public class DateformatConfig {

    /**
     * Date格式化字符串
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * DateTime格式化字符串
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * Time格式化字符串
     */
    private static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 自定义Bean
     *
     * @return
     */
    @Bean
    @Primary
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }
}
```



