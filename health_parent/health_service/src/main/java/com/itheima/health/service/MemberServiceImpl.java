package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//会员服务
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    // 根据月份统计会员数量
    public List<Integer> findMemberCountByMonth(List<String> months) {
        List<Integer> list = new ArrayList<>();
        for (String m : months) {
            m = m + "-31";
            Integer count = memberDao.findMemberCountBeforeDate(m);
            list.add(count);
        }
        return list;
    }

    @Transactional
    public void add(Member member) {
        memberDao.add(member);
    }

    public List<Integer> findMemberCountBySex() {
        return memberDao.findMemberCountBySex();
    }

    public List<Integer> findMemberCountByBirthday2Date(List<String> startDateList, List<String> endDateList) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < startDateList.size(); i++) {
            Integer count = memberDao.findMemberCountByBirthday2Date(startDateList.get(i), endDateList.get(i));
            list.add(count);
        }
        return list;
    }


}
