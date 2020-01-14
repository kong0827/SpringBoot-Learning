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