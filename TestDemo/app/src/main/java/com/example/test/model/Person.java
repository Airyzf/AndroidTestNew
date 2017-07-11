package com.example.test.model;

import java.io.Serializable;

/**
 * Created by yuanzhaofeng
 * on 2017/6/29 9:26.
 * desc:
 * version:
 */
public class Person implements Serializable {
    private String name;
    private String num;

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
