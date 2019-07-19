package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.OrderSettingList;
/**
 * @author zyx
 * @Description
 * @dateTime 2019/7/18 9:03
 */
public interface OrderSettingListService {
    PageResult<OrderSettingList> findPage(QueryPageBean queryPageBean);

    void add(Member member, Integer[] packageIds);

    void editOrderStatus(int id);

    void cancelOrderStatus(int id);
}
