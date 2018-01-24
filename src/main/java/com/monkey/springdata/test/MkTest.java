package com.monkey.springdata.test;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @program:
 * @description: TODO
 * @author: MK monkey
 * @create: 2018-01-06 21:25
 **/
@Entity
public class MkTest {

    @Id
    private Integer id;
    private String name;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}

