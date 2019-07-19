package com.itheima.health.pojo;

import java.io.Serializable;

/**
 * @ClassName Indicators
 * @Description TODO
 * @Author
 * @Date 2019/7/17 9:33
 * @Version 1.0
 **/
public class Indicators implements Serializable {
    private int id;
    private String name;
    private String reference;
    private String unit;
    private String sex;

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
