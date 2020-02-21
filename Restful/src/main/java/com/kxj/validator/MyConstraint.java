package com.kxj.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kxj
 * @date 2020/2/20 21:18
 * @Desc 自定义注解，参数校验
 */

/**
 * @Target 作用在方法和属性上
 */
@Target({ElementType.FIELD, ElementType.METHOD})
/**
 * 运行时生效
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * @Constraint 代表参数校验
 * validatedBy 具体哪个类实现参数校验
 */
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {
    /**
     * 参数校验必须添加以下三个
     * @return
     */
    String message() default "自定义参数校验";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
