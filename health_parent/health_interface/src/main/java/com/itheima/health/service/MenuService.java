package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {
    void add(Menu menu, Integer[] permissionIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    Menu findById(Integer id);

    List<Integer> findPermissionIdsByMenuId(Integer id);

    void edit(Menu menu, Integer[] permissionIds);

    void delete(Integer id);

    List<Menu> findAll();

}
