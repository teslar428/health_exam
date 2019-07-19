package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.OrderSettingList;
import com.itheima.health.service.OrderSettingListService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/orderSettingList")
public class OrderSettingListController {
    @Reference
    private OrderSettingListService orderSettingListService;

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult<OrderSettingList> pageResult = orderSettingListService.findPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ORDER_FAIL);
        }

    }

    @PostMapping("/add")
    public Result add(@RequestBody Member member, Integer[] packageIds) throws ParseException {
        try {
            orderSettingListService.add(member, packageIds);
            return new Result(true, MessageConstant.ADD_ORDER_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ORDER_STATUS_FAIL);
        }

    }

    @GetMapping("/editOrderStatus")
    public Result editOrderStatus(int id) {
        try {
            orderSettingListService.editOrderStatus(id);
            return new Result(true, MessageConstant.EDIT_ORDER_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ORDER_STATUS_FAIL);
        }

    }

    @GetMapping("/cancelOrderStatus")
    public Result cancelOrderStatus(int id) {
        try {
            orderSettingListService.cancelOrderStatus(id);
            return new Result(true, MessageConstant.DELETE_ORDER_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ORDER_STATUS_FAIL);
        }
    }
}
