package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Questionnaire;

public interface QuestionnaireService {
    void add(Questionnaire questionnaire);

    PageResult<Questionnaire> findPage(QueryPageBean queryPageBean);


    void update(Questionnaire questionnaire);

    void delete(int id);

    Questionnaire findById(int id);
}
