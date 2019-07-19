package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Permission;

import java.util.List;

public interface PermissionService {

    void add(Permission permission);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    Permission findById(Integer id);

    void edit(Permission permission);

    List<Permission> findAll();

}
