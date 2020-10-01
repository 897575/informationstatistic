package com.java.informationstatistic.controller;

import com.java.informationstatistic.service.BrandInfoService;
import com.java.informationstatistic.service.CarResultService;
import com.java.informationstatistic.service.DataDealService;
import com.java.informationstatistic.tools.DataUtil;
import com.java.informationstatistic.tools.StringInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 信息处理控制层
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020729
 */
@Controller
public class InformationDealController {

    @Resource
    private DataDealService dataDealService;

    @Resource
    private CarResultService carResultService;

    @Resource
    private BrandInfoService brandInfoService;

    @ResponseBody
    @PostMapping("/data/initDeal")
    public String initinformationDeal(String month, String year, String platform) {
        if (month == null || year == null || platform == null || month.isEmpty() || year.isEmpty() || platform.isEmpty()) {
            return StringInfo.TWO + "";
        }
        //开始时间
        int day = DataUtil.generateYearMonthDay(Integer.valueOf(year), Integer.valueOf(month));
        String beginTime = year + "-" + month + "-01";
        String endTime = year + "-" + month + "-" + day;
        System.out.println("开始时间：" + beginTime);
        System.out.println("结束时间：" + endTime);
        Map<String, String> params = new HashMap<>();
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        //加入平台信息
        params.put("platform", platform);
        int resultCount;
        if (!"2020".equals(year)) {
            resultCount = carResultService.queryAllResultInfo(params);
        } else {
            resultCount = carResultService.queryResultInfo(params);
        }
        if (resultCount != 0) {
            return StringInfo.THREE + "";
        }
        dataDealService.dataDeal(carResultService, beginTime, endTime, platform);
        return StringInfo.ONE + "";
    }

    /**
     * 数据处理
     * @param year 年份
     * @param month 月份
     * @param platform 平台
     * @param type 执行类型
     * @return 处理结果
     */
    @ResponseBody
    @PostMapping("data/initDayDeal")
    public String initDateDataDeal(String year, String month, String platform, String type) {
        int day = DataUtil.generateYearMonthDay(Integer.valueOf(year), Integer.valueOf(month));
        String beginTime = year + "-" + month + "-01";
        String endTime = year + "-" + month + "-" + day;
        Map<String, String> params = new HashMap<>();
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        params.put("platform", platform);
        //查看这个月的数据有没有处理
        int count;
        if("1".equals(type)){
            if (Integer.valueOf(year) >= 2020) {
                count = carResultService.queryResultInfo(params);
            } else {
                count = carResultService.queryAllResultInfo(params);
            }
        }else{
            count = carResultService.countBrandInfo(params);
        }
        if (count > 0) {
            return StringInfo.THREE + "";
        }
        for (int i = 0; i < day; i++) {
            if (month.length() < 2) {
                month = "0" + month;
            }
            beginTime = year + "-" + month + "-" + ((i + 1) < 10 ? "0" + (i + 1) : (i + 1));
            endTime = beginTime;
            List<String> resultList;
            if ("1".equals(type)) {
                resultList = dataDealService.dataDeal(carResultService, beginTime, endTime, platform);
            } else {
                resultList = brandInfoService.brandSplit(carResultService, platform, beginTime, endTime);
            }
            if(resultList!=null&&!resultList.isEmpty()){
                DataUtil.insertData(resultList,beginTime,carResultService,platform,type);
            }
        }
        return StringInfo.ONE + "";
    }
}