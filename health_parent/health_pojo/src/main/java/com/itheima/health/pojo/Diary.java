package com.itheima.health.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Diary
 * @Description TODO
 * @Author
 * @Date 2019/7/17 15:04
 * @Version 1.0
 **/
public class Diary implements Serializable {
    private int id;
    private Date date;
    private String name;
    private String review;
    private String way;
    private String content;
    private String feedback;
    private String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
