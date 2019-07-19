package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Transactional
    public void add(Menu menu, Integer[] permissionIds) {
        menuDao.add(menu);
        setMenuAndPermission(menu.getId(), permissionIds);
    }

    @Transactional
    public void setMenuAndPermission(Integer menuId, Integer[] permissionIds) {
        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                menuDao.setMenuAndPermission(menuId, permissionId);
            }

        }
    }

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Menu> page = menuDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    public List<Integer> findPermissionIdsByMenuId(Integer id) {
        return menuDao.findPermissionIdsByMenuId(id);
    }

    @Transactional
    public void edit(Menu menu, Integer[] permissionIds) {
        //根据Id删除中间表数据
        menuDao.deleteAssociation(menu.getId());
        //向中间表插入数据
        setMenuAndPermission(menu.getId(), permissionIds);
        menuDao.edit(menu);
    }

    @Transactional
    public void delete(Integer id) {
        //查询菜单是否与角色表相关联
        long count = menuDao.findCountByMenuId(id);
        if (count > 0) {
            throw new RuntimeException("当前菜单被引用,不能删除");
        }
        menuDao.deleteAssociation(id);

        menuDao.deleteById(id);
    }

    public List<Menu> findAll() {
        return menuDao.findAll();
    }
}
