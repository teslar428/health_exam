package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.pojo.Question;

public interface QuestionDao {
    void add(Question question);

    Page<Question> findByCondition(String queryString);

    Question findById(int id);

    void update(Question question);

    void delete(int id);
}
