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

    /**
     * repost品牌处理
     * @param reposts repost信息
     * @param repostTags repost标签信息
     * @param postTags post标签信息
     * @param platform 平台
     * @return 处理信息
     */
    List<String> repostInfoBrand(List<Repost> reposts, List<RepostTag> repostTags, List<PostTag> postTags,String platform);
}
