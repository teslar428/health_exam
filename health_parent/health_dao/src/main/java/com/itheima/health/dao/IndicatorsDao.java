package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.pojo.Indicators;

public interface IndicatorsDao {
   Page<Indicators> findByCondition(String queryString);

    void add(Indicators indicators);

    Indicators findById(int id);

    void update(Indicators indicators);

    void delete(int id);
}
