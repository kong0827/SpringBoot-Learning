## ThreadLocal

### 简介

`ThreadLocal` 类是用来提供线程内部的局部变量。这种变量在多线程环境下访问(通过get和set方法访问)时能够保证各个线程的变量相对独立于其他线程内的变量。`ThreadLocal` 实例通常来说都是 `private static类型的，用来关联线程和线程上下文。

我们可以得知 `ThreadLocal`的作用是：提供线程内的局部变量，不同线程之间不会相互干扰，这种变量在线程的生命周期内起作用，减少同一个线程内多个函数或组件之间一些公共变量传递的复杂度。

**总结**

1. 线程并发：在多线程并发的场景下
2. 传递数据：我们可以ThreadLocal在同一线程，不同组件中传递公共变量
3. 线程隔离：每个线程的变量都是独立的，不会相互影响



### 基本使用

#### 1、常用方法

| 方法声明                 | 描述                       |
| ------------------------ | -------------------------- |
| ThreadLocal()            | 创建ThreadLocal对象        |
| public void set(T value) | 设置当前线程绑定的局部变量 |
| public T get()           | 获取当前线程绑定的局部变量 |
| Public void remove()     | 移除当前线程绑定的局部变量 |

#### 2、使用案例

```java
public class ThreadLocal_Demo_01 {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {
        ThreadLocal_Demo_01 demo_01 = new ThreadLocal_Demo_01();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                demo_01.setContent(Thread.currentThread().getName() + "的数据");
                System.out.println("---------");
                System.out.println(Thread.currentThread().getName() + "---->" + demo_01.getContent());
            });
            thread.setName("线程"+i);
            thread.start();
        }
    }
}

```

**运行结果**

线程0---->线程3的数据

线程3---->线程3的数据

线程2---->线程4的数据

线程1---->线程4的数据

线程4---->线程4的数据



从结果可以看出多个线程在访问同一变量的时候出现异常，线程间的数据没有隔离，接着采用ThreadLocal的方式来解决这个问题

```java
public class ThreadLocal_Demo_02 {
    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public String getContent() {
        return tl.get();
    }

    public void setContent(String content) {
        tl.set(content);
    }

    public static void main(String[] args) {
        ThreadLocal_Demo_02 demo_02 = new ThreadLocal_Demo_02();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                demo_02.setContent(Thread.currentThread().getName() + "的数据");
                System.out.println("---------");
                System.out.println(Thread.currentThread().getName() + "---->" + demo_02.getContent());
            });
            thread.setName("线程" + i);
            thread.start();
        }
    }
}
```

打印结果

![image-20210726012231448](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210726012231448.png)

从结果看，使用`ThreadLocal`很好的解决了多线程之间的数据隔离问题，十分方便