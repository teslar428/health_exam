package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Setmeal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//@Repository
public interface SetmealDao {

    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(Map<String, Integer> map);

    Page<CheckItem> selectByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    List<Map<String, Object>> findSetmealCount();

    List<Integer> findCheckgroupIdsBySetmealId(Integer id);

    void deleteAssociation(Integer id);

    void edit(Setmeal setmeal);

    void deleteById(Integer id);

    Setmeal findSetmealAllInfoById(int id);

    Setmeal findSetmealAllInfoById2(int id);
}
