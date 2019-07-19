package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UserDao {

    User findUserByUsername(String username);

    List<Menu> findUserFatherMenuByUsername(String username);

    List<Menu> findUserChildrenMenuByUsername(String username);

    void add(User user);

    void setUserAndRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

    Page<User> selectByCondition(String queryString);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void deleteAssociation(Integer id);

    void edit(User user);

    void deleteById(Integer id);

    List<User> findAll();

    void changePassword(String password);
}
