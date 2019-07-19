package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Sports;
import com.itheima.health.service.SportsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sports")
public class SportsController {
    @Reference
    private SportsService sportsService;
    //添加
    @PostMapping("/add")
    public Result add(@RequestBody Sports sports){
        sportsService.add(sports);
        return new Result(true, MessageConstant.ADD_SPORTS_SUCCESS);
    }

    //查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult<Sports> pageResult = sportsService.findPage(queryPageBean);
       return new Result(true,MessageConstant.QUERY_SPORTS_SUCCESS,pageResult);
    }

    //编辑
    @PostMapping("/findById")
    public Result findById(@RequestParam int id){
      Sports sports = sportsService.findById(id);
      return new Result(true,MessageConstant.UPDATE_SPOTRS_SUCCESS,sports);
    }
    @PostMapping("/update")
    public Result update(@RequestBody Sports sports){
        sportsService.update(sports);
        return new Result(true,MessageConstant.UPDATE_SPOTRS_SUCCESS);
    }
    @PostMapping("/delete")
    public Result delete(int id){
        sportsService.delete(id);
        return new Result(true,MessageConstant.DELETE_SPORS_SUCCESS);
    }
}
