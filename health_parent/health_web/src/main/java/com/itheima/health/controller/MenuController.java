package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu, Integer[] permissionIds) {
        try {
            menuService.add(menu, permissionIds);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = menuService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    //根据id查询
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Menu menu = menuService.findById(id);
        if (menu != null) {
            Result result = new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_MENU_FAIL);
    }

    //根据菜单id查询对应的所有权限id
    @RequestMapping("/findPermissionIdsByMenuId")
    public List<Integer> findPermissionIdsByMenuId(Integer id) {
        List<Integer> list = menuService.findPermissionIdsByMenuId(id);
        return list;
    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu, Integer[] permissionIds) {
        try {
            menuService.edit(menu, permissionIds);
            return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            menuService.delete(id);
            return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll() {
        List<Menu> menuList = menuService.findAll();
        if (menuList != null && menuList.size() > 0) {
            Result result = new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuList);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_MENU_FAIL);
    }

}
