package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.pojo.Sports;

public interface SportsDao {
    void add(Sports sports);

    Page<Sports> findByCondition(String queryString);

    Sports findById(int id);

    void update(Sports sports);

    void delete(int id);
}
