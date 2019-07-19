package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.OrderSettingListDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.OrderSettingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

/**
 * @author zyx
 * @Description
 * @dateTime 2019/7/18 9:13
 */
@Service(interfaceClass = OrderSettingListService.class)
public class OrderSettingListServiceImpl implements OrderSettingListService {
    @Autowired
    private OrderSettingListDao orderSettingListDao;

    @Override
    public PageResult<OrderSettingList> findPage(QueryPageBean queryPageBean) {
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<OrderSettingList> page = orderSettingListDao.findByCondition(queryPageBean.getQueryString());
        PageResult<OrderSettingList> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    @Transactional
    public void add(Member member, Integer[] packageIds) {
        orderSettingListDao.add(member);
        Integer memberId = member.getId();
        if (packageIds != null) {
            for (Integer packageId : packageIds) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(member.getOrderDate());
                orderSettingListDao.setMemberAndPackage(memberId, packageId, member.getOrderDate());
            }
        }
    }

    @Override
    public void editOrderStatus(int id) {
        orderSettingListDao.editOrderStatus(id);
    }

    @Override
    public void cancelOrderStatus(int id) {
        orderSettingListDao.cancelOrderStatus(id);
    }
}
