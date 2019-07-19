package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

//检查项服务
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Autowired
    private JedisPool jedisPool;

    //新增
    @Transactional
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //分页查询
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //为什么有的类在service拼接查询条件,有的类在dao拼接?
        //因为我高兴
        if (queryString != null && queryString != "") {
            queryString = "%" + queryString + "%";
        }
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());

    }

    //删除
    @Transactional
    public void delete(Integer id) {
        //查询当前检查项是否和检查组关联
        long count = checkItemDao.findCountByCheckItemId(id);
        if (count > 0) {
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    // 主键查询
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }

    // 编辑保存
    @Transactional
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
        //当检查项有变动时,找出引用这个检查组的套餐,从redis中删除
        findSetmealId(checkItem.getId());
    }

    //查询所有
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    public void findSetmealId(int id) {
        Jedis jedis = jedisPool.getResource();
        List<Integer> list = checkItemDao.findSetmealId(id);
        if (list != null) {
            for (Integer setmealId : list) {
                jedis.del(RedisConstant.SETMEAL_PREFIX + setmealId);
            }
        }
    }
}
