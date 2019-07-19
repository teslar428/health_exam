package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Indicators;
import com.itheima.health.service.IndicatorsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indicators")
public class IndicatorsController {
    @Reference
    private IndicatorsService indicatorsService;

    @PostMapping("/add")
    public Result add(@RequestBody Indicators indicators) {
        try {
            indicatorsService.add(indicators);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_INDICATORS_FAIL);
        }
        return new Result(true, MessageConstant.ADD_INDICATORS_SUCCESS);
    }

    //分页查询
    @PostMapping("/findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Indicators> pagedResult = indicatorsService.findByPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_INDICATORS_SUCCESS, pagedResult);
    }

    //修改
    @PostMapping("/findById")
    public Result findById(@RequestParam int id) {
        Indicators indicators = indicatorsService.findById(id);
        if (indicators != null) {
            return new Result(true, MessageConstant.FINDBYID_INDICATORS_SUCCESS, indicators);
        }
        return new Result(false, MessageConstant.FINDBYID_INDICATORS_FAIL);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Indicators indicators) {
        indicatorsService.update(indicators);
        return new Result(true, MessageConstant.UPDATE_INDICATORS_SUCCESS);
    }

    @PostMapping("/delete")
    public Result delete(int id) {
        indicatorsService.delete(id);
        return new Result(true, MessageConstant.DELETE_INDICATORS_SUCCESS);
    }
}
