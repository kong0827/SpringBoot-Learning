package com.kxj.entity;

import com.kxj.group.First;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author kxj
 * @date 2020/2/20 22:42
 * @Desc
 */
@Data
public class Teacher {

    @Digits(message = "id必须为数字", integer = 6, fraction = 2)
    // 在First分组中，判断不能为空
    // 不分配groups，默认每次都要进行验证
    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;
}
