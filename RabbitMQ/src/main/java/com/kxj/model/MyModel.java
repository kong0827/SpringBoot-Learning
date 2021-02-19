package com.kxj.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author xiangjin.kong
 * @date 2021/2/19 13:47
 */
@Data
public class MyModel implements Serializable {

    private static final long serialVersionUID = 2575901427859085241L;
    private UUID id;
    private String info;
}
