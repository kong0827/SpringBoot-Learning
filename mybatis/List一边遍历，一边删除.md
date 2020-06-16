## 并发修改异常产生原因及解决方案

### 触发案例

需求：对集合进行一次遍历，删除集合中 `a`元素 。

很多人会写出以下代码，但是运行则会抛出异常。

```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
list.add("c");

for (String str : list) {
    if ("a".equals(str)) {
        list.remove("a");
    }
}
```

会出现并发修改异常`ConcurrentModificationException`，那么为什么会产生这个异常呢？

![1592296136610](E:\github_resp\SpringBoot-Demo\mybatis\src\main\resources\img\1592296136610.png)



### 报错原因

 	上述代码产生`ConcurrentModificationException`真正原因是`modCount`不一致。当遍历`ArrayList`时， `Iterator`的`next()`方法将跟踪`modCount`。如果通过添加或删除元素来修改集合，则`modCount`将更改，并且与预期的`modCount`不匹配，因此`Iterator`将抛出 `ConcurrentModificationException `

	#### 源码解析

`ArrayList`有一个**`modCount`**的成员变量，继承自` AbstractList `

```java
protected transient int modCount = 0;
```

**`modCount`**记录了集合发生**结构性**修改的次数。

**结构性的修改**是一种操作：添加，删除一个或者多个元素，或者明显的重新调整背后数组的大小叫做结构性修改；仅仅修改元素的内容并不叫结构性修改。

```java
// 添加
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // 调用的方法内部 modCount+1
    elementData[size++] = e;
    return true;
}

// 指定位置添加
public void add(int index, E element) {
    rangeCheckForAdd(index);
    ensureCapacityInternal(size + 1);  // 调用的方法内部 modCount+1
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}

private void ensureCapacityInternal(int minCapacity) {
    ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}

private void ensureExplicitCapacity(int minCapacity) {
    modCount++;   // modCount发生改变 

    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

// 删除指定索引位置上的对象
public E remove(int index) {
    rangeCheck(index);  // 检查此索引是否存在

    modCount++; 		// modCount+1
    E oldValue = elementData(index);
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work

    return oldValue;
}
// 删除
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);    // 所调用方法内部modCount+1
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);	// 所调用方法内部modCount+1
                return true;
            }
    }
    return false;
}

 private void fastRemove(int index) {
     modCount++;			// modCount发生改变 
     int numMoved = size - index - 1;
     if (numMoved > 0)
         System.arraycopy(elementData, index+1, elementData, index,
                          numMoved);
     elementData[--size] = null; // clear to let GC do its work
 }
// .....
```

从`ArrayList`的源码中我们可以看到，当集合执行添加或者删除操作时，`modCount`会发生改变（`moCount++`）。其他场景`modCount`改变可以查看`ArrayList`方法。



那么`modCount`改变为什么会引起并发修改异常`ConcurrentModificationException`呢？

其实当程序执行`forEach`遍历时（如案例），实际是 调用`Itertor`的`hasNext`方法，然后再调用`next`方法。

`Itertor` 中维护了`cursor`， `lastRet`，`expectedModCount` 三个成员变量。

```java
private class Itr implements Iterator<E> {
    int cursor;       // 即将取出元素的索引
    int lastRet = -1; // 上一次取出的元素的索引
    int expectedModCount = modCount;  // 记录迭代器被创建时的集合结构改变的次数
	// .....
}
```

`hasNext()` 方法：判断是否能够继续遍历

```java
// 判断是否还能继续遍历，当cursor=size时，后续再取值或者删除都会越界
public boolean hasNext() {
    return cursor != size;
}
```

`next ` 方法：取出数据

```java
public E next() {
    /**
    * 判定expectedModCount与modCount之间是否相等，如果不相等，则抛出
    * concurrentModificationException
    **/
    checkForComodification();
    int i = cursor;   // 把需要取出元素的索引赋值，
    if (i >= size)
        throw new NoSuchElementException();
    Object[] elementData = ArrayList.this.elementData;
    if (i >= elementData.length)
        throw new ConcurrentModificationException();
    cursor = i + 1;    // 加一是为了使cursor变成下一次遍历要取的值的索引
    return (E) elementData[lastRet = i]; // 给lastRet赋值，此时的i已经变成了上一次取出值的索引
}

final void checkForComodification() {
    if (modCount != expectedModCount)
        // 当不相等时，跑出异常
        throw new ConcurrentModificationException();
}
```



当在遍历的时候执行`remove` 方法，`modCount` 会发生改变`modCount++`, 但是 `expectedModCount`并不会发生改变。因此在执行 `next` 方法的 `checkForComodification` 时，`modCount++ ` 和 `expectedModCount` 并不相等，会抛出`ConcurrentModificationException`。

### 解决方案

#### 1、直接使用迭代器

```java
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    if ("a".equals(iterator.next())) {
        iterator.remove();
    }
}
```

这里使用`Iterator` 的 `remove` 方法，那么为什么这里删除不会产生`ConcurrentModificationException`呢？

```java
public void remove() {
    // lastRet默认为-1，使用remove必须先使用next方法对lastRet进行赋值
    if (lastRet < 0)
        throw new IllegalStateException();
    // 检查是否相等，此时未执行删除操作，modCount是expectedModCount相等的
    checkForComodification();
    try {
        ArrayList.this.remove(lastRet);    // 删除上一个next取出的元素
        cursor = lastRet;	// 修正，将删除元素的索引赋值下一个取出的元素索引，否则会错失一位
        lastRet = -1;		// 上一次取出的元素的索引重新置为-1，下次next再赋值，防止重复删除	
        expectedModCount = modCount;  // 将modCount赋值给expectedModCount，保持相等
    } catch (IndexOutOfBoundsException ex) {
        throw new ConcurrentModificationException();
    }
}
```

#### 2、使用`Stream` 流过滤

```java
List<String> collect = list
    .stream()
    .filter(str -> !"a".equals(str))
    .collect(Collectors.toList());
```

#### 3、使用`removeIf()`

```java
list.removeIf(str -> "a".equals(str));
```

#### 4、等等



### 总结

- ` ConcurrentModfiicationException`产生原因是因为`modCount`不一致
- 在遍历集合的时候不能直接调用集合的`remove`方法，会造成集合的结构性改变
- `ArrayList`是线程不安全的，当一个线程去改变了`modCount`时，其他线程也会发生错误

 



