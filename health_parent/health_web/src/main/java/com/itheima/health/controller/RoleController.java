package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RolerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RolerService rolerService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds) {
        try {
            rolerService.add(role, permissionIds, menuIds);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = rolerService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    //根据id查询
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Role role = rolerService.findById(id);
        if (role != null) {
            Result result = new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, role);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
    }

    //根据角色id查询对应的所有权限id和菜单id
    @RequestMapping("/findIdsByRoleId")
    public Result findIdsByRoleId(Integer id) {
        try {
            Map<String, Object> map = rolerService.findIdsByRoleId(id);
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }

    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds) {
        try {
            rolerService.edit(role, permissionIds, menuIds);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            rolerService.delete(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll() {
        List<Role> roleList = rolerService.findAll();
        if (roleList != null && roleList.size() > 0) {
            Result result = new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, roleList);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
    }

}
