package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Questionnaire;
import com.itheima.health.service.QuestionnaireService;
import org.springframework.web.bind.annotation.*;
/**
 * @ClassName QuestionnaireController
 * @Description TODO
 * @Author
 * @Date 2019/7/17 17:21
 * @Version 1.0
 **/
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Reference
    private QuestionnaireService questionnaireService;

    @PostMapping("/add")
    public Result add(@RequestBody Questionnaire questionnaire){
       questionnaireService.add(questionnaire);
       return new Result(true, MessageConstant.ADD_QUESTIONNAIRE_SUCCESS);
    }
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Questionnaire> pageResult = questionnaireService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_QUESTIONNAIRE_SUCCESS,pageResult);
    }
    @PostMapping("/findById")
    public Result findById(@RequestParam int id){
        Questionnaire questionnaire = questionnaireService.findById(id);
        return new Result(true,MessageConstant.UPDATE_QUESTIONNAIRE_SUCCESS,questionnaire);
    }
    @PostMapping("/update")
    public Result update(@RequestBody Questionnaire questionnaire){
        questionnaireService.update(questionnaire);
        return new Result(true,MessageConstant.UPDATE_QUESTIONNAIRE_SUCCESS);
    }
    @PostMapping("/delete")
    public Result delete(int id){
        questionnaireService.delete(id);
        return new Result(true,MessageConstant.DELETE_QUESTIONNAIRE_SUCCESS);
    }
}
