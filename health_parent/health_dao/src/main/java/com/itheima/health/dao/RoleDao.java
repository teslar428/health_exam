package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

//@Repository
public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);

    void add(Role role);

    void setRoleAndMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    Page<Role> selectByCondition(String queryString);

    Role findById(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    void deleteRoleMenu(Integer id);

    void deleteRolePermission(Integer id);

    void edit(Role role);

    long findCountByRoleId(Integer id);

    void deleteById(Integer id);

    List<Role> findAll();

    void setRoleAndPermission(@Param("roleId") Integer roleId,@Param("permissionId") Integer permissionId);
}
