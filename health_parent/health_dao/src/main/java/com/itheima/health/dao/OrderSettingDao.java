package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

//@Repository
public interface OrderSettingDao {
    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Object map);

    //根据预约日期查询预约设置信息
    OrderSetting findByOrderDate(Date date);

    //更新已预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);

    void deleteByDate(String date);

    List<OrderSetting> findAllOrderSetting();
}
