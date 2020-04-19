## Restful

### 特点

- 用URL描述资源
- 使用HTTP方法描述行为。使用HTTP状态码来表示不同的结果
- 使用json交互
- Restful知识一种风格，并不是强制的标准

- ##### 用HTTP方法体现对资源的操作（动词）

  - GET ： 获取、读取资源
  - POST ： 添加资源
  - PUT ： 修改资源
  - DELETE ： 删除资源

### 规范

| 不符合REST的接口URI      | 符合REST接口URI        | 功能       |
| :----------------------- | :--------------------- | :--------- |
| GET /api/getCar          | GET  /api/cars/{id}    | 获取一辆车 |
| GET /api/getCars         | GET   /api/cars        | 获取所有车 |
| GET /api/addCars         | POST  /api/cars        | 添加一辆车 |
| GET /api/editCars/{id}   | PUT     /api/cars/{id} | 修改一辆车 |
| GET /api/deleteCars/{id} | DELETE /api/cars/{id}  | 删除一辆车 |

### HTTP状态码

通过HTTP状态码体现动作的结果,不要自定义

```
200 OK 
400 Bad Request 
500 Internal Server Error
```

- ##### 资源**过滤、排序、选择和分页**的表述

![img](C:\Users\小K\Pictures\springboot\41b90d46f19fe1277a5fc6e2828bbd39_845x190.png)



### MockMvc

-  **Spring MockMVC**来执行Spring webmvc控制器的**集成测试**。**MockMVC**类是[Spring MVC](https://howtodoinjava.com/spring-mvc-tutorial/)测试框架的一部分，该框架有助于显式启动Servlet容器来测试控制器。 

- get请求也可以用对象接收

- post请求也可以用参数接收

  当我们post请求只有一两个参数时，不需要创建对象时，可以使用JSONObject实体类。

  如下用post传递Integer类型，在controller层定义**getInteger()**方法；

  ```
  @ResponseBody
  @PostMapping("/postID")
    public Integer getInteger(@RequestBody JSONObject jsonObject){
    String a=jsonObject.get(“id”).toString();
    Integer id=Integer.parseInt(a);
    return id;
  }
  ```

  

- 使用方式一

  ```java
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;@Beforepublic void setup() {    
      mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();}
  /** 
  * 查询 
  * @throws Exception 
  */
  @Testpublic void whenQuerySuccess() throws Exception {    
      //发送get请求    
      mockMvc.perform(MockMvcRequestBuilders.get("/user")            	               .param("username", "tom")            
      .param("age", "21")            
      .param("sex","2")            
       //请求的格式            
      .contentType(MediaType.APPLICATION_JSON_UTF8))            
       //请求的相应期待是200                        .andExpect(MockMvcResultMatchers.status().isOk())            
          //返回的长度是3            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));}
  ```

- 使用方式二 **@AutoConfigureMockMvc**

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
  @AutoConfigureMockMvc
  public class TeacherControllerTest {
      @Autowired
      private MockMvc mockMvc;
  
      @Test
      public void test() throws Exception {
          mockMvc.perform(MockMvcRequestBuilders.get("/teacher")
                  .contentType(MediaType.APPLICATION_JSON_UTF8))
                  .andExpect(MockMvcResultMatchers.status().isOk());
      }
  }
  ```

- 使用方式三  **@WebMvcTest**

  **注意事项**

  - @SpringBootTest不能和@WebMvcTest(TeacherController.class)同时使用
  - @WebMvcTest(Xxx.class)   Xxx.class可省略
  - 不能和springsecurity使用，同**@AutoConfigureMockMvc**，可以通过**@WebMvcTest(secure = false)**使用
  
- **如何避开拦截器**
  
  ```java
  @RunWith(SpringRunner.class)
  @WebMvcTest(TeacherController.class)
  public class TeacherControllerTest {
  
      @Autowired
      private MockMvc mockMvc;
  
      @Test
      public void test() throws Exception {
          mockMvc.perform(MockMvcRequestBuilders.get("/teacher")
                  .contentType(MediaType.APPLICATION_JSON_UTF8))
                  .andExpect(MockMvcResultMatchers.status().isOk());
      }
  }
  ```
```
  
  
  
  **如何避开拦截器**
  
  ```java
  @MockBean
  private WebMVCInterceptorConfig webMVCInterceptorConfig;
```

  

### 常用注解

- RestController     标明一个Java类提供RestAPI
- RequestMapping及其变体，映射HTTP请求url到java方法  
- RequestParam     映射请求参数到Java方法上的参数
- PathVariable         映射URL片段到java方法的参数
- JsonView                控制返回的json视图
  1. 使用接口来声明多个视图
  2. 在值对象的get方法上指定视图
  3. 在Controller方法上指定视图
- PageDefault          指定分页参数默认值



#### @PathVariable

- 接收请求路径中占位符的值

```java
@GetMapping(value = "/user/{id:\\d+}")
public User getInfo(@PathVariable(name = "id") String id) {
    User user = new User();
    user.setUsername("tom");
    return user;
}
```

- **@GetMapping(value = "/user/{id:\\d+}")**

  正则表达式限定只能输入数字，可以根据实际要求改变

- 测试用例

  ```java 
  @Test
  public void whenGenInfoSuccess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/user/1")                 .contentType(MediaType.APPLICATION_JSON_UTF8))             .andExpect(MockMvcResultMatchers.status().isOk());
  }
  
  /**
   * 正则表达式只能输入数字
   * @throws Exception
   */
  @Test
  public void whenGenInfoSuccess1() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
              .contentType(MediaType.APPLICATION_JSON_UTF8))      .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }
  ```

#### @JsonView

**@JsonView可以过滤pojo的属性，使Controller在返回json时候，pojo某些属性不返回，比如User的密码，一般是不返回的，就可以使用这个注解**

**使用步骤**

+ 使用接口来声明多个接口
+ 在值对象的get方法上指定视图
+ 在Controller方法上指定视图



**示例：一个返回密码，一个不返回密码**

```java
public class Student implements Serializable {

    public interface simpleView {
    };

    public interface detailView extends simpleView {
    };

    private String username;
    private String password;

    @JsonView(simpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(detailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

```java
// controller层
@GetMapping("/student")
@JsonView(Student.simpleView.class)
public List<Student> getStudents() {
    List<Student> students = new ArrayList<>();
    students.add(new Student());
    students.add(new Student());
    return students;
}

@GetMapping("/student2")
@JsonView(Student.detailView.class)
public List<Student> getStudents2() {
    List<Student> students = new ArrayList<>();
    students.add(new Student());
    students.add(new Student());
    return students;
}
```

**测试用例**

```java
@Autowired
private WebApplicationContext wac;

private MockMvc mockMvc;

@Before
public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
}

