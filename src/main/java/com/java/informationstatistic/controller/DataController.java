package com.java.informationstatistic.controller;

import com.java.informationstatistic.model.Result;
import com.java.informationstatistic.service.CarResultService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据查询控制类
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 20200830
 */
@Controller
public class DataController {

    @Resource
    private CarResultService carResultService;

    @ResponseBody
    @PostMapping("/data/query")
    public Map<String, Object> queryResultByTimeLimit(Integer pageSize, Integer pageNumber, String time, String platform) {
        Map<String, Object> resultMap = new HashMap<>();
        if (platform == null || "".equals(platform)) {
            resultMap.put("status", "202");
            resultMap.put("message", "平台未选择");
            return resultMap;
        }
        if (pageNumber == null | pageSize == null || pageSize == 0 || pageNumber == 0) {
            resultMap.put("status", "202");
            resultMap.put("message", "页码缺失");
            return resultMap;
        }
        if (time == null || "".equals(time)) {
            resultMap.put("status", "202");
            resultMap.put("message", "开始时间缺失");
            return resultMap;
        }
        String beginTime = time.split("/")[0].trim();
        String endTime = time.split("/")[1].trim();

        //验证时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            sdf.parse(beginTime);
            sdf.parse(endTime);
        } catch (Exception e) {
            resultMap.put("status", "202");
            resultMap.put("message", "请输入正确的时间");
            return resultMap;
        }
        //获取总共的条数
        int total = 0;
        Map<String, String> params = new HashMap<>();
        params.put("beginTime", beginTime + "-01");
        params.put("endTime", endTime + "-31");
        params.put("platform", platform);
        if (Integer.valueOf(beginTime.split("-")[0]) >= 2020) {
            total = carResultService.queryResultInfo(params);
        } else {
            total = carResultService.queryAllResultInfo(params);
        }
        if (total == 0) {
            resultMap.put("status", "201");
            return resultMap;
        }
        String startIndex = String.valueOf((pageNumber - 1) * pageSize);
        params.put("firstIndex", startIndex);
        params.put("pageSize", pageSize + "");
        List<Result> results;
        if (Integer.valueOf(beginTime.split("-")[0]) >= 2020) {
            results = carResultService.queryResultLimit(params);
        } else {
            results = carResultService.queryAllResultLimit(params);
        }
        if (results == null || results.isEmpty()) {
            resultMap.put("status", "204");
            resultMap.put("message", "没有数据");
            return resultMap;
        }
        resultMap.put("status", "200");
        resultMap.put("total", total);
        resultMap.put("rows", results);
        return resultMap;
    }
}
