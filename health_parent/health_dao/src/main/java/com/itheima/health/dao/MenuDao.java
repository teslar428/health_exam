package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface MenuDao {
    List<Menu> findAll();

    void add(Menu menu);

    void setMenuAndPermission(@Param("menuId") Integer menuId,@Param("permissionId") Integer permissionId);

    Page<Menu> selectByCondition(String queryString);

    Menu findById(Integer id);

    List<Integer> findPermissionIdsByMenuId(Integer id);

    void deleteAssociation(Integer id);

    void edit(Menu menu);

    long findCountByMenuId(Integer id);

    void deleteById(Integer id);
}
