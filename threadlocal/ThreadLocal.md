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

在处理多线程并发安全的方法中，最常用的方法就是使用锁，通过锁来控制不同线程对临界区的访问。但是，无论是什么样的锁，乐观锁还是悲观锁，都会在并发冲突的时候对性能产生一定的影响。

ThreadLocal 提供了一种与众不同的线程安全方式，它不是在发生线程冲突时想办法解决冲突，而是彻底的避免了冲突的发生

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



### ThreadLocal中线程隔离原理

```java
public class Thread {
     /* 
     * ThreadLocal values pertaining to this thread. This map is maintained
     * by the ThreadLocal class.
     */
    ThreadLocal.ThreadLocalMap threadLocals = null;

    /*
     * InheritableThreadLocal values pertaining to this thread. This map is
     * maintained by the InheritableThreadLocal class.
     */
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
}
```

Threadlocals 和inheritableThreadLocals 是一个Thread 的两个属性，所以每个Thread 的threadlocales 是孤立的和排他的。在Thread的init方法中，父Thread创建子Thread时，会复制inheritableThreadLocals的值，但不会复制threadlocals的值

```java
// 线程中的init方法
private void init(ThreadGroup g, Runnable target, String name, 
                      long stackSize, AccessControlContext acc) { 
    ... 
        //当父线程的inheritableThreadLocals值不为null时
        //所有inheritable中的值都会被传递到子线程
        if (parent.inheritableThreadLocals != null) 
            this.inheritableThreadLocals = 
               // 
                ThreadLocal.createInheritedMap(parent.inheritableThreadLocals); 
     ... 
    } 


// 
static ThreadLocalMap createInheritedMap(ThreadLocalMap parentMap) {
        return new ThreadLocalMap(parentMap);
    }
```



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

/**
 * 设置threadlocal关联的value值
 */
private void set(ThreadLocal<?> key, Object value) {
  
  Entry[] tab = table;
  int len = tab.length;
  
  // 查找索引位置
  int i = key.threadLocalHashCode & (len-1);

  for (Entry e = tab[i];
       e != null;
       // 下一个索引位置
       e = tab[i = nextIndex(i, len)]) {
    ThreadLocal<?> k = e.get();

    // 如果key相同，则替换value
    if (k == key) {
      e.value = value;
      return;
    }

    // 如果key为null，取代旧的entry
    if (k == null) {
      replaceStaleEntry(key, value, i);
      return;
    }
  }

  tab[i] = new Entry(key, value);
  int sz = ++size;
  if (!cleanSomeSlots(i, sz) && sz >= threshold)
    rehash();
}



