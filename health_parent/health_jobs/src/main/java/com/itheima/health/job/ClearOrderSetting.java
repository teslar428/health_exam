package com.itheima.health.job;

import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClearOrderSetting {
    @Autowired
    private OrderSettingDao orderSettingDao;

    public void doClearOrderSetting() {
        Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(firstDay4ThisMonth);
        orderSettingDao.deleteByDate(date);
    }

}
