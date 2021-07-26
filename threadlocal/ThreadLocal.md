## ThreadLocal

### 简介

`ThreadLocal` 类是用来提供线程内部的局部变量。这种变量在多线程环境下访问(通过get和set方法访问)时能够保证各个线程的变量相对独立于其他线程内的变量。`ThreadLocal` 实例通常来说都是 `private static`类型的，用来关联线程和线程上下文。

我们可以得知 `ThreadLocal`的作用是：提供线程内的局部变量，不同线程之间不会相互干扰，这种变量在线程的生命周期内起作用，减少同一个线程内多个函数或组件之间一些公共变量传递的复杂度。

**总结**

1. 线程并发：在多线程并发的场景下
2. 传递数据：我们可以`ThreadLocal`在同一线程，不同组件中传递公共变量
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

![image-20210726225152360](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210726225152360.png)



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



#### 3、ThreadLocal和synchronized关键字区别

使用`synchronized`关键字同样可以解决线程并发问题

```java
for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                synchronized (ThreadLocal_Demo_01.class) {
                    demo_01.setContent(Thread.currentThread().getName() + "的数据");
                    System.out.println("---------");
                    System.out.println(Thread.currentThread().getName() + "---->" + demo_01.getContent());
                }
            });
            thread.setName("线程" + i);
            thread.start();
        }
}
```



**区别**

`ThreadLocal`和`sychronized`关键字都用于处理多线程并发访问变量的问题，不过两者的处理问题的角度和思路不同。

|        | sychronized                                                  | ThreadLocal                                                  |
| ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 原理   | 同步机制采用“以时间换空间”的方式，只提供了一份变量，让不同的线程排队访问 | `ThreadLocal `采用“以空间换时间”的方式，为每一个线程都提供了一份变量的副本，从而实现同时访问而互不干扰 |
| 侧重点 | 多个线程之间访问资源的同步                                   | 多线程中让每个线程之间数据相互隔离                           |

**总结**

在刚才的案例中，虽然使用`ThreadLocal`和`sychronized`都能解决问题，但是使用`ThreadLocal`更为合适，因为这样可以使程序拥有更高的并发性



**ThreadLocal的好处**

1、传递数据：保存每个线程保定的数据，在需要的地方可以直接获取，避免参数直接传递带来的代码耦合问题

2、线程隔离：各个线程之间的数据相互隔离却又具备并发性，避免同步方法带来的性能损失



### ThreadLocal的内部结构

#### JDK1.8之前

每个`ThreadLocal`都创建一个`Map`，然后用线程作为`Map`的`key`，要存储的局部变量作为`Map`的`value`，这样能够达到各个线程的局部变量隔离的效果。

![1582788729557](https://gitee.com/kongxiangjin/images/raw/master/img/008.png)

#### JDK1.8之后

在JDK8中的`ThreadLocal`的设计是：每个`Thread`维护一个`ThreadLocalMap`，这个`Map`的`key`是`ThreadLocal`实例本身，`value`才是真正要存储的值`Object`。

具体的过程是这样的：

1. 每个`Thread`线程内部都有一个`Map`（`ThreadLocalMap`）

2. `Map`里面存储`ThreadLocal`对象（key) 和线程的变量副本（value)

3. `Thread`内部的`Map`是由`ThreadLocal`维护的，由`ThreadLocal`负责向map获取和设置线程的变量值

4. 对于不同的线程，每次获取副本值时，别的线程并不能获取到当前线程的副本值，形成了副本的隔离，互不干扰

   ![1574155226793](https://gitee.com/kongxiangjin/images/raw/master/img/004.png)

#### 设计的好处

1、这样设计之后每个`Map`存储的`Entry`数量会变少。因为之前存储的数量由`Thread`的数量决定，现在由`ThreadLocal`的数量决定。在实际运用当中，往往`ThreadLocal`的数量要少于`Thread`的数量

2、当`Thread`销毁之后，对应的`ThreadLocalMap`也会随之销毁，减少内存的使用



