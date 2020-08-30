package com.java.informationstatistic.dao.tag;

import com.java.informationstatistic.model.RepostTag;

import java.util.List;
import java.util.Map;

/**
 * tag中repost信息
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020731
 */
public interface TagRepostDao {

    /**
     * 通过postId获取信息
     *
     * @param params repostid集合
     * @return repostTag信息
     */
    List<RepostTag> findByRepostIds(Map<String,Object> params);
}
