package com.kxj.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author kxj
 * @date 2020/2/20 21:22
 * @desc 自定义参数校验实现类
 *          必须实现ConstraintValidator接口
 *            ConstraintValidator<MyConstraint, Object>
 *                MyConstraint: 自定义注解
 *                Object: 参数类型
 */
public class MyConstraintValidator implements
        ConstraintValidator<MyConstraint, Object> {

    /**
     * 初始化方法
     * @param constraintAnnotation
     */
    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("自定义参数校验初始化");
    }

    /**
     * 真正做参数校验的方法
     * @param value 参数校验的参数
     * @param context 上下文对象
     * @return
     */
    @Override
    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {
        System.out.println("参数为：" + value);
        return false;
    }
}
