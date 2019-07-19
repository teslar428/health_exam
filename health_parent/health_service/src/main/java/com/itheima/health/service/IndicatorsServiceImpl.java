package com.itheima.health.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.IndicatorsDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Indicators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName IndicatorsServiceImpl
 * @Description TODO
 * @Author
 * @Date 2019/7/17 9:40
 * @Version 1.0
 **/
@Service(interfaceClass = IndicatorsService.class)
@Transactional
public class IndicatorsServiceImpl implements IndicatorsService {
    @Autowired
    private IndicatorsDao indicatorsDao;
    @Override
    public void add(Indicators indicators) {
      indicatorsDao.add(indicators);
    }


    @Override
    public PageResult<Indicators> findByPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        //要实现模糊查询，就得拼接%
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 有查询条件时，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        // 使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 紧接着的查询语句会被拦截进行分页，要查询所有
        Page<Indicators> page = indicatorsDao.findByCondition(queryPageBean.getQueryString());
        // PageResult 用PageResult包装
        PageResult<Indicators> result = new PageResult<Indicators>(page.getTotal(),page.getResult());
        return result;
    }

    @Override
    public Indicators findById(int id) {
        return indicatorsDao.findById(id);
    }

    @Override
    public void update(Indicators indicators) {
        indicatorsDao.update(indicators);
    }

    @Override
    public void delete(int id) {
        indicatorsDao.delete(id);
    }
}
