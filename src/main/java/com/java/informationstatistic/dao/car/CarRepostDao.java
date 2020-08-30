package com.java.informationstatistic.dao.car;

import com.java.informationstatistic.model.Repost;

import java.util.List;
import java.util.Map;

/**
 * car中repost信息
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
public interface CarRepostDao {

    /**
     * 通过时间获取repost信息
     *
     * @return repost信息
     */
    List<Repost> findByRepostTime(Map<String, String> params);

}
