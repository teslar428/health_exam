package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(@Param(value = "checkGroupId") Integer checkGroupId, @Param(value = "checkitemId") Integer checkitemId);

    Page<CheckGroup> selectByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void deleteAssociation(Integer id);

    void edit(CheckGroup checkGroup);

    long findCountByCheckItemGrouId(Integer id);

    void deleteById(Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupListById(Integer id);

    List<Integer> findSetmealId(int id);
}