@Test
// 简单视图不返回密码
public void testQuery() throws Exception {
    String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString();
    System.out.println(contentAsString);
}

@Test
// 详细视图返回密码
public void testQuery1() throws Exception {
    String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/student2"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString();
    System.out.println(contentAsString);
}
```

#### @PageableDefault

- Pageable 是[spring](http://lib.csdn.net/base/javaee) Data库中定义的一个接口，该接口是所有分页相关信息的一个抽象，通过该接口，我们可以得到和分页相关所有信息

- Pageable定义了很多方法，但其核心的信息只有两个：一是分页的信息（page、size），二是排序的信息。

- 只能用于get请求

- post请求可以添加在url后，也可以接收到。放在请求体内无法接收。eg:http://localhost:8080/user?page=1&size=2

- ```java
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.PARAMETER})
  public @interface PageableDefault {
      int value() default 10;
      int size() default 10;
      int page() default 0;
      String[] sort() default {};
      Direction direction() default Direction.ASC;
  }
  ```
  
- 用法

  ```java
  @PageableDefault(page = 2, size= 10, sort = "username,DESC") Pageable pageable
  ```

  ```java
@PageableDefault(value = 15, sort = { "update_time" }, direction = Sort.Direction.DESC) Pageable pageable
  ```

#### @RequestBody

- 主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的) 

- 只能作用Post、put等请求，因为get请求无请求体

  ```
  @PostMapping("/user")
  public User create(@RequestBody User user) {    
      return null;
  }
  
  @Data
  public class User {
      private Integer id;
      private String username;
      private int age;
  }
  ```

- 测试

  请求对象可以是其中的部分参数，仍然可以映射到对象。没有请求的参数为默认值

  ```
  @Testpublic void whenCreateSuccess() throws Exception {    
      String content = "{\"username\": \"tom\", \"age\": 10}";    	
      mockMvc.perform(MockMvcRequestBuilders.post("/user")            	   .contentType(MediaType.APPLICATION_JSON_UTF8)            
      .content(content))            
      .andExpect(MockMvcResultMatchers.status().isOk())            				.andExpect(MockMvcResultMatchers
      .jsonPath("$.id").value(1));
  }
  ```

#### 日期类型的处理

- 前后台传递是只传递时间戳，前后自己处理显示什么样式（yyyy-MM-dd还是yyyy-MM-dd HH:mm:ss等）

  在application.properties加配置,不加返回的还是日期类型 

  ```java
  spring.jackson.serialization.write-dates-as-timestamps: true
  ```

  ```java
  /** 
  * 日期类型处理，只传递时间戳 
  */
  @Test
  public void whenDateSuccess() throws Exception {   
      Date date = new Date();    
      String content = "{\"username\": \"tom\", \"age\": 10, 	\"birthday\":"+date.getTime()+"}";   
      String reuslt = mockMvc.perform(post("/user")
      .contentType(MediaType.APPLICATION_JSON_UTF8)           
      .content(content))           
      .andExpect(status().isOk())            
      .andExpect(jsonPath("$.id").value("1"))            		  .andReturn().getResponse()
      .getContentAsString();   
      System.out.println(reuslt);
      }
  ```

- 利用spring框架中的Converter接口实现自定义Bean

  ```Java
  package org.springframework.core.convert.converter;
  public interface Converter<S, T> {
      T convert(S var1);
  }
  ```


  实现自定义的Bean

  ```Java
  private static final String DATE_TIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
  
  @Bean
  public Converter<String, Date> convertDateTime(){
      return new Converter<String, Date>(){
          @Override
          public Date convert(String source) {
              Date date = null;
              SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMATE);
              try{
                  simpleDateFormat.parse(source);
              }catch (ParseException e){
                  log.info("parse date failed!" + e);
              }
              return date;
          }
      };
  }
  ```

  这样接前端参数的实体，就可以直接由String类型映射到Date类型。时间格式为 yyyy-MM-dd HH:mm:ss.

  优点 ： 无
  缺点： 每个都需要自动注入，不推荐使用。


- **直接在相关的字段上使用 JsonFormat 注解**。
  @JsonFormat(pattern = “yyyy-MM-dd HH:mm:ss”, timezone = “GMT+8”)
  private Date time;

  问题2：后端的Date类型直接返回给前端是以时间戳形式返回的，如果想以指定的时间格式返回，需要做如何处理？

  解决办法：

  在@RestController注解下，Date类型返回给前端默认是时间戳格式。要指定的格式有一下两种方式：

  - 方式一：

   在application.properties 属性文件中增加两个属性配置：

  ```Java
  spring.jackson.time-zone=GMT+8
  spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
  ```


  优点：简单
  缺点：适用工程中所有的DTO，如果老的代码中就是要求时间戳格式返回，前端也做了解析，那么后面新增这两个属性配置，会出现不兼容错误。

  - 方式二：

  直接在DTO的属性上使用注解：

  ```Java
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private Date time;
  ```

  优点： 可以根据需要对指定的DTO进行格式转换
  缺点： 无

#### 参数校验

##### 方式一：@Valid

1. **@Valid注解和BindingResult验证请求参数的合法性并处理校验结果**

   -  org.hibernate.validator.constraints包下，过时，但是BindingResult必须配合此包下使用，不能使用import javax.validation.constraints包下，否则无效

   - 在属性上加上相关注解

     ```
     @Datapublic class User implements Serializable {   
         @NotBlank(message = "不能爲空")    
         private Integer id;    
         private String username;    
         private int age;    
         private Date birthday;
     }
     ```

   - 在请求上加上@Valid注解

     ```java
       @PostMapping("/user")
         public User create(@Valid @RequestBody User user) {
             String s = ReflectionToStringBuilder.toString(user);
             System.out.println(s);
             user.setId(1);
             return user;
         }
     ```

   - 如果校验不通过，不会进入请求中，例如上面的user不会被打印。我们需要看到错误信息，可以加上BindingResult，记录查看错误信息。BindingResult也可以结合@Validated使用，BindingResult和错误信息一一对应

     ```java
     @PostMapping("/user")
     public User create(@Valid @RequestBody User user, BindingResult errors) {   
         if (errors.hasErrors()) {        
             errors.getAllErrors().stream().forEach(error ->                        		System.out.println(error.getDefaultMessage()));  
         }    
      }
     ```

##### 方式二：@Validated

1. controller层参数校验分为两种

   - **单个参数校验**

     适用于**get请求**时只传入少量参数的场景

     这里一定要在方法所在的**controller类上加入`@Validated`注解**，不然没有任何效果。 

     ```java
     @RestController
     @Validated
     public class TeacherController {
         @GetMapping("/teacher")
         public void getTeacher(
             @NotEmpty(message = "用户名不能为空") String username,
             @Min(value = 18, message = "年龄不能小于18岁") Integer age) {
             System.out.println("username:" + username + "    age:" + age);
         }
     }
     ```

   - **实体类参数校验**

     当处理post请求或者请求参数较多的时候我们一般会选择使用一个bean来接收参数，然后在每个需要校验的属性上使用参数校验注解：

     ```java
     @Data
     public class Teacher {
         @Digits(message = "id必须为数字", integer = 6, fraction = 2)
         private Integer id;
         @NotEmpty(message = "用户名不能为空")
         private String username;
         @NotNull(message = "密码不能为空")
         private String password;
     }
     ```
     

然后在controller方法中用`@RequestBody`表示这个参数接收的类：
     
```
      @PostMapping("/teacher")
      public void getTeacher(@Validated @RequestBody Teacher teacher,
      							BindingResult errors) {
              errors.getAllErrors().stream().forEach(
              	error -> {System.out.println(error.getDefaultMessage());});
      }
