package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RolerService.class)
public class RoleServiceImpl implements RolerService {
    @Autowired
    private RoleDao roleDao;

    @Transactional
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.add(role);
        setRoleAndPermissionAndMenu(role.getId(), permissionIds, menuIds);
    }

    public void setRoleAndPermissionAndMenu(Integer roleId, Integer[] permissionIds, Integer[] menuIds) {
        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                roleDao.setRoleAndPermission(roleId, permissionId);
            }
        }

        if (menuIds != null && menuIds.length > 0) {
            for (Integer menuId : menuIds) {
                roleDao.setRoleAndMenu(roleId, menuId);
            }
        }
    }

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> page = roleDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Transactional
    public void edit(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.deleteRoleMenu(role.getId());
        roleDao.deleteRolePermission(role.getId());
        setRoleAndPermissionAndMenu(role.getId(), permissionIds, menuIds);
        roleDao.edit(role);
    }

    @Transactional
    public void delete(Integer id) {
        long count = roleDao.findCountByRoleId(id);
        if (count > 0) {
            throw new RuntimeException("当前角色被引用,不能删除");
        }
        roleDao.deleteRolePermission(id);
        roleDao.deleteRoleMenu(id);
        roleDao.deleteById(id);
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public Map<String, Object> findIdsByRoleId(Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> menuIds = roleDao.findMenuIdsByRoleId(id);
        List<Integer> permissionIds = roleDao.findPermissionIdsByRoleId(id);
        map.put("menuIds", menuIds);
        map.put("permissionIds", permissionIds);
        return map;
    }
}
