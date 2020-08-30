package com.java.informationstatistic.service;

import com.java.informationstatistic.model.Repost;

import java.util.List;

/**
 * car中repost信息服务层
 *
 * @author luyu
 * @since 2020731
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public interface CarRepostService {

     /**
      * 通过时间获取repost信息
      * @return repost 信息
      */
     List<Repost> getRepostTimeInfo(String beginTime, String endTime, String platform);

}