```

get请求，使用对象接收也可以进行校验
     
```java
     /**
      * get请求对象接收
      * @param teacher
      * @param errors
      */
     @GetMapping("/teacher1")
     public void getTeacher1(@Validated Teacher teacher,
                             BindingResult errors) {
         errors.getAllErrors().stream().forEach(error -> {
             System.out.println(error.getDefaultMessage());
         });
     }
```



 **分组**

- @Valid是javax.validation里的。
  
- @Validated是@Valid 的一次封装，是Spring提供的校验机制使用。@Valid不提供分组功能
  
- 为什么需要分组？
  
  例如：当一个实体类 需要多种验证方式的时，对于一个实体类的id 来说，新增的时候不需要校验，更新时则是必须校验
  
- 步骤
  
  1. 新建一个接口
  
     ```java
        /**
         * @author kxj
         * @date 2020/2/21 21:30
         * @desc 分组接口类
         */
        public interface First {
        }
     ```
     
   2. 实体类
  
      ```java
        @NotEmpty(groups = {First.class})
      ```
     
      注：
  
      (1)不分配groups，默认每次都要进行验证
  
      (2)对一个参数需要多种验证方式时，也可通过分配不同的组达到目的。例：
  
      ```java
        @NotEmpty(groups={First.class})  
        @Size(min=3,max=8,groups={Second.class})  
        private String name; 
      ```
     
   3. 控制类
  
      ```java
        /**
         * 分组验证 校验
         */
        @PostMapping("/teachers")
        public void getTeachers(@Validated({First.class}) @RequestBody Teacher teacher,BindingResult errors) {
            System.out.println(teacher.getId());
            errors.getAllErrors().stream().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
        }
        
        /**
         * 分组验证 不校验
         */
        @PostMapping("/teachers2")
        public void getTeachers2(@Validated @RequestBody Teacher teacher,
                                 BindingResult errors) {
            System.out.println(teacher.getId());
            errors.getAllErrors().stream().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
        }
      ```
     
      注：
        **@Validated没有添加groups属性时，默认验证没有分组的属性**
     
      **@Validated没有添加groups属性时， 所有参数的验证类型都有分组 ,则不验证任何参数**
  
   - **组序列 **

     默认情况下，不同组别的约束验证是无序的，然而在某些情况下，约束验证的顺序却很重要。

     例：

     （1）第二个组中的约束验证依赖于一个稳定状态来运行，而这个稳定状态是由第一个组来进行验证的。

     （2）某个组的验证比较耗时，CPU 和内存的使用率相对比较大，最优的选择是将其放在最后进行验证。因此，在进行组验证的时候尚需提供一种有序的验证方式，这就提出了组序列的概念。
  一个组可以定义为其他组的序列，使用它进行验证的时候必须符合该序列规定的顺序。在使用组序列验证的时候，如果序列前边的组验证失败，则后面的组将不再给予验证。
  
     **分组接口类 （通过@GroupSequence注解对组进行排序）：**

     ```java
  public interface First {   
     }
     
     public interface Second {  
     }
     
     @GroupSequence({First.class,Second.class})  
     public interface Group {  
     } 
     ```
  
     **实体类**

       ```java
  @Data
     public class People implements Serializable {
     
         // 在First分组，判断不能为空
         @NotEmpty(groups = {First.class}, message = "id不能为空")
         private String id;
     
         // name的字段不能为空，且长度在3-8位
         @NotEmpty(groups = {First.class}, message = "name不能为空")
         @Size(min=3, max=8, groups={Second.class},message="name在3-8位之间")
         private String name;
     
     }
       ```
  
     **控制类**

     **@Validate**不加Group，默认校验字段校验的规则不起作用

     ```java
  @RestController
     public class PeopleController {
     
     /**
      * 单个对象校验
      * @param people
      * @param errors
      */
     @PostMapping("/people")
     public void addPeople(@Validated({Group.class}) @RequestBody People people,                                                 BindingResult errors) {
             errors.getAllErrors().stream().forEach(error ->                             System.out.println(error.getDefaultMessage()));
     }
     
     /**
      * 多个对象校验
      * 个功能方法上处理多个模型对象时，需添加多个验证结果对象
      * @param p1
      * @param errors1
      * @param p2
      * @param errors2
     */
     @PostMapping("/peoples")
     public void addPeople2(@Validated({Group.class}) @RequestBody People p1, 	                        BindingResult errors1,
                            @Validated({Group.class}) @RequestBody People p2,                            BindingResult errors2) {
             errors1.getAllErrors().stream().forEach(error -> 		                     System.out.println(error.getDefaultMessage()));
             System.out.println("--------------------------");
             errors2.getAllErrors().stream().forEach(error ->                             System.out.println(error.getDefaultMessage()));
         }
     }
     
     ```
  
     

   

   

   ##### Difference Between @NotNull, @NotEmpty, and @NotBlank

   ###### @NotNull

   不能为`null`，但是可以为空字符串`""`

   ###### @NotEmpty

   不能为`null`，不能为空字符串`""`，其本质是CharSequence, Collection, Map, or Array的size或者length不能为0

   ###### @NotBlank

   a constrained String is valid as long as it’s not null and the trimmed length is greater than zero

   ###### @NonNull

   @NotNull 是 JSR303（Bean的校验框架）的注解，用于运行时检查一个属性是否为空，如果为空则不合法。
@NonNull 是JSR 305（缺陷检查框架）的注解，是告诉编译器这个域不可能为空，当代码检查有空值时会给出一个风险警告，目前这个注解只有IDEA支持

##### **自定义注解(参数校验)**

1. 编写自定义注解

   ```java
   /**
    * @Target 作用在方法和属性上
    */
   @Target({ElementType.FIELD, ElementType.METHOD})
   /**
    * 运行时生效
    */
   @Retention(RetentionPolicy.RUNTIME)
   /**
    * @Constraint 代表参数校验
    * validatedBy 具体哪个类实现参数校验
    */
   @Constraint(validatedBy = MyConstraintValidator.class)
   public @interface MyConstraint {
       /**
        * 添加value属性，可以作为校验时的条件,若不需要，可去掉此处定义
        */
       int value() default 0;
       /**
        * 参数校验必须添加以下三个
        * @return
        */
       String message() default "自定义参数校验";
   
       Class<?>[] groups() default {};
   
       Class<? extends Payload>[] payload() default {};
   
   }
   ```

2. 编写参数校验实现类

   ```java
   /**
    * @author kxj
    * @date 2020/2/20 21:22
    * @desc 自定义参数校验实现类
    *          必须实现ConstraintValidator接口
    *            ConstraintValidator<MyConstraint, Object>
    *                MyConstraint: 自定义注解
    *                Object: 参数类型
    */
   public class MyConstraintValidator implements
           ConstraintValidator<MyConstraint, Object> {
   
       /**
        * 初始化方法
        * @param constraintAnnotation
        */
       @Override
       public void initialize(MyConstraint constraintAnnotation) {
           System.out.println("自定义参数校验初始化");
       }
   
       /**
        * 真正做参数校验的方法
        * @param value 参数校验的参数
        * @param context 上下文对象
        * @return
        */
       @Override
       public boolean isValid(Object value,
                              ConstraintValidatorContext context) {
           System.out.println("参数为：" + value);
           return false;
       }
   }
   ```

3. 在指定方法或属性上添加 注解

   ```java
    @MyConstraint
    private String username;
   ```

4. 测试

   ```java
   /**
    * 修改测试 put请求
    */
   @Test
   public void updateTest() throws Exception {
           Date date = new Date();
           String content = "{\"id\": 1,\"username\": \"tom\", \"password\": null, \"birthday\":"+date.getTime()+"}";
           String reuslt = mockMvc.perform(MockMvcRequestBuilders.put("/student/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                   .content(content))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.id").value("1"))
                   .andReturn().getResponse().getContentAsString();
           System.out.println(reuslt);
   }
   
   /**
    * 删除测试 delete请求
    */
   @Test
   public void deleteTest() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.delete("/student/1")
                       .contentType(MediaType.APPLICATION_JSON_UTF8))
           .andExpect(MockMvcResultMatchers.status().isOk());
   }
   ```

### Restful API的拦截

#### 过滤器（J2EE的规范）

- **自定义的过滤器**

```java
@Component
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse                servletResponse,FilterChain filterChain) throws IOException, ServletException    {
        LocalDateTime startTime = LocalDateTime.now();
        filterChain.doFilter(servletRequest, servletResponse);
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        long millis = duration.toMillis();
        System.out.println("耗时为：" + millis + "毫秒");

    }

    @Override
    public void destroy() {
        System.out.println("Filter销毁");
    }
}
```

- **第三方过滤器**

  ```java
  @Configuration
  public class WebConfig {
  
      // 将TimerFilter默认为第三方的Filter，然后交给Spring管理
      // 此时上面的@Componenet需要注释（假设为第三方的Filter）
      @Bean
      public FilterRegistrationBean timeFilter() {
          FilterRegistrationBean registration = new FilterRegistrationBean();
          TimeFilter timeFilter = new TimeFilter();
          // 设置过滤器
          registration.setFilter(timeFilter);
          // 设置URL
          List<String> urls = new ArrayList<>();
          urls.add("/*");
          registration.setUrlPatterns(urls);
          return registration;
      }
  }
  ```

  

#### 拦截器（Spring框架提供）

```java
@Component
public class TimeInterceptor implements HandlerInterceptor {
    /**
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用。
     * @param request
     * @param response
     * @param handler   控制器方法的声明
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
        LocalDateTime startTime = LocalDateTime.now();
        request.setAttribute("startTime", startTime);
        System.out.println("拦截的方法" + ((HandlerMethod)handler).getMethod().getName());
        //
        return true;
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LocalDateTime startTime = (LocalDateTime) request.getAttribute("startTime");
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        long millis = duration.toMillis();
        System.out.println("Interceptor耗时为：" + millis + "毫秒");
    }

    /**
     * 控制器方法执行后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Interceptor执行结束");
        System.out.println(ex);

    }
}

```

**配置类**

```java
@Configuration
public class WebMVCInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     *
     * @param registry 拦截器的注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
	}
}
```
**拦截器概念**

Java 里的拦截器是动态拦截action调用的对象。它提供了一种机制可以使开发者可以定义在一个action执行的前后执行的代码，也可以在一个action执行前阻止其执行，同时也提供了一种可以提取action中可重用部分的方式。在 AOP（Aspect-Oriented Programming，面向切面编程）中拦截器用于在某个方法（包括构造器）或字段被访问之前进行拦截，然后在之前或之后加入某些操作。特别地，现阶段 Spring 自身仅支持基于方法的拦截操作！如果基于方法的拦截操作不能满足需求，可以使用 AspectJ 与 Spring 进行集成，以实现更细粒度或更多方面的拦截操作。

（1） preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用。

SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。

（SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返回值为false，当preHandle的返回值为false的时候整个请求就结束了。）

重写preHandle方法，在请求发生前执行。


（2）这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。

postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操作。


（3）该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。

该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行。

**具体实现**

##### 单个拦截器

###### 1.新建拦截器

```
	public class Test1Interceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("执行preHandle方法-->01");
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("执行postHandle方法-->02");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("执行afterCompletion方法-->03");
	}
}
复制代码
```

###### 2.配置拦截器

```
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	/*
	 * 拦截器配置
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册自定义拦截器，添加拦截路径和排除拦截路径
		registry.addInterceptor(new Test1Interceptor()) // 添加拦截器
				.addPathPatterns("/**") // 添加拦截路径
				.excludePathPatterns(// 添加排除拦截路径
						"/hello").order(0);//执行顺序
		super.addInterceptors(registry);
	}
```

##### 多个拦截器

###### 1.新建两个拦截器

Test1Interceptor

```
public class Test1Interceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("执行Test1Interceptor preHandle方法-->01");
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("执行Test1Interceptor postHandle方法-->02");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("执行Test1Interceptor afterCompletion方法-->03");
	}
}
复制代码
```

Test2Interceptor

```
public class Test2Interceptor extends HandlerInterceptorAdapter{


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("执行Test2Interceptor preHandle方法-->01");
		return true;
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("执行Test2Interceptor postHandle方法-->02");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("执行Test2Interceptor afterCompletion方法-->03");
	}
}
```

###### 2.配置拦截器

```
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	/*
	 * 拦截器配置
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册自定义拦截器，添加拦截路径和排除拦截路径
		registry.addInterceptor(new Test1Interceptor()) // 添加拦截器1
				.addPathPatterns("/**") // 添加拦截路径
				.excludePathPatterns(// 添加排除拦截路径
						"/hello")
				.order(0);
		registry.addInterceptor(new Test2Interceptor()) // 添加拦截器2
				.addPathPatterns("/**") // 添加拦截路径
				.excludePathPatterns(// 添加排除拦截路径
						"/test1")
				.order(1);
		super.addInterceptors(registry);
	}

}
```



#### 过滤器拦截器的区别

1.拦截器是基于java的反射机制的，而过滤器是基于函数回调。 

2.拦截器不依赖于servlet容器，而过滤器依赖于servlet容器。

 3.拦截器只能对Controller请求起作用，而过滤器则可以对几乎所有的请求起作用。

 4.拦截器可以访问action上下文、值栈里的对象，而过滤器不能访问。

 5.在Controller的生命周期中，拦截器可以多次被调用，而过滤器只能在容器初始化时被调用一次。

 6.拦截器可以获取IOC容器中的各个bean，而过滤器不行。这点很重要，在拦截器里注入一个service，可以调用业务逻辑。



#### 切片（Aspect ）

![1583847477271](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1583847477271.png)

##### 配置切面类

**引入依赖**

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```



```java
@Aspect
@Component
public class TimeAspect {

    /**
     * 声明切入点
     * 第一个 "*", 代表任何返回值
     * 第二个 "*"，代表此类中的任何方法
     */
    @Around(value = "execution(* com.kxj.controller.UserController.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("time aspect start");
        LocalDateTime startTime = LocalDateTime.now();
        Object[] args = joinPoint.getArgs();
        Arrays.asList(args).stream().forEach(arg -> System.out.println("请求参数为：" + arg));

        Object object = joinPoint.proceed();

        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("time aspect end");
        Duration duration = Duration.between(startTime, endTime);
        System.out.println("耗时：" + duration.minus(duration));
        return object;
    }

}
```



#### 过滤器、拦截器、切片区别

过滤器可以拿到原始Http请求和响应的信息，但是不可以拿到真正处理原始Http请求和响应的方法信息

拦截器可以拿到原始Http请求和响应的信息，也可以拿到真正处理原始Http请求和响应的方法信息，但是拿不到 方法被调用的时候真正调用的参数的值

切片可以 方法被调用的时候真正调用的参数的值 ，但是拿不到原始Http请求和响应的信息

![1583848933942](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1583848933942.png)



### 文件的上传和下载

#### 文件上传

```java
 @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        String filePath = "E:/githubResp/SpringBoot-Demo/Restful/src/main/resources";
        File localFile = new File(filePath,
                LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + ".txt");
        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
