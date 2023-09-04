# springboot整合websocket最基础入门使用教程详解

这篇文章主要介绍了springboot整合websocket最基础入门使用教程详解,本文给大家介绍的非常详细，对大家的学习或工作具有一定的参考借鉴价值，需要的朋友可以参考下

**项目最终的文件结构**

![image.png](https://kong-blog.oss-cn-shanghai.aliyuncs.com/b0517c6c674141a1aa3b4d55ee988bf7-20230903232650553.png)

1 添加maven依赖

```
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
<dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.12</version>
</dependency>
```

2 编写配置类 WebSocketConfig

```
package cn.huawei.socket_test_1.config;
  
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
  
@Component
public class WebSocketConfig {
  
  /**
   * ServerEndpointExporter 作用
   *
   * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
   *
   * @return
   */
  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }
}
```

3 编写核心业务类 WebSocket

```
package cn.huawei.socket_test_1.websock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
  
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
  
/**
 * @ServerEndpoint 注解的作用
 *
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
  
@Slf4j
@Component
@ServerEndpoint("/websocket/{name}")
public class WebSocket {
  
  /**
   * 与某个客户端的连接对话，需要通过它来给客户端发送消息
   */
  private Session session;
  
  /**
   * 标识当前连接客户端的用户名
   */
  private String name;
  
  /**
   * 用于存所有的连接服务的客户端，这个对象存储是安全的
   * 注意这里的kv,设计的很巧妙，v刚好是本类 WebSocket (用来存放每个客户端对应的MyWebSocket对象)
   */
  private static ConcurrentHashMap<String,WebSocket> webSocketSet = new ConcurrentHashMap<>();
  
  
  /**
   * 连接建立成功调用的方法
   * session为与某个客户端的连接会话，需要通过它来给客户端发送数据
   */
  @OnOpen
  public void OnOpen(Session session, @PathParam(value = "name") String name){
    log.info("----------------------------------");
    this.session = session;
    this.name = name;
    // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
    webSocketSet.put(name,this);
    log.info("[WebSocket] 连接成功，当前连接人数为：={}",webSocketSet.size());
    log.info("----------------------------------");
    log.info("");
  
    GroupSending(name+" 来了");
  }
  
  /**
   * 连接关闭调用的方法
   */
  @OnClose
  public void OnClose(){
    webSocketSet.remove(this.name);
    log.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
  
    GroupSending(name+" 走了");
  }
  
  /**
   * 收到客户端消息后调用的方法
   */
  @OnMessage
  public void OnMessage(String message_str){
    log.info("[WebSocket] 收到消息：{}",message_str);
    //判断是否需要指定发送，具体规则自定义
    //message_str的格式 TOUSER:user2;message:aaaaaaaaaaaaaaaaaa;
    if(message_str.indexOf("TOUSER") == 0){
      //取出 name和message的值
      String[] split = message_str.split(";");
      String[] split1 = split[0].split(":");
      String[] split2 = split[1].split(":");
      String name = split1[1];
      String message = split2[1];
      //指定发送
      AppointSending(name,message);
    }else{
      //群发
      GroupSending(message_str);
    }
  }
  
  /**
   * 发生错误时调用
   * @param session
   * @param error
   */
  @OnError
  public void onError(Session session, Throwable error){
    log.info("发生错误");
    error.printStackTrace();
  }
  
  /**
   * 群发
   * @param message
   */
  public void GroupSending(String message){
    for (String name : webSocketSet.keySet()){
      try {
        webSocketSet.get(name).session.getBasicRemote().sendText(message);
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }
  
  /**
   * 指定发送
   * @param name
   * @param message
   */
  public void AppointSending(String name,String message){
    try {
      webSocketSet.get(name).session.getBasicRemote().sendText(message);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
```

4 因前端不熟悉，所以使用 [http://coolaf.com/tool/chattest](http://coolaf.com/tool/chattest?spm=a2c6h.12873639.article-detail.7.36312f9ds1uXQs) 在线测试
因代码中 @ServerEndpoint("/websocket/{name}") 是这样定义的，所以 通过下面的方式连接上去，测试群发和指定发送

![image.png](https://kong-blog.oss-cn-shanghai.aliyuncs.com/44fa365bc1a84fe6ac37ba0b3ae966a3-20230903232650569.png)

因java代码中这样定义的消息，可以指定发送某个人，测试

![image.png](https://kong-blog.oss-cn-shanghai.aliyuncs.com/f9c33f6ec23c4a0bbd28b7752b0ba801-20230903232650581.png)

到此这篇关于springboot整合websocket最基础入门使用教程详解的文章就介绍到这了！









https://cloud.tencent.com/developer/article/2168088

https://www.niaoyun.com/info/details/30363.html