package com.java.informationstatistic.dao.tag;

import com.java.informationstatistic.model.PostTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据服务层
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
public interface TagPostDao {

    /**
     * 通过id信息找寻tag信息
     *
     * @param params 参数集合
     * @return 结果
     */
    List<PostTag> findByPostIds(Map<String,Object> params);

    /**
     * 测试表是否存在
     * @param tableName 表名
     */
    String testTableName(@Param("tableName")String tableName);
}
