package com.itheima.health.pojo;

import java.io.Serializable;

/**
 * @ClassName Questionnaire
 * @Description TODO
 * @Author
 * @Date 2019/7/17 17:23
 * @Version 1.0
 **/
public class Questionnaire implements Serializable {

    private int id;
    private String name;
    private String classification;
    private String enable;
    private String gender;

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

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
