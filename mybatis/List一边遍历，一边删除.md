### List一边遍历，一边删除

```java
 for (Student student : list) {     // 实际先调用itertor的hasNext方法，判断当前索引是否等于集合长											度，然后再次调用next方法
     if (student.getName().equalsIgnoreCase("lucy")) {
         list.remove(student);
     }
 }
```

会出现并发修改异常`ConcurrentModificationException`

![1591801681725](E:\githubResp\SpringBoot-Demo\mybatis\src\main\resources\img\1591801681725.png)



#### 为什么会报错

`ArrayList`有一个`modCount`的成员变量，继承自` AbstractList `

```java
protected transient int modCount = 0;
```

`modCount`记录了集合发生**结构性**修改的次数，每次修改都将其值加一

结构性的改变是一种操作：添加，删除一个或者多个元素，或者明显的重新调整所背后的数组的大小；仅仅修改元素的内容并不叫结构性修改

```java
// 新增
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // 调用此方法，内部进行 modCount+1 操作
    elementData[size++] = e;
    return true;
}

// 指定位置新增
public void add(int index, E element) {
    rangeCheckForAdd(index);

    ensureCapacityInternal(size + 1);  // 调用此方法
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}

private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }

    ensureExplicitCapacity(minCapacity); //调用ensureExplicitCapacity方法 ，内部进行 													modCount+1 操作
}
 private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

     if (minCapacity - elementData.length > 0)
         grow(minCapacity);
 }
 // 、、、、、
```

```java
// 删除指定索引的对象
public E remove(int index) {
    rangeCheck(index);
    modCount++;				//  modCount+1 操作
    E oldValue = elementData(index);

    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work

    return oldValue;
}

// 删除对象
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);  // 调用fastRemove方法，内部进行 modCount+1 操作
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}
private void fastRemove(int index) {
    modCount++;  				//  modCount+1 操作
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; 
}
// 、、、、
```

```java
private class Itr implements Iterator<E> {
    int cursor;       // 返回下一个元素的索引
    int lastRet = -1; // /返回的最后一个元素的索引 
    int expectedModCount = modCount;

    public boolean hasNext() {
        return cursor != size;
    }

    @SuppressWarnings("unchecked")
    public E next() {
        checkForComodification();   // 每次迭代都进行检查
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        cursor = i + 1;
        return (E) elementData[lastRet = i];
    }

    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    final void checkForComodification() {
        if (modCount != expectedModCount) 
            throw new ConcurrentModificationException(); // 抛出并发修改异常
    }
}
```



