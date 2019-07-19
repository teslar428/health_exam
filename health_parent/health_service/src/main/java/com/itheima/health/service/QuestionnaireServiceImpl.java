package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.QuestionnaireDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @ClassName QuestionnaireServiceImpl
 * @Description TODO
 * @Author
 * @Date 2019/7/17 17:27
 * @Version 1.0
 **/
@Service(interfaceClass = QuestionnaireService.class)
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    private QuestionnaireDao questionnaireDao;

    @Override
    public void add(Questionnaire questionnaire) {
        questionnaireDao.add(questionnaire);
    }

    @Override
    public PageResult<Questionnaire> findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        //要实现模糊查询，就得拼接%
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 有查询条件时，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        // 使用分页插件
        PageHelper.startPage(currentPage-1, queryPageBean.getPageSize());
        // 紧接着的查询语句会被拦截进行分页，要查询所有
        Page<Questionnaire> page = questionnaireDao.findByCondition(queryPageBean.getQueryString());
        // PageResult 用PageResult包装
        PageResult<Questionnaire> result = new PageResult<Questionnaire>(page.getTotal(),page.getResult());
        return result;
    }

    @Override
    public Questionnaire findById(int id) {
        return questionnaireDao.findById(id);
    }

    @Override
    public void update(Questionnaire questionnaire) {
        questionnaireDao.update(questionnaire);
    }

    @Override
    public void delete(int id) {
        questionnaireDao.delete(id);
    }
}
