package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Diary;
import com.itheima.health.service.DiaryService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/diary")
public class DiaryController {
    @Reference
    private DiaryService diaryService;
    @PostMapping("/add")
    public Result add(@RequestBody Diary diary){
     diaryService.add(diary);
     return new Result(true, MessageConstant.ADD_DIARY_SUCCESS);
    }
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
          PageResult<Diary> pageResult = diaryService.findPage(queryPageBean);
          return new Result(true,MessageConstant.QUERY_DIARY_SUCCESS,pageResult);
    }
    @PostMapping("/findById")
    public Result findById(@RequestParam int id){
         Diary diary = diaryService.findById(id);
         return new Result(true,MessageConstant.UPDATE_DIARY_SUCCESS,diary);
    }
    @PostMapping("/update")
    public Result update(@RequestBody Diary diary){
        diaryService.update(diary);
        return new Result(true,MessageConstant.UPDATE_DIARY_SUCCESS);
    }
    @PostMapping("/delete")
    public Result delete(int id){
        diaryService.delete(id);
        return new Result(true,MessageConstant.DELETE_DIARY_SUCCESS);
    }
}
