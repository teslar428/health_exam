package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.pojo.Diary;

public interface DiaryDao {

    void add(Diary diary);

    Page<Diary> findByCondition(String queryString);

    Diary findById(int id);

    void update(Diary diary);

    void delete(int id);

}
