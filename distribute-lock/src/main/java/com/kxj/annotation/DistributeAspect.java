package com.kxj.annotation;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author xiangjin.kong
 * @date 2022/6/18 14:55
 * @desc 分布式锁AOP
 */
@Aspect
@Component
public class DistributeAspect {

    @Pointcut(value = "@annotation(com.kxj.annotation.DistributeAnnotation)")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // 目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();

        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();

        // 方法中参数的类型
        Class<?>[] parameterTypes = method.getParameterTypes();


        // 请求参数
        Object[] args = joinPoint.getArgs();


        getLockName(method, args);

        // The class of the pointcut
//        Class targetClass = joinPoint.getTarget().getClass();
//        //Using annotated method
//        String methodName = joinPoint.getSignature().getName();
//
//        Class[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
//
//        Method method = targetClass.getMethod(methodName, parameterTypes);
//
//        Object[] arguments = joinPoint.getArgs();
//
//        final String lockName = getLockName(method, arguments);

        return joinPoint.proceed();
    }

    @AfterThrowing(value = "pointcut()", throwing="ex")
    public void afterThrowing(Throwable ex) {
        throw new RuntimeException(ex);
    }

    private String getLockName(Method method, Object[] args) {
        Objects.requireNonNull(method);
        DistributeAnnotation annotation = method.getAnnotation(DistributeAnnotation.class);
        // todo 获取一系列注解上的参数
        String lockName = annotation.lockName();
        // 需要锁的参数
        String param = annotation.param();

        if (StringUtils.isEmpty(lockName)) {
            if (args.length> 0) {
                if (!StringUtils.isEmpty(param)) {
                    // 请求参数不为空
                    Object arg;
                    if (annotation.argNum()> 0) {
                        arg = args[annotation.argNum()-1];
                    } else {
                        arg = args[0];
                    }
                    lockName = String.valueOf(getParam(arg, param));

                } else if (annotation.argNum()> 0) {
                    // 对于String指定位置获取
                    lockName = args[annotation.argNum()-1].toString();
                }
            }
        }
        if (!StringUtils.isEmpty(lockName)) {
//            String preLockName = annotation.lockNamePre(),
//                    postLockName = annotation.lockNamePost(),
//                    separator = annotation.separator();
            String preLockName = "",  separator = ".", postLockName="";
            StringBuilder lName = new StringBuilder();
            if (!StringUtils.isEmpty(preLockName)) {
                lName.append(preLockName).append(separator);
            }
            lName.append(lockName);
            if (!StringUtils.isEmpty(postLockName)) {
                lName.append(separator).append(postLockName);
            }

            lockName = lName.toString();

            return lockName;
        }
        throw new IllegalArgumentException("Can't get or generate lockName accurately!");
    }

    /**
     * 从方法参数中获取数据
     * @param arg
     * @param param
     * @return
     */
    private Object getParam(Object arg, String param) {
        if (!StringUtils.isEmpty(param) && arg != null) {
            try {
                return PropertyUtils.getProperty(arg, param);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(arg + "no attributes" + param + "or get method not implemented.", e);
            } catch (Exception e) {
                throw new RuntimeException("", e);
            }
        }
        return null;
    }
}
