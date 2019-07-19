package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;


@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        return user;
    }

    public List<Menu> findUserMenuByUsername(String username) {
        List<Menu> AllMenus = userDao.findUserFatherMenuByUsername(username);
        List<Menu> SomeMenus = userDao.findUserChildrenMenuByUsername(username);
        for (Menu allMenu : AllMenus) {
            List<Menu> children = new LinkedList<>();
            for (Menu someMenu : SomeMenus) {
                if (allMenu.getId() == someMenu.getParentMenuId()) {
                    children.add(someMenu);
                }
            }
            allMenu.setChildren(children);
        }
        return AllMenus;
    }

    @Transactional
    public void add(User user, Integer[] roleIds) {
        userDao.add(user);
        setUserAndRole(user.getId(), roleIds);
    }

    @Transactional
    public void setUserAndRole(Integer userId, Integer[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                userDao.setUserAndRole(userId, roleId);
            }
        }
    }

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<User> page = userDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public User findById(Integer id) {
        return userDao.findById(id);
    }

    public List<Integer> findRoleIdsByUserId(Integer id) {
        return userDao.findRoleIdsByUserId(id);
    }

    @Transactional
    public void edit(User user, Integer[] roleIds) {
        userDao.deleteAssociation(user.getId());
        setUserAndRole(user.getId(),roleIds );
        userDao.edit(user);
    }

    @Transactional
    public void delete(Integer id) {
        userDao.deleteAssociation(id);
        userDao.deleteById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void changePassword(String password) {
        userDao.changePassword(password);
    }

}