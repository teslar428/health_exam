package com.itheima.health.service;


import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Indicators;

public interface IndicatorsService {

    void add(Indicators indicators);


    PageResult<Indicators> findByPage(QueryPageBean queryPageBean);

    Indicators findById(int id);

    void update(Indicators indicators);

    void delete(int id);
}