```

```java

    /**
     * 测试文件上传，上传文件字符串
     *
     * @throws Exception
     */
    @Test
    public void testUpload() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "multipart/form-data", "hello world".getBytes("UTF-8"));

        String content = mockMvc.perform(MockMvcRequestBuilders.multipart("/file").file(multipartFile))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("content:" + content);
    }
```



#### 文件下载

**把文件写入到响应中**

```java
 /**
     * 文件下载
     */
    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(
                    new File("E:/githubResp/SpringBoot-Demo/Restful/src/main/resources/" + id + ".txt"));
            outputStream = response.getOutputStream();
            response.setContentType("application/x-download");

            /**
             * test.txt 下载下来的文件名
             */
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            /**
             * 把文件的输入流复制到输出流中
             * 即把文件写入到响应中
             */
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            outputStream.close();
            inputStream.close();
        }

    }
```





### 异步处理Rest服务

在我们的实际生产中，常常会遇到下面的这种情况，某个请求非常耗时(大约5s返回)，当大量的访问该请求的时候，再请求其他服务时，会造成没有连接使用的情况，造成这种现象的主要原因是，我们的容器(tomcat)中线程的数量是一定的，例如500个，当这500个线程都用来请求服务的时候，再有请求进来，就没有多余的连接可用了，只能拒绝连接。要是我们在请求耗时服务的时候，能够异步请求(请求到controller中时，则容器线程直接返回，然后使用系统内部的线程来执行耗时的服务，等到服务有返回的时候，再将请求返回给客户端)，那么系统的吞吐量就会得到很大程度的提升了。当然，大家可以直接使用Hystrix的资源隔离来实现，今天我们的重点是spring mvc是怎么来实现这种异步请求的

可以先释放容器分配给请求的线程与相关资源，减轻系统负担，释放了容器所分配线程的请求，其响应将被延后，可以在耗时处理完成（例如长时间的运算）时再对客户端进行响应。

**一句话：增加了服务器对客户端请求的吞吐量**（实际生产上我们用的比较少，如果并发请求量很大的情况下，我们会通过nginx把请求负载到集群服务的各个节点上来分摊请求压力，当然还可以通过消息队列来做请求的缓冲）**。**

![1584020311489](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584020311489.png)

#### 使用Runnale异步处理Rest服务

**副线程必须由主线程掉起**

 ```java
