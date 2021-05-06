

## StatementHandler

### 参数处理

- 单个参数

  默认不做任何处理。除非设置了@param

- 多个参数

  转换成Param1，param2....

  基于@param中的name转换

  基于反射转换成变量名，如果不支持转换成arg0, arg1



**源码解析**

参数处理主要在ParamNameResolver中

![image-20210506223325732](https://gitee.com/kongxiangjin/images/raw/master/img/20210506223328.png)

`MapperInvoke`

mapper代理类

```java
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else if (isDefaultMethod(method)) {
            return invokeDefaultMethod(proxy, method, args);
        }
    } catch (Throwable t) {
        throw ExceptionUtil.unwrapThrowable(t);
    }
    final MapperMethod mapperMethod = cachedMapperMethod(method);
    // args 参数值
    return mapperMethod.execute(sqlSession, args);
}
```



`MapperMethod`

```java
public MethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
    Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
    if (resolvedReturnType instanceof Class<?>) {
        this.returnType = (Class<?>) resolvedReturnType;
    } else if (resolvedReturnType instanceof ParameterizedType) {
        this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
    } else {
        this.returnType = method.getReturnType();
    }
    this.returnsVoid = void.class.equals(this.returnType);
    this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
    this.returnsCursor = Cursor.class.equals(this.returnType);
    this.returnsOptional = Optional.class.equals(this.returnType);
    this.mapKey = getMapKey(method);
    this.returnsMap = this.mapKey != null;
    this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
    this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
    
    // 进行参数解析
    this.paramNameResolver = new ParamNameResolver(configuration, method);
}
```



`ParamNameResolver`

获取参数名称，并封装成map

```java
public ParamNameResolver(Configuration config, Method method) {
    // 获取参数类型
    final Class<?>[] paramTypes = method.getParameterTypes();
    // 获取有@Param修饰的参数
    final Annotation[][] paramAnnotations = method.getParameterAnnotations();
    final SortedMap<Integer, String> map = new TreeMap<>();
    int paramCount = paramAnnotations.length;
    // get names from @Param annotations
    for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
        if (isSpecialParameter(paramTypes[paramIndex])) {
            // skip special parameters
            continue;
        }
        String name = null;
        // 对@Param修饰的参数进行遍历
        for (Annotation annotation : paramAnnotations[paramIndex]) {
            if (annotation instanceof Param) {
                hasParamAnnotation = true;
                name = ((Param) annotation).value();
                break;
            }
        }
        if (name == null) {
            // @Param 未指定
            if (config.isUseActualParamName()) {
                name = getActualParamName(method, paramIndex);
            }
            if (name == null) {
                // use the parameter index as the name ("0", "1", ...)
                // gcode issue #71
                name = String.valueOf(map.size());
            }
        }
        map.put(paramIndex, name);
    }
    names = Collections.unmodifiableSortedMap(map);
}
```

`MapperMethod`

```java
public Object execute(SqlSession sqlSession, Object[] args) {
    Object result;
    // 判断SQL执行的类型
    switch (command.getType()) {
      case INSERT: {
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.insert(command.getName(), param));
        break;
      }
      case UPDATE: {
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.update(command.getName(), param));
        break;
      }
      case DELETE: {
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.delete(command.getName(), param));
        break;
      }
      case SELECT:
        if (method.returnsVoid() && method.hasResultHandler()) {
          executeWithResultHandler(sqlSession, args);
          result = null;
        } else if (method.returnsMany()) {
          result = executeForMany(sqlSession, args);
        } else if (method.returnsMap()) {
          result = executeForMap(sqlSession, args);
        } else if (method.returnsCursor()) {
          result = executeForCursor(sqlSession, args);
        } else {
           // 参数转化
          Object param = method.convertArgsToSqlCommandParam(args);
           // param是组装好的参数和值的对应的map，执行查询
          result = sqlSession.selectOne(command.getName(), param);
          if (method.returnsOptional()
              && (result == null || !method.getReturnType().equals(result.getClass()))) {
            result = Optional.ofNullable(result);
          }
        }
        break;
      case FLUSH:
        result = sqlSession.flushStatements();
        break;
      default:
        throw new BindingException("Unknown execution method for: " + command.getName());
    }
    if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
      throw new BindingException("Mapper method '" + command.getName()
          + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
    }
    return result;
  }
```



`ParamNameResolver`

```java
public Object getNamedParams(Object[] args) {
    final int paramCount = names.size();
    if (args == null || paramCount == 0) {
        return null;
    } else if (!hasParamAnnotation && paramCount == 1) {
        // 如果没有@param修饰的参数，并且为一个参数的情况下，直接获取第0个参数并返回，参数名可以随便写
        return args[names.firstKey()];
    } else {
        final Map<String, Object> param = new ParamMap<>();
        int i = 0;
        for (Map.Entry<Integer, String> entry : names.entrySet()) {
            param.put(entry.getValue(), args[entry.getKey()]);
            // 添加@Param的名称，@Param1,@Param2... 将arg0 -> param1
            final String genericParamName = GENERIC_NAME_PREFIX + String.valueOf(i + 1);
            // ensure not to overwrite parameter named with @Param
            if (!names.containsValue(genericParamName)) {
                param.put(genericParamName, args[entry.getKey()]);
            }
            i++;
        }
        // 组装成了Map，参数和值的对应
        return param;
    }
}
```



### 参数设置

**单个原始类型**
直接映射，忽略SQL中的引用名称

**Map类型**
基于Map key映射

**Object**
基于属性名称映射，支持嵌套对象属性访问





**源码解析**

**SimpleExecutor**

```java
private Statement prepareStatement(StatementHandler handler, Log statementLog) throws SQLException {
  Statement stmt;
  Connection connection = getConnection(statementLog);
  stmt = handler.prepare(connection, transaction.getTimeout());
  handler.parameterize(stmt);
  return stmt;
}
```

**PreparedStatementHandler**

```java
@Override
public void parameterize(Statement statement) throws SQLException {
  parameterHandler.setParameters((PreparedStatement) statement);
}
```

**DefaultParameterHandler**

```java
@Override
public void setParameters(PreparedStatement ps) {
  ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
  List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
  if (parameterMappings != null) {
    for (int i = 0; i < parameterMappings.size(); i++) {
      ParameterMapping parameterMapping = parameterMappings.get(i);
      if (parameterMapping.getMode() != ParameterMode.OUT) {
        Object value;
        String propertyName = parameterMapping.getProperty();
        if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
          value = boundSql.getAdditionalParameter(propertyName);
        } else if (parameterObject == null) {
          value = null;
        } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
           // 单参
          value = parameterObject;
        } else {
          MetaObject metaObject = configuration.newMetaObject(parameterObject);
          value = metaObject.getValue(propertyName);
        }
        TypeHandler typeHandler = parameterMapping.getTypeHandler();
        JdbcType jdbcType = parameterMapping.getJdbcType();
        if (value == null && jdbcType == null) {
          jdbcType = configuration.getJdbcTypeForNull();
        }
        try {
          typeHandler.setParameter(ps, i + 1, value, jdbcType);
        } catch (TypeException | SQLException e) {
          throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
        }
      }
    }
  }
}
```



### 结果集处理 



![image-20210428003256749](C:/Users/%E5%B0%8FK/Pictures/image-20210428003256749.png)

![image-20210428004016005](C:/Users/%E5%B0%8FK/Pictures/image-20210428004016005.png)

![image-20210428004610692](C:/Users/%E5%B0%8FK/Pictures/image-20210428004610692.png)





