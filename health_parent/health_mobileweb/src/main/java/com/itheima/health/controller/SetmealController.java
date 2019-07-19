package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //获取所有套餐信息
    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {
        try {
            String getSetmealList = jedisPool.getResource().get(RedisConstant.SETMEAL_LIST);
            if (getSetmealList != null) {
                Object parseSetmealList = JSON.parse(getSetmealList);
                System.out.println("从redis从取出所有套餐信息");
                return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, parseSetmealList);
            }
            List<Setmeal> setmealList = setmealService.findAll();

            String setmealListJSON = JSON.toJSONString(setmealList);
            jedisPool.getResource().set(RedisConstant.SETMEAL_LIST, setmealListJSON);
            System.out.println("往redis中存入所有套餐信息");

            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    //根据id查询套餐信息
    @RequestMapping("/findById")
    public Result findById(int id) {
        try {
            String getSetmeal = jedisPool.getResource().get(RedisConstant.SETMEAL_PREFIX + id);
            if (getSetmeal != null) {
                Object redisSetmeal = JSONObject.parse(getSetmeal);
                System.out.println("从redis中取出套餐信息");
                return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, redisSetmeal);
            }
            Setmeal setmeal = setmealService.findById(id);

            String setmealJSON = JSONObject.toJSONString(setmeal);
            jedisPool.getResource().set(RedisConstant.SETMEAL_PREFIX + id, setmealJSON);
            System.out.println("往redis中存入套餐" + id + "信息");

            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
