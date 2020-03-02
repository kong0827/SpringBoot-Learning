package kxj.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kxj
 * @date 2019/12/27 0:08
 * @desc
 */
@Data
public class User implements Serializable {


    @NotEmpty
    private String id;
    private String username;
    private int age;
}
