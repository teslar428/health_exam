package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiContext;
import org.jxls.util.JxlsHelper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

//统计报表
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> result = reportService.getBusinessReport();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    //套餐占比统计
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try {
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> list = setmealService.findSetmealCount();
            List<String> setmealNames = new ArrayList<>();
            for (Map<String, Object> m : list) {
                String name = (String) m.get("name");
                setmealNames.add(name);
            }
            map.put("setmealNames", setmealNames);
            map.put("setmealCount", list);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    //会员数量统计
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -12);//获得当前日期之前12个月的日期

            List<String> list = new ArrayList<String>();
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("months", list);

            List<Integer> memberCount = memberService.findMemberCountByMonth(list);
            map.put("memberCount", memberCount);

            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

    }

    //导出Excel报表
    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // excel操作在controller完成，基于网络的考虑
        Map<String, Object> reportData = reportService.getBusinessReport();
        // 获取模板文件
        String template = req.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        // 下载的内容是excel文件
        res.setContentType("application/vnd.ms-excel");
        String filename = null;
        try {
            filename = new String("运营数据统计.xlsx".getBytes(), "ISO-8859-1");
            // 告诉浏览器，内容体是一个附件
            res.setHeader("Content-Disposition", "attachment;filename=" + filename);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try (
                // 放在这里的对象必须实现closeable接口，try{代码块里的代码执行完后，会自动调用close方法
                // 写入到输出流中
                OutputStream os = res.getOutputStream();
                // 相当打开了模板文件
                XSSFWorkbook wk = new XSSFWorkbook(template);
        ) {
            // 获取第一个工作表
            XSSFSheet sht = wk.getSheetAt(0);
            // 写数据
            XSSFRow row = sht.getRow(2);
            // 报表日期的单元格
            XSSFCell cell = row.getCell(5);
            cell.setCellValue((String) reportData.get("reportDate"));
            // 会员 数量
            row = sht.getRow(4);
            row.getCell(5).setCellValue((Integer) reportData.get("todayNewMember"));
            row.getCell(7).setCellValue((Integer) reportData.get("totalMember"));
            row = sht.getRow(5);
            row.getCell(5).setCellValue((Integer) reportData.get("thisWeekNewMember"));
            row.getCell(7).setCellValue((Integer) reportData.get("thisMonthNewMember"));

            // 到诊预约
            row = sht.getRow(7);
            row.getCell(5).setCellValue((Integer) reportData.get("todayOrderNumber"));
            row.getCell(7).setCellValue((Integer) reportData.get("todayVisitsNumber"));
            row = sht.getRow(8);
            row.getCell(5).setCellValue((Integer) reportData.get("thisWeekOrderNumber"));
            row.getCell(7).setCellValue((Integer) reportData.get("thisWeekVisitsNumber"));
            row = sht.getRow(9);
            row.getCell(5).setCellValue((Integer) reportData.get("thisMonthOrderNumber"));
            row.getCell(7).setCellValue((Integer) reportData.get("thisMonthVisitsNumber"));

            int rowNum = 12;
            // 热门套餐
            List<Map<String, Object>> hotSetmeals = (List<Map<String, Object>>) reportData.get("hotSetmeal");
            for (Map<String, Object> hotSetmeal : hotSetmeals) {
                row = sht.getRow(rowNum);
                // {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',count:200,proportion:0.222,remark:'abc'},
                row.getCell(4).setCellValue((String) hotSetmeal.get("name"));
                row.getCell(5).setCellValue((Long) (hotSetmeal.get("setmeal_count")));
                // hotPackage.get("proportion") BigDecimal
                row.getCell(6).setCellValue(hotSetmeal.get("proportion").toString());
                row.getCell(7).setCellValue((String) hotSetmeal.get("remark"));
                rowNum++;
            }
            wk.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //废弃
    @RequestMapping("/exportBusinessReport2")
    public Result exportBusinessReport2(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> result = reportService.getBusinessReport();

            //取出返回结果数据,准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //获得Excel模板文件绝对路径
            String temlateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";

            //读取模板文件创建Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));

            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数   
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map map : hotSetmeal) {//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms‐excel");
            response.setHeader("content‐Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        }
    }

    //废弃
    @RequestMapping("/exportBusinessReport3")
    public void exportBusinessReport3(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // excel操作在controller完成，基于网络的考虑
        Map<String, Object> reportData = reportService.getBusinessReport();
        // 获取模板文件
        String template = req.getSession().getServletContext().getRealPath("/template/report_template2.xlsx");
        // 下载的内容是excel文件
        res.setContentType("application/vnd.ms-excel");
        String filename = null;
        try {
            filename = new String("运营数据统计.xlsx".getBytes(), "ISO-8859-1");
            // 告诉浏览器，内容体是一个附件
            res.setHeader("Content-Disposition", "attachment;filename=" + filename);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (
                OutputStream os = res.getOutputStream();
        ) {
            Context context = new PoiContext();
            context.putVar("obj", reportData);
            JxlsHelper.getInstance().processTemplate(new FileInputStream(template), os, context);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //测试
    @RequestMapping("/reportTest")
    public void reportTest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Date date = new Date();
        String today = DateUtils.parseDate2String(date, "yyyy-MM-dd");
        map.put("reportDate", today);
        map.put("username", "admin");
        map.put("password", "admin");
        List<Map> userList = new ArrayList<>();
        HashMap<String, String> user01 = new HashMap();
        HashMap<String, String> user02 = new HashMap();
        HashMap<String, String> user03 = new HashMap();
        user01.put("name", "张三");
        user01.put("age", "18");
        user01.put("address", "地球");
        user01.put("money", "12580");

        user02.put("name", "李四");
        user02.put("age", "19");
        user02.put("address", "月球");
        user02.put("money", "12306");

        user03.put("name", "模板");
        user03.put("age", "20");
        user03.put("address", "火星");
        user03.put("money", "13579");

        userList.add(user01);
        userList.add(user02);
        userList.add(user03);

        map.put("userList", userList);
        String template = req.getSession().getServletContext().getRealPath("/template/report_test.xlsx");
        // 下载的内容是excel文件
        res.setContentType("application/vnd.ms-excel");
        String filename = null;
        try {
            filename = new String("测试模板.xlsx".getBytes(), "ISO-8859-1");
            // 告诉浏览器，内容体是一个附件
            res.setHeader("Content-Disposition", "attachment;filename=" + filename);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (
                OutputStream os = res.getOutputStream();
        ) {
            Context context = new PoiContext();
            context.putVar("obj", map);
            JxlsHelper.getInstance().processTemplate(new FileInputStream(template), os, context);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/findMemberCountBySex")
    public Result findMemberCountBySex() {
        try {
            Map<String, Object> map = new HashMap<>();
            List<String> sex = new ArrayList<String>();
            sex.add("男");
            sex.add("女");
            map.put("sex", sex);

            List<Integer> count = memberService.findMemberCountBySex();
            Map<String, Object> map01 = new HashMap<>();
            map01.put("name", "男");
            map01.put("value", count.get(0));

            Map<String, Object> map02 = new HashMap<>();
            map02.put("name", "女");
            map02.put("value", count.get(1));

            List<Map<String, Object>> sexCount = new ArrayList<>();
            sexCount.add(map01);
            sexCount.add(map02);
            map.put("count", sexCount);
            return new Result(true, MessageConstant.QUERY_MEMBER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MEMBER_FAIL);
        }

    }

    @RequestMapping("/findMemberCountByAge")
    public Result findMemberCountByAge() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> startDateList = new ArrayList<String>();
        List<String> endDateList = new ArrayList<String>();


        //0~18岁:0~(12*18)个月前
        //select count(*) from t_member between #{startDate} and #{endDate}
        //startDate:12*18个月前 endDate:现在
        String startDate01 = simpleDateFormat.format(calendar.getTime());//当前日期
        endDateList.add(startDate01);//0~18岁的结束日期
        calendar.add(Calendar.MONTH, -12 * 18);
        String endDate01 = simpleDateFormat.format(calendar.getTime());
        startDateList.add(endDate01);//0~18岁的起始日期

        endDateList.add(endDate01);//18~30岁的结束日期
        calendar.add(Calendar.MONTH, -12 * 12);
        String endDate02 = simpleDateFormat.format(calendar.getTime());
        startDateList.add(endDate02);//18~30岁的起始日期

        endDateList.add(endDate02);//30~45岁的结束日期
        calendar.add(Calendar.MONTH, -12 * 15);
        String endDate03 = simpleDateFormat.format(calendar.getTime());
        startDateList.add(endDate03);//30~45岁的起始日期

        endDateList.add(endDate03);//45岁
        calendar.add(Calendar.MONTH, -12 * 200);//基本没人能活200年以上
        String endDate04 = simpleDateFormat.format(calendar.getTime());
        startDateList.add(endDate04);//45岁以上

        List<Integer> ageList = memberService.findMemberCountByBirthday2Date(startDateList, endDateList);

        //一个年龄段当做一个对象
        Map<String, Object> map01 = new HashMap<>();
        Map<String, Object> map02 = new HashMap<>();
        Map<String, Object> map03 = new HashMap<>();
        Map<String, Object> map04 = new HashMap<>();

        map01.put("name", "0-18");
        map01.put("value", ageList.get(0));
        map02.put("name", "18-30");
        map02.put("value", ageList.get(1));
        map03.put("name", "30-45");
        map03.put("value", ageList.get(2));
        map04.put("name", "45以上");
        map04.put("value", ageList.get(3));

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map01);
        list.add(map02);
        list.add(map03);
        list.add(map04);

        List<String> age = new ArrayList<String>();
        age.add("0-18");
        age.add("18-30");
        age.add("30-45");
        age.add("45以上");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("age", age);
        map.put("ageList", list);
        return new Result(true, MessageConstant.QUERY_MEMBER_SUCCESS, map);
    }

    @RequestMapping("/findMemberCountByMonth")
    public Result findMemberCountByMonth(String startMonth, String endMonth) {
        try {
            String startDate = startMonth + "-01";
            String endDate = endMonth + "-31";
            //用工具类得到中间月份
            List<String> months = DateUtils.getMonthBetween(startDate, endDate, "yyyy-MM");
            List<Integer> list = memberService.findMemberCountByMonth(months);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("months", months);
            map.put("memberCount", list);
            return new Result(true, MessageConstant.QUERY_MEMBER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MEMBER_FAIL);
        }
    }

}
