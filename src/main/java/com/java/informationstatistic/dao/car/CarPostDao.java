package com.java.informationstatistic.dao.car;

import com.java.informationstatistic.model.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * car中post的数据查询
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
public interface CarPostDao {

    /**
     * 根据时间来获取信息
     *
     * @return 返回结果
     */
    List<Post> findByPostTime(Map<String, String> params);

    /**
     * 测试表
     * @param tableName 表名
     */
    String testTable(@Param("tableName")String tableName);
}
