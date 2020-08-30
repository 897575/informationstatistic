package com.java.informationstatistic.service;

import java.util.List;

/**
 * 数据处理服务
 *
 * @author luyu
 * @since 20200807
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public interface DataDealService {

    /**
     * 数据处理
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param platform 平台类别
     */
    void dataDeal(CarResultService carResultService, String beginTime, String endTime, String platform);
}
