### Restful

##### 特点

- 用URL描述资源
- 使用HTTP方法描述行为。使用HTTP状态码来表示不同的结果
- 使用json交互
- Restful知识一种风格，并不是强制的标准

- ##### 用HTTP方法体现对资源的操作（动词）

  - GET ： 获取、读取资源
  - POST ： 添加资源
  - PUT ： 修改资源
  - DELETE ： 删除资源

##### 规范

| 不符合REST的接口URI      | 符合REST接口URI        | 功能       |
| :----------------------- | :--------------------- | :--------- |
| GET /api/getCar          | GET  /api/cars/{id}    | 获取一辆车 |
| GET /api/getCars         | GET   /api/cars        | 获取所有车 |
| GET /api/addCars         | POST  /api/cars        | 添加一辆车 |
| GET /api/editCars/{id}   | PUT     /api/cars/{id} | 修改一辆车 |
| GET /api/deleteCars/{id} | DELETE /api/cars/{id}  | 删除一辆车 |

##### HTTP状态码

通过HTTP状态码体现动作的结果,不要自定义

```
200 OK 
400 Bad Request 
500 Internal Server Error
```

- ##### 资源**过滤、排序、选择和分页**的表述

![img](C:\Users\小K\Pictures\springboot\41b90d46f19fe1277a5fc6e2828bbd39_845x190.png)



##### MockMvc

- get请求也可以用对象接收
- post请求也可以用参数接收

+ 使用方式

  ```java
  @Autowiredprivate WebApplicationContext wac;private MockMvc mockMvc;@Beforepublic void setup() {    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();}/** * 查询 * @throws Exception */@Testpublic void whenQuerySuccess() throws Exception {    //发送get请求    mockMvc.perform(MockMvcRequestBuilders.get("/user")            .param("username", "tom")            .param("age", "21")            .param("sex","2")            //请求的格式            .contentType(MediaType.APPLICATION_JSON_UTF8))            //请求的相应期待是200            .andExpect(MockMvcResultMatchers.status().isOk())            //返回的长度是3            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));}
  ```

##### **常用注解**

- RestController     标明一个Java类提供RestAPI
- RequestMapping及其变体，映射HTTP请求url到java方法  
- RequestParam     映射请求参数到Java方法上的参数
- PathVariable         映射URL片段到java方法的参数
- JsonView                控制返回的json视图
  1. 使用接口来声明多个视图
  2. 在值对象的get方法上指定视图
  3. 在Controller方法上指定视图
- PageDefault          指定分页参数默认值



###### @PathVariable

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

###### @JsonView

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

###### @PageableDefault

- Pageable 是[spring](http://lib.csdn.net/base/javaee) Data库中定义的一个接口，该接口是所有分页相关信息的一个抽象，通过该接口，我们可以得到和分页相关所有信息

- Pageable定义了很多方法，但其核心的信息只有两个：一是分页的信息（page、size），二是排序的信息。

- 只能用于get请求

- post请求可以添加在url后，也可以接收到。eg:http://localhost:8080/user?page=1&size=2

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