public class AsyncController {

    @GetMapping("/order")
    public Callable<String> order() {
        log.info("主线程开始执行............");

        Callable<String> result = () -> {
            log.info("副线程执行");
            Thread.sleep(1000);
            log.info("副线程返回");
            return "success";
        };

        log.info("主线程返回.......");
        return result;

    }
}
 ```



#### 使用DeferredResult异步处理Rest服务

![1584024346860](C:\Users\小K\AppData\Roaming\Typora\typora-user-images\1584024346860.png)



**模拟下单的请求**

​	 相比于`callable`，`DeferredResult`可以处理一些相对复杂一些的业务逻辑，最主要还是可以在另一个线程里面进行业务处理及返回，即可在两个完全不相干的线程间的通信。 

```java
 @GetMapping
    public DeferredResult<String> orders() {
        log.info("主线程开始");

        // 模拟生成订单号
        String orderNumber = RandomStringUtils.randomNumeric(8);
        // 发送订单请求
        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);

        log.info("主线程返回");
        return result;

    }
```

```java
/**
 * @author kxj
 * @date 2020/3/12 23:14
 * @desc 模拟下单
 */
@Component
@Slf4j
public class MockQueue implements Serializable {
    private static final long serialVersionUID = 3844418539989931372L;

    // 下单
    private String placeOrder;

