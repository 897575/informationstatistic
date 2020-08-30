package com.java.informationstatistic.service;

import com.java.informationstatistic.model.Post;

import java.util.List;

/**
 * car库中post服务
 *
 * @author luyu
 * @since 2020726
 * @version v1.0
 *
 * copyright 18994139782@163.com
 *
 */
public interface CarPostService {

    /**
     * 根据时间获取数据
     * @return post信息
     */
    List<Post> getPostTimeInfo(String beginTime, String endTime,String tableName);

    /**
     * 测试表
     * @param tableName 表名
     */
    String testTable(String tableName);
}
