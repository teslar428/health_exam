package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.pojo.Questionnaire;

public interface QuestionnaireDao {
    void add(Questionnaire questionnaire);

    Page<Questionnaire> findByCondition(String queryString);

    Questionnaire findById(int id);

    void update(Questionnaire questionnaire);

    void delete(int id);
}
