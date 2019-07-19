package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RolerService {
    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    Role findById(Integer id);

    void edit(Role role, Integer[] permissionIds, Integer[] menuIds);

    void delete(Integer id);

    List<Role> findAll();

    Map<String, Object> findIdsByRoleId(Integer id);
}