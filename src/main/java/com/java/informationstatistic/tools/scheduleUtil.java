package com.java.informationstatistic.tools;

import com.java.informationstatistic.service.CarResultService;
import com.java.informationstatistic.service.DataDealService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时执行工具类
 */
@Component
public class scheduleUtil {


    @Resource
    private DataDealService dataDealService;

    @Resource
    private CarResultService carResultService;

    /**
     * 数据定时处理业务，每天处理昨天的数据(每天凌晨触发)
      */
    @Scheduled(cron = "0 0 0 * * ?")
    public void DataScheduleDeal(){

    }
}