private void replaceStaleEntry(ThreadLocal<?> key, Object value,
                                       int staleSlot) {
  Entry[] tab = table;
  int len = tab.length;
  Entry e;

  .......

    for (int i = nextIndex(staleSlot, len);
       (e = tab[i]) != null;
       i = nextIndex(i, len)) {
    ThreadLocal<?> k = e.get();

    // If we find key, then we need to swap it
    // with the stale entry to maintain hash table order.
    // The newly stale slot, or any other stale slot
    // encountered above it, can then be sent to expungeStaleEntry
    // to remove or rehash all of the other entries in run.
    if (k == key) {
      e.value = value;

      tab[i] = tab[staleSlot];
      tab[staleSlot] = e;

      // Start expunge at preceding stale entry if it exists
      if (slotToExpunge == staleSlot)
        slotToExpunge = i;
      cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
      return;
    }

    ........
}
  
/**
 * 删除一个陈旧的条目通过再处理任何可能碰撞条目躺陈旧槽和下一个空槽之间
 */
private int expungeStaleEntry(int staleSlot) {
  Entry[] tab = table;
  int len = tab.length;

  // 删除
  tab[staleSlot].value = null;
  tab[staleSlot] = null;
  size--;

  // Rehash until we encounter null
  Entry e;
  int i;
  for (i = nextIndex(staleSlot, len);
       (e = tab[i]) != null;
       i = nextIndex(i, len)) {
    ThreadLocal<?> k = e.get();
    if (k == null) {
      e.value = null;
      tab[i] = null;
      size--;
    } else {
      int h = k.threadLocalHashCode & (len - 1);
      if (h != i) {
        tab[i] = null;

        // Unlike Knuth 6.4 Algorithm R, we must scan until
        // null because multiple entries could have been stale.
        while (tab[h] != null)
          h = nextIndex(h, len);
        tab[h] = e;
      }
    }
  }
  return i;
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

**执行流程**

1. 首先获取当前线程，根据当前线程获取一个Map
2. 如果获取的Map不为空，则在Map中以ThreadLocal的引用作为key来获取Map中对应的Entry，否则转到4
3. 如果entry不为null，则返回e.value，否则转到4
4. Map为空或者e为空，则通过initialValue函数获取初始值value，然后用ThreadLocal的引用和value作为firstKey和firstValue创建新的Map



#### 3、remove方法

```java
/**
 * 删除当前线程中保存的ThreadLocal对应的实体entry
 */
public void remove() {
  // 获取当前线程对象中维护的ThreadLocalMap对象
   ThreadLocalMap m = getMap(Thread.currentThread());
   if (m != null)
     // 如果Map存在，则调用remove方法
     // 以当前ThreadLocal为key删除对应的实体entry
     m.remove(this);
 }
```

**执行流程**

1. 获取当前线程，并根据当前线程获取一个Map
2. 如果获取的Map不为空，则移除当前ThreadLocal对应的entry

#### 4、initialValue方法

```java
/**
 * 返货当前线程对应的ThreadLocal的初始值
 *
 * 此方法的第一此调用发生在当线程通过get方法访问此线程的ThreadLocal值时
 * 除非此线程先调用了set方法，在这种情况下，initalValue才不会被这个线程调用
 * 通常情况下，每个线程最多调用一次这个方法
 *
 * 这个方法仅仅简单的方法返回null
 * 如果程序员向ThreadLocal线程局部变量有一个除null以外的初始值
 * 必须通过子类继承的方法去重写此方法
 * 通常可以通过匿名内部类的方式实现
 *
 */
protected T initialValue() {
  return null;
}
```

**执行流程**

1. 这个方法是一个延迟调用方法，从上面的代码可知，在set方法还未调用而先调用了`get`方法时才执行，并且仅执行1次
2. 这个方法缺省实现直接返回一个`null`
3. 如果要返回除null之外的初始值，可以重写此方法



可以看出`set、get、remove`方法的底层原理比较简单，但都包含一个共同的特点，那就是使用`ThreadLocalMap`。



### ThreadLocalMap源码分析

`ThreadLocalMap` 是 `ThreadLocal` 中的一个静态内部类，本质上是一个简单的 `Map` 结构。 `key`是`ThreadLocal`类型，`value`是`ThreadLocal`保存的值，底层是一个Entry类型数组组成的数据结构

#### 基本结构

**成员变量**

```java
        /**
         * 初始容量
         */
        private static final int INITIAL_CAPACITY = 16;

        /**
         * 存放数据的table，Entry类
         * 必须是2的整数幂
         */
        private Entry[] table;

        /**
         * 数组中entrys的个数，可以用于判断table当前使用量是否超过阈值
         */
        private int size = 0;

        /**
         * 进行扩容的阈值，表使用量大于它的时候进行扩容
         */
        private int threshold; // Default to 0
```

**存储结构-Entry**

```java
/**
 * Entry集成WeakReference，并且使用ThreadLocal作为key
 * 如果key为null(entry.get() == null), 意味着key不在被引用
 * 因此这时候entry也可以从table中清除
 */
static class Entry extends WeakReference<ThreadLocal<?>> {
  /** The value associated with this ThreadLocal. */
  Object value;

  Entry(ThreadLocal<?> k, Object v) {
    super(k);
    value = v;
  }
}
```

​	 在ThreadLocalMap中，也是用Entry来保存K-V结构数据的。不过Entry中的key只能是ThreadLocal对象，这点在构造方法中已经限定死了。

​	另外，Entry继承WeakReference，也就是key（ThreadLocal）是弱引用，其目的是将ThreadLocal对象的生命周期和线程生命周期解绑。



#### 弱引用和内存泄漏

**（1） 内存泄漏相关概念**

内存泄漏主要有两种情况：一种是堆中申请的空间没有释放；另一个是对象不再使用，但仍在内存中。

- Memory overflow:内存溢出，没有足够的内存提供申请者使用。
- Memory leak: 内存泄漏是指程序中己动态分配的堆内存由于某种原因程序未释放或无法释放，造成系统内存的浪费，导致程序运行速度减慢甚至系统崩溃等严重后果。内存泄漏的堆积终将导致内存溢出。

**（2）  弱引用相关概念**

​	Java中的引用有4种类型： 强、软、弱、虚。当前这个问题主要涉及到强引用和弱引用：

​	**强引用（“Strong” Reference）**，就是我们最常见的普通对象引用，只要还有强引用指向一个对象，就能表明对象还“活着”，垃圾回收器就不会回收这种对象。

​	**弱引用（WeakReference）**，垃圾回收器一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。



![image-20210809005358741](https://gitee.com/kongxiangjin/images/raw/master/img/image-20210809005358741.png)

如果key使用弱引用，在使用完ThreadLocal后，threadLocal Ref被回收了

由于ThreadLocalMap中只持有ThreadLocal的弱引用，没有任何强引用指向threadLocal实例，所以threadlocal可以被gc回收，此时Entry中的key=null

但是在没有手动删除这个Entry以及CurrentThread依然运行的前提下，也存在有强引用连CurrentThreadRef-> currentThread ->threadLocalMap -> entry -> value，value不会被回收，而这块value永远不会访问到了，导致value内存泄漏



弱引用并不能解决内存泄漏问题，实际作用就是标记的不再使用的entry, 便于扫描到进行value置为null

**那既然key为null，调用get/set方法value也会被置为null，那么还有必要remove吗**

可能长时间不执行get、set方法

**假设使用强引用**

​	假设在业务代码中使用完ThreadLocal ，threadLocal Ref被回收了。

​	但是因为threadLocalMap的Entry强引用了threadLocal，造成threadLocal无法被回收。

​	在没有手动删除这个Entry以及CurrentThread依然运行的前提下，始终有强引用链 threadRef->currentThread->threadLocalMap->entry，Entry就不会被回收（Entry中包括了ThreadLocal实例和value），导致Entry内存泄漏。

​	也就是说，ThreadLocalMap中的key使用了强引用， 是无法完全避免内存泄漏的。



**为什么会出现内存泄漏**

比较以上两种情况，可以发现内存泄漏的发生跟ThreadLocalMap中的key是否使用弱引用是没有关系的，那么内存泄漏的真正原因是什么呢？

可以发现，在以上两种内存泄漏情况下，都有两个前提

1. 没有手动删除这个Entry
2. CurrentThread依然在运行

第一点很好理解，之哟啊在使用完ThreadLocal，调用remove方法删除对应的Entry，就能避免内存泄漏

第二点则是因为ThreadLocalMap是Thread的一个属性，被当前线程引用，所以它的声明周期和Thread一样长。那么使用完ThreadLocal，如果当前线程也随之执行结束，ThreadLocalMap自然也会 被gc回收，从根源避免了内存泄漏。

综上，**ThreadLocal内存泄漏的根源是**：由于ThreadLocalMap的生命周期跟Thread一样长，如果没有手动删除对应key就会导致内存泄漏。



**为什么要使用弱引用**

经过根系，发现无论ThreadLocalMap中的key使用哪种类型引用都无法完全避免内存泄漏，跟强弱引用无关。那么为什么要使用弱引用呢

事实上，在ThreadLocalMap中的set/getEntry方法中，会对key为null（也即是ThreadLocal为null）进行判断，如果为null的话，那么是会对value置为null的。

这就意味着使用完ThreadLocal，CurrentThread依然运行的前提下，就算忘记调用remove方法，**弱引用比强引用可以多一层保障**：弱引用的ThreadLocal会被回收，对应的value在下一次ThreadLocalMap调用set,get,remove中的任一方法的时候会被清除，从而避免内存泄漏。



#### hash冲突





1、ThreadLocal是什么

2、ThreadLocal的应用

spring @transactional

3、ThreadLocal内存泄漏





































