package com.itheima.health.pojo;

import java.io.Serializable;

/**
 * @ClassName Question
 * @Description TODO
 * @Author
 * @Date 2019/7/17 19:29
 * @Version 1.0
 **/
public class Question implements Serializable {
    private int id;
    private String name;
    private String type;
    private String gender;
    private String fill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }
}
