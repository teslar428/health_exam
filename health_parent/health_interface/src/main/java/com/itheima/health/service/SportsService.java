package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Sports;

public interface SportsService {
    void add(Sports sports);

    PageResult<Sports> findPage(QueryPageBean queryPageBean);

    Sports findById(int id);

    void update(Sports sports);

    void delete(int id);
}
