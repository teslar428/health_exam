package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    //新增套餐
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            //绑定套餐和检查组的多对多关系
            setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        }
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());

        jedisPool.getResource().del(RedisConstant.SETMEAL_LIST);
    }

    //绑定套餐和检查组的多对多关系
    @Transactional
    public void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("setmeal_id", id);
            map.put("checkgroup_id", checkgroupId);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }

    //将图片名称保存到Redis
    public void savePic2Redis(String pic) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic);
    }

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        if (queryString != null && queryString != "") {
            queryString = "%" + queryString + "%";
        }
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = setmealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public List<Setmeal> findAll() {
        List<Setmeal> setmealList = setmealDao.findAll();
        return setmealList;
    }

    public Setmeal findById(int id) {
        return setmealDao.findSetmealAllInfoById(id);
    }

    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    public List<Integer> findCheckgroupIdsBySetmealId(Integer id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    @Transactional
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.deleteAssociation(setmeal.getId());
        setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        setmealDao.edit(setmeal);

        jedisPool.getResource().del(RedisConstant.SETMEAL_LIST);
        jedisPool.getResource().del(RedisConstant.SETMEAL_PREFIX + setmeal.getId());
    }

    @Transactional
    public void delete(Integer id) {
        setmealDao.deleteAssociation(id);
        setmealDao.deleteById(id);
        jedisPool.getResource().del(RedisConstant.SETMEAL_LIST);
        jedisPool.getResource().del(RedisConstant.SETMEAL_PREFIX + id);
    }

}
