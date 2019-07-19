package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

//@Repository
public interface PermissionDao {

    Set<Permission> findPermissionsByRoleId(Integer roleId);

    void add(Permission permission);

    Page<Permission> selectByCondition(String queryString);

    long findCountByPermissionId(Integer id);

    void deleteById();

    Permission findPermissionsById(Integer id);

    void edit(Permission permission);

    List<Permission> findAll();
}