    // 订单完成
    private String completeOrder;

    public MockQueue() {
    }

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            log.info("接到下单请求：" + placeOrder);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.completeOrder = placeOrder;
            log.info("下单请求处理完毕：" + placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}

```

```java
/**
 * @author kxj
 * @date 2020/3/12 23:27
 * @desc 线程间传递DeferredResult
 */

@Component
public class DeferredResultHolder {

    private Map<String, DeferredResult<String>> map = new HashMap<>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
}
```

```java
/**
 * @author kxj
 * @date 2020/3/12 23:30
 * @desc 模拟消息队列的监听器
 */
@Component
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            while (true) {
                if (StringUtils.isEmpty(mockQueue.getCompleteOrder())) {
                    String orderNumber = mockQueue.getCompleteOrder();
                    log.info("返回订单处理结果：" + orderNumber);
                    deferredResultHolder.getMap().get(orderNumber).setResult("place order success");
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
```



#### 异步处理配置

```java
@Configuration
public class WebMVCInterceptorConfig implements WebMvcConfigurer {
    /**
     * 异步请求的配置
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 设置一个异步线程池
        // 默认使用SimpleAsyncTaskExecutor
        configurer.setTaskExecutor(new SimpleAsyncTaskExecutor());

        // 设置异步request等待被处理的超时时间
        // 默认的大小为10秒
        configurer.setDefaultTimeout(100);

        // 设置Callable任务的拦截器
        configurer.registerCallableInterceptors(new CallableProcessingInterceptor(){});

        // 设置Callable任务的带有延迟的拦截器
        configurer.registerDeferredResultInterceptors(new DeferredResultProcessingInterceptor() {});
    }
}
```









































