package com.java.informationstatistic.controller;

import com.java.informationstatistic.service.CarResultService;
import com.java.informationstatistic.tools.ExcelUtil;
import com.java.informationstatistic.tools.StringInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 打印excel
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020724
 */
@Controller
public class ExcelController {

    @Resource
    private CarResultService carResultService;

    /**
     * 打印数据到excel表中
     *
     * @param time 导出时间
     * @param platform 导出平台
     */
    @GetMapping("/excel/download")
    public void printExcel(String time,String platform,HttpServletResponse response) {
        if(time==null||"".equals(time)||platform==null||"".equals(platform)){
            return;
        }
        if(!time.contains("/")){
            return ;
        }
        String[] times = time.split("/");
        String beginTime = times[0].trim();
        String endTime = times[1].trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        int month;
        try{
            Calendar calendar = Calendar.getInstance();
            Date beginDate = sdf.parse(beginTime);
            calendar.setTime(beginDate);
            int beginMonth =calendar.get(Calendar.MONTH)+1;
            Date endDate = sdf.parse(endTime);
            calendar.setTime(endDate);
            int endMonth = calendar.get(Calendar.MONTH)+1;
            //因为不支持跨年，所以直接减去
            month = endMonth - beginMonth+StringInfo.ONE;
        }catch (Exception e){
            return ;
        }
        ExcelUtil.generateExcel(response, beginTime,platform,month,carResultService);
    }
}
