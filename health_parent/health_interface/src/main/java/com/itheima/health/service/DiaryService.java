package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Diary;

public interface DiaryService {
    void add(Diary diary);

    PageResult<Diary> findPage(QueryPageBean queryPageBean);

    Diary findById(int id);

    void update(Diary diary);

    void delete(int id);
}
