package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.QuestionDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @ClassName QuestionServiceImpl
 * @Description TODO
 * @Author
 * @Date 2019/7/17 19:42
 * @Version 1.0
 **/
@Service(interfaceClass = QuestionService.class)
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;
    @Override
    public void add(Question question) {
        questionDao.add(question);
    }

    @Override
    public PageResult<Question> findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        //要实现模糊查询，就得拼接%
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 有查询条件时，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        // 使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 紧接着的查询语句会被拦截进行分页，要查询所有
        Page<Question> page = questionDao.findByCondition(queryPageBean.getQueryString());
        // PageResult 用PageResult包装
        PageResult<Question> result = new PageResult<Question>(page.getTotal(),page.getResult());
        return result;
    }

    @Override
    public Question findById(int id) {
        return questionDao.findById(id);
    }

    @Override
    public void update(Question question) {
        questionDao.update(question);
    }

    @Override
    public void delete(int id) {
        questionDao.delete(id);
    }
}
