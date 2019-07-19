package com.itheima.health.service;

import com.itheima.health.pojo.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MemberService {
    Member findByTelephone(String telephone);

    List<Integer> findMemberCountByMonth(List<String> list);

    void add(Member member);

    List<Integer> findMemberCountBySex();


    List<Integer> findMemberCountByBirthday2Date(List<String> startDateList, List<String> endDateList);

}
