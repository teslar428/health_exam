package com.itheima.health.pojo;

import java.io.Serializable;

/**
 * @ClassName Sports
 * @Description TODO
 * @Author
 * @Date 2019/7/18 9:29
 * @Version 1.0
 **/
public class Sports implements Serializable {
    private int id;
    private String coding;
    private String name;
    private String intensity;
    private String step;
    private String time;
    private String age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
