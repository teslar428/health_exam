package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
        return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            permissionService.delete(id);
            return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }

    // 跳转到权限编辑页面
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Permission permission  = permissionService.findById(id);
            return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    //编辑保存
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_PERMISSION_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS);
    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll(){
        List<Permission> permissionList = permissionService.findAll();
        if(permissionList != null && permissionList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,permissionList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
    }


}
