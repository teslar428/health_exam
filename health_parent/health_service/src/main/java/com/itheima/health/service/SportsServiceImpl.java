package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SportsDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Sports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @ClassName SportsServiceImpl
 * @Description TODO
 * @Author
 * @Date 2019/7/18 10:00
 * @Version 1.0
 **/
@Service(interfaceClass = SportsService.class)
public class SportsServiceImpl implements SportsService {
    @Autowired
    private SportsDao sportsDao;
    @Override
    public void add(Sports sports) {
        sportsDao.add(sports);
    }

    @Override
    public PageResult<Sports> findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        //要实现模糊查询，就得拼接%
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 有查询条件时，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        // 使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 紧接着的查询语句会被拦截进行分页，要查询所有
        Page<Sports> page = sportsDao.findByCondition(queryPageBean.getQueryString());
        // PageResult 用PageResult包装
        PageResult<Sports> result = new PageResult<Sports>(page.getTotal(),page.getResult());
        return result;
    }

    @Override
    public Sports findById(int id) {
        return sportsDao.findById(id);
    }

    @Override
    public void update(Sports sports) {
        sportsDao.update(sports);
    }

    @Override
    public void delete(int id) {
        sportsDao.delete(id);
    }
}
