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



### ThreadLocal的核心方法

#### 1、set方法

```java
/**
 * 设置当前线程对应的ThreadLocal的值
 * 
 * @param value 将要保存在当前线程对应的ThreadLocal的值
 */
public void set(T value) {
  // 获取当前线程
  Thread t = Thread.currentThread();
  // 获取此线程对象中维护的ThreadLocalMap对象
  ThreadLocalMap map = getMap(t);
  // 判断map是否存在
  if (map != null)
    // 存在则调用map.set设置此实体的entry
    map.set(this, value);
  else
    // 1、当前线程Thread 不存在ThreadLocalMap对象
    // 2、则调用createMap进行ThreadLocalMap对象的初始化
    // 3、并将t(当前线程)和value(t对应的值)作为第一个entry存放到ThreadLocalMap对象中
    createMap(t, value);
}

/**
 * 获取当前线程Thread对应维护的ThreadLocalMap
 * 
 * @param value 将要保存在当前线程对应的ThreadLocal的值
 */
ThreadLocalMap getMap(Thread t) {
  return t.threadLocals;
}

/**
 * 创建当前线程Thread对应维护的ThreadLocalMap
 *
 * @param t 当前线程
 * @param firstValue 存放到map中的第一个entry的值
 */
void createMap(Thread t, T firstValue) {
  // 这里的this是调用此方法的threadLocal
  t.threadLocals = new ThreadLocalMap(this, firstValue);
}

```

**代码执行流程**

1. 首先获取当前线程，并根据当前线程获取一个Map
2. 如果获取的Map不为空，则将参数设置到Map中(当前threadLocal的引用作为key)
3. 如果Map为空，则给该线程创建Map，并设置初始值

#### 2、get方法

```java
/**
 * 返回当前线程中保存ThreadLocal的值
 * 如果当前线程没有此ThreadLocal变量
 * 则会通过调用{@link #initialValue}方法进行初始化值
 * 
 * @return 返回当前线程对应的此ThreadLocal的值
 */
public T get() {
  // 获取当前线程
  Thread t = Thread.currentThread();
  // 获取此线程对象中维护的ThreadLocal对象
  ThreadLocalMap map = getMap(t);
  // 如果此map存在
  if (map != null) {
    // 以当前的ThreadLocal为key, 调用getEntry获取对应存储的entry
    ThreadLocalMap.Entry e = map.getEntry(this);
    // 对e进行判空
    if (e != null) {
      @SuppressWarnings("unchecked")
      // 获取存储实体e对应的value值
      // 即为我们想要的当前线程对应的ThreadLocal的值
      T result = (T)e.value;
      return result;
    }
  }
  // 初始化
  // 第一种情况：map不存在，表示此线程没有维护的ThreadLocal对象
  // 第二种情况：map存在，但是没有于当前ThreadLocal关联的entry
  return setInitialValue();
}

/**
 * 初始化
 */
private T setInitialValue() {
  // 调用initalValue获取初始化的值
  // 此方法可以被子类重写，如果不重写默认返回为null
  T value = initialValue();
  // 获取当前线程对象
  Thread t = Thread.currentThread();
  // 获取此线程对象中维护的ThreadLocalMap对象
  ThreadLocalMap map = getMap(t);
  if (map != null)
    // 存在则调用map.set设置此实体的entry
    map.set(this, value);
  else
    // 1）当前线程Thread 不存在ThreadLocalMap对象
    // 2）则调用createMap进行ThreadLocalMap对象的初始化
    // 3）并将 t(当前线程)和value(t对应的值)作为第一个entry存放至ThreadLocalMap中
    createMap(t, value);
  return value;
}
```



#### 3、remove方法

#### 4、initialValue方法





