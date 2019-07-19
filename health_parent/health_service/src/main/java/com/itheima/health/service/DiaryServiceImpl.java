package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.DiaryDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @ClassName DiaryServiceImpl
 * @Description TODO
 * @Author
 * @Date 2019/7/17 15:08
 * @Version 1.0
 **/
@Service(interfaceClass = DiaryService.class)
@Transactional
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private DiaryDao diaryDao;
    @Override
    public void add(Diary diary) {
        diaryDao.add(diary);
    }
    @Override
    public PageResult<Diary> findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        //要实现模糊查询，就得拼接%
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 有查询条件时，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        // 使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 紧接着的查询语句会被拦截进行分页，要查询所有
        Page<Diary> page = diaryDao.findByCondition(queryPageBean.getQueryString());
        // PageResult 用PageResult包装
        PageResult<Diary> result = new PageResult(page.getTotal(),page.getResult());
        return result;
    }

    @Override
    public Diary findById(int id) {
        return  diaryDao.findById(id);
    }

    @Override
    public void update(Diary diary) {
        diaryDao.update(diary);
    }

    @Override
    public void delete(int id) {
        diaryDao.delete(id);
    }

}
