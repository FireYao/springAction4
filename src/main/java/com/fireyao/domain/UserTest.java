package com.fireyao.domain;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserTest implements Serializable {

    private Long id;
    private String name;
    private Integer age;

}
