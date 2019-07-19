package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Question;
import com.itheima.health.service.QuestionService;
import org.springframework.web.bind.annotation.*;



/**
 * @ClassName QuestionController
 * @Description TODO
 * @Author
 * @Date 2019/7/17 19:38
 * @Version 1.0
 **/
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Reference
    private QuestionService questionService;
    @PostMapping("/add")
    public Result add(@RequestBody Question question){
        questionService.add(question);
        return  new Result(true, MessageConstant.ADD_QUESTION_SUCCESS);
    }
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Question> pageResult = questionService.findPage(queryPageBean);
         return new Result(true,MessageConstant.QUERY_QUESTION_SUCCESS,pageResult);
    }
    @PostMapping("/findById")
    public Result findById(@RequestParam int id ){
         Question question = questionService.findById(id);
         return new Result(true,MessageConstant.UPDATE_QUESTION_SUCCESS,question);
    }
    @PostMapping("/update")
    public Result update(@RequestBody Question question){
        questionService.update(question);
        return new Result(true,MessageConstant.UPDATE_QUESTION_SUCCESS);
    }
    @PostMapping("/delete")
    public Result delete(int id){
        questionService.delete(id);
        return new Result(true,MessageConstant.DELETE_QUESTION_SUCCESS);
    }
}
