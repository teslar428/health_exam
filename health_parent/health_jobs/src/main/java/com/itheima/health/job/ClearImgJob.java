package com.itheima.health.job;

import com.itheima.health.constant.RedisConstant;
import com.itheima.health.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    //清理图片
    public void doCleanImage(){
        // - 注入redis,计算两个集合的差集，就是我们要删除的图片名称
        // SETMEAL_PIC_RESOURCES 所有的
        // SETMEAL_PIC_DB_RESOURCES 保存到数据库中的
        Jedis jedis = jedisPool.getResource();
        Set<String> picsNeed2Delete = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        // - 调用7牛删除服务器上的文件
        QiNiuUtil.removeFiles(picsNeed2Delete.toArray(new String[]{}));
        // - 移除已经删除了的文件的缓存
        for (String imageName : picsNeed2Delete) {
            jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES,imageName);
        }
    }

}
