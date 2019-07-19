package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Question;

public interface QuestionService {
    void add(Question question);


    PageResult<Question> findPage(QueryPageBean queryPageBean);

    Question findById(int id);

    void update(Question question);

    void delete(int id);
}
