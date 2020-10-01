package com.java.informationstatistic.service;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌拆分服务层
 *
 * @author luyu
 * @since 20200928
 * @version v.0
 *
 * copyright 18994139782@163.com
 *
 */
public interface BrandInfoService {


    /**
     * 品牌拆分
     * @param platform 平台
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 拆分结果
     */
    List<String> brandSplit(CarResultService carResultService,String platform,String beginTime,String endTime);

}
