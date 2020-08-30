package com.java.informationstatistic.service;

import com.java.informationstatistic.model.PostTag;
import com.java.informationstatistic.model.Repost;
import com.java.informationstatistic.model.RepostTag;

import java.util.List;

/**
 * repost信息处理服务层
 *
 * @author luyu
 * @since 2020731
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public interface RepostInformationDealService {
    /**
     * repost信息处理
     * @return 处理结果
     */
    List<String> repostInfoDeal(List<Repost> reposts, List<RepostTag> repostTags, List<PostTag> postTags,String platform);
}
