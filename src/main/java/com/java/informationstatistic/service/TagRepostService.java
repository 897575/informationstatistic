package com.java.informationstatistic.service;

import com.java.informationstatistic.model.Repost;
import com.java.informationstatistic.model.RepostTag;

import java.util.List;

/**
 * tag中repost信息
 *
 * @author luyu
 * @since 2020731
 * @version v1.0
 *
 * copyright 18994139782@163.com
 *
 */
public interface TagRepostService {

    /**
     * 获取repost信息
     * @param reposts repostId集合
     * @return repostTag信息
     */
    List<RepostTag> findByRepostIds(List<Repost> reposts, String platform);
}
