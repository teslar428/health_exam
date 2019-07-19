package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.OrderSettingList;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author zyx
 * @Description
 * @dateTime 2019/7/18 9:15
 */
public interface OrderSettingListDao {

    Page<OrderSettingList> findByCondition(String queryString);

    void add(Member member);

    void setMemberAndPackage(@Param("memberId") Integer memberId, @Param("packageId") Integer packageId, @Param("orderDate") Date orderDate);

    void editOrderStatus(int id);

    void cancelOrderStatus(int id);
}
