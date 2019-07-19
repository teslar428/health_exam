package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

//检查组服务
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Autowired
    private JedisPool jedisPool;

    //添加检查组合，同时需要设置检查组合和检查项的关联关系
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        //获取自增后的ID,添加到关联表中
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

    //设置检查组合和检查项的关联关系
    @Transactional
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.setCheckGroupAndCheckItem(checkGroupId, checkitemId);
            }
        }
    }

    //分页查询
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    //编辑检查组，同时需要更新和检查项的关联关系
    @Transactional
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
        //更新检查组基本信息
        checkGroupDao.edit(checkGroup);

        //当检查组有变动时,找出引用这个检查组的套餐,从redis中删除
        findSetmealId(checkGroup.getId());

    }

    @Transactional
    public void delete(Integer id) {
        //查询当前检查组是否和检查套餐关联
        long count = checkGroupDao.findCountByCheckItemGrouId(id);
        if (count > 0) {
            throw new RuntimeException("当前检查组被引用，不能删除");
        }
        checkGroupDao.deleteAssociation(id);
        checkGroupDao.deleteById(id);
    }

    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    public void findSetmealId(int id) {
        Jedis jedis = jedisPool.getResource();
        List<Integer> list = checkGroupDao.findSetmealId(id);
        if (list != null) {
            for (Integer setmealId : list) {
                jedis.del(RedisConstant.SETMEAL_PREFIX + setmealId);
            }
        }
    }

}
