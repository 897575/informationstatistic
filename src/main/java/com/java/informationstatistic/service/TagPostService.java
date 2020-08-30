package com.java.informationstatistic.service;

import com.java.informationstatistic.model.PostTag;

import java.util.List;

/**
 * tag中post信息服务
 *
 * @author luyu
 * @since 2020729
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public interface TagPostService {

    /**
     *  根据post获取name标签
      * @param postIds postId集合
     * @return postTag信息
     */
     List<PostTag> getTagPostInfo(List<Long> postIds,String tableName);

    /**
     * 测试表是否存在
     * @param tableName 表名
     */
    String testTableName(String tableName);

}
