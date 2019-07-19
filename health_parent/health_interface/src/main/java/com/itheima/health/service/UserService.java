package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    List<Menu> findUserMenuByUsername(String username);

    void add(User user, Integer[] roleIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(User user, Integer[] roleIds);

    void delete(Integer id);

    List<User> findAll();

    void changePassword(String password);
}
