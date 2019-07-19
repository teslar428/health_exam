package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Transactional
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Permission> page = permissionDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional
    public void delete(Integer id) {
        long count = permissionDao.findCountByPermissionId(id);
        if (count > 0) {
            throw new RuntimeException("当前权限被引用,不能删除");
        }
        permissionDao.deleteById();
    }

    public Permission findById(Integer id) {
        Permission permission = permissionDao.findPermissionsById(id);
        return permission;
    }

    @Transactional
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
