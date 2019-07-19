package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @Autowired
    @Qualifier("passwordEncoder")
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            //框架的user类中保存了user的账号密码权限等信息
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("/getUserMenu")
    public Result getUserMenu(String username) {
        try {
            List<Menu> menus = userService.findUserMenuByUsername(username);
            return new Result(true, MessageConstant.GET_MENU_SUCCESS, menus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
    }

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody User user, Integer[] roleIds) {
        //对传进来的密码进行加密
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        try {
            userService.add(user, roleIds);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = userService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    //根据id查询
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        User user = userService.findById(id);
        if (user != null) {
            //把传给前端的密码设置为空,防止泄密
            user.setPassword("");
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, user);
        }
        return new Result(false, MessageConstant.QUERY_USER_FAIL);
    }

    //根据检查组合id查询对应的所有检查项id
    @RequestMapping("/findRoleIdsByUserId")
    public List<Integer> findRoleIdsByUserId(Integer id) {
        List<Integer> list = userService.findRoleIdsByUserId(id);
        return list;
    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody User user, Integer[] roleIds) {
        //1.如果用户不想改密码,就不用填写密码框
        //2.在findById方法中将密码设置为空,如果接收回来的密码仍为空,则说明用户不想改变密码
        //3.此时将密码设置为null,然后在dao动态生成sql语句,判断密码为null则不进行修改
        //4.dao:   <if test="password != null"> password = #{password}, </if>
        //5.mybatis会管理if标签结尾的逗号,如果标签外没有其他语句,则自动删除该逗号,不会影响查询
        if ("".equals(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        }
        try {
            userService.edit(user, roleIds);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            userService.delete(id);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll() throws Exception {
        List<User> userList = userService.findAll();

        if (userList != null && userList.size() > 0) {
            Result result = new Result(true, MessageConstant.QUERY_USER_SUCCESS, userList);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_USER_FAIL);
    }

    @RequestMapping("/getUserTelephone")
    public Result getUserTelephone(String username) {
        try {
            User user = userService.findUserByUsername(username);
            return new Result(true, MessageConstant.QUERY_USER_TELEPHONE_SUCCESS, user.getTelephone());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_TELEPHONE_FAIL);
        }
    }

    @RequestMapping("/changePassword")
    public Result changePassword(@RequestBody Map map) {
        try {
            String telephone = (String) map.get("telephone");
            String code = (String) map.get("code");
            String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_FOR_CHANGEPASSWORD);
            if (codeInRedis == null || !codeInRedis.equals(code)) {
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            } else {
                String password = bcryptPasswordEncoder.encode((String) map.get("password"));
                userService.changePassword(password);
                return new Result(true, MessageConstant.EDIT_USER_PASSWORD_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USER_PASSWORD_FAIL);
        }
    }
}
